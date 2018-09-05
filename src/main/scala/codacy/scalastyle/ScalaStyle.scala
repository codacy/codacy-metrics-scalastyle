package codacy.scalastyle

import java.net.URLClassLoader
import java.nio.file.Paths

import better.files.File
import codacy.docker.api.metrics.{FileMetrics, MetricsTool}
import codacy.docker.api.{MetricsConfiguration, Source}
import com.codacy.api.dtos.Language
import org.scalastyle._

import scala.util.Try

object ScalaStyle extends MetricsTool {

  override def apply(source: Source.Directory,
                     language: Option[Language],
                     files: Option[Set[Source.File]],
                     options: Map[MetricsConfiguration.Key, MetricsConfiguration.Value]): Try[List[FileMetrics]] = {

    val filesToLint = files.map(_.map(file => source.path + "/" + file.path)).getOrElse(Set(source.path))

    val config = MainConfig(error = false, directories = filesToLint.toList)

    Try {
      fileComplexities(config).map {
        case (file, complexity) =>
          val filePath = relativize(source.path, file)
          FileMetrics(filePath, complexity)
      }
    }

  }

  private def relativize(sourcePath: String, filePath: String): String = {
    val file = File(Paths.get(filePath))
    val srcFolder = File(Paths.get(sourcePath))
    srcFolder.relativize(file).toString
  }

  private def fileComplexities(mc: MainConfig): List[(String, Option[Int])] = {
    val configuration = ScalastyleConfiguration.readFromString(
      """
        |<scalastyle commentFilter="enabled">
        |    <check enabled="true" class="org.scalastyle.scalariform.CyclomaticComplexityChecker" level="warning">
        |        <parameters>
        |            <parameter name="maximum">1</parameter>
        |            <parameter name="countCases">true</parameter>
        |        </parameters>
        |    </check>
        |</scalastyle>
      """.stripMargin)

    val urlClassLoaderOpt =
      mc.externalJar.flatMap(jar => Some(new URLClassLoader(Array(new java.io.File(jar).toURI.toURL))))
    val fileSpecs =
      Directory.getFiles(mc.inputEncoding, mc.directories.map(new java.io.File(_)), excludedFiles = mc.excludedFiles)
    val messages = new ScalastyleChecker(urlClassLoaderOpt).checkFiles(configuration, fileSpecs)

    val methodComplexities = messages.collect {
      case StyleError(file, _, _, _, complexityStr :: _, _, _, _) =>
        (file.name, Try(complexityStr.toInt).toOption)
    }

    val complexitiesByFile = methodComplexities.groupBy { case (filename, _) => filename }

    complexitiesByFile.map {
      case (_, complexities) =>
        complexities.maxBy { case (_, complexity) => complexity.getOrElse(0) }
    }(collection.breakOut)
  }
}

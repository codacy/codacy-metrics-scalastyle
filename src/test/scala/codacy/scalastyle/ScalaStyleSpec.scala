package codacy.scalastyle

import better.files.File
import com.codacy.plugins.api.Source
import com.codacy.plugins.api.languages.Languages.Scala
import com.codacy.plugins.api.metrics.FileMetrics
import org.specs2.mutable.Specification

import scala.util.Success

class ScalaStyleSpec extends Specification {

  val targetDir = File("src/test/resources")

  val dummyScalaFileMetrics =
    FileMetrics("codacy/metrics/DummyScalaFile.scala", complexity = Some(2))
  val loggerFileMetrics = FileMetrics("codacy/metrics/JLineHistory.scala", complexity = Some(3))

  "ScalaStyle" should {
    "get complexity" in {
      "all files within a directory" in {
        val expectedFileMetrics = List(dummyScalaFileMetrics, loggerFileMetrics)
        val fileMetricsMap =
          ScalaStyle(
            source = Source.Directory(targetDir.pathAsString),
            language = None,
            files = None,
            options = Map.empty)

        fileMetricsMap should beLike {
          case Success(elems) => elems should containTheSameElementsAs(expectedFileMetrics)
        }
      }

      "specific files" in {
        val expectedFileMetrics = List(loggerFileMetrics)

        val fileMetricsMap = ScalaStyle(
          source = Source.Directory(targetDir.pathAsString),
          language = Option(Scala),
          files = Some(Set(Source.File(s"${targetDir.pathAsString}/${loggerFileMetrics.filename}"))),
          options = Map.empty)

        fileMetricsMap should beLike {
          case Success(elems) => elems should containTheSameElementsAs(expectedFileMetrics)
        }
      }
    }
  }

}

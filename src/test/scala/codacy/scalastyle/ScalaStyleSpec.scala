package codacy.scalastyle

import com.codacy.plugins.api.Source
import com.codacy.plugins.api.metrics.FileMetrics
import org.specs2.mutable.Specification

import scala.util.Success

class ScalaStyleSpec extends Specification {

  val dummyScalaFileMetrics =
    FileMetrics("codacy/metrics/DummyScalaFile.scala", complexity = Some(2))
  val loggerFileMetrics = FileMetrics("codacy/metrics/JLineHistory.scala", complexity = Some(3))

  val targetDir = "src/test/resources"

  "ScalaStyle" should {
    "get complexity" in {
      "all files within a directory" in {
        val expectedFileMetrics = List(dummyScalaFileMetrics, loggerFileMetrics)
        val fileMetricsMap =
          ScalaStyle(source = Source.Directory(targetDir), language = None, files = None, options = Map.empty)

        fileMetricsMap should beLike {
          case Success(elems) => elems should containTheSameElementsAs(expectedFileMetrics)
        }
      }

      "specific files" in {
        val expectedFileMetrics = List(loggerFileMetrics)

        val fileMetricsMap = ScalaStyle(
          source = Source.Directory(targetDir),
          language = None,
          files = Some(Set(Source.File(loggerFileMetrics.filename))),
          options = Map.empty)

        fileMetricsMap should beLike {
          case Success(elems) => elems should containTheSameElementsAs(expectedFileMetrics)
        }
      }
    }
  }

}

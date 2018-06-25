package codacy

import codacy.scalastyle.ScalaStyle
import com.codacy.docker.api.metrics.DockerMetrics

object Metrics extends DockerMetrics(ScalaStyle)

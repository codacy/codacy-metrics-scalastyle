package codacy.metrics

class DummyScalaFile {

  // This is the main
  def main(args: Array[String]) = {
    /*
    And, of course, the classic:
     */
    val helloWorld = "Hello World!"
    helloWorld match {
      case "Hello World!" =>
        println(helloWorld)
    }

  }

}

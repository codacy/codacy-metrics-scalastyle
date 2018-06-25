package codacy.metrics

class Logger {
  def trace(str: String) = println(str)

  def warn(str: String) = {
    true match {
      case 1 =>
        false match {
          case 3 =>
            println("rawr")
        }
    }
  }

}

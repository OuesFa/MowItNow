import model.{Actions, Boundaries}

import scala.io.Source
import java.io.{FileReader, FileNotFoundException, IOException}
import scala.util.parsing.combinator._

class FileParser extends RegexParsers {

  val REGEX_LAWN = "^\\s*(\\d+)\\s+(\\d+)\\s*$"
  val REGEX_MOWER = "^\\s*(\\d+)\\s+(\\d+)\\s+([NESW])\\s*$"

    def actionList: Parser[String]   = """(A|G|D)++""".r       ^^ { Actions(_) }
    def boundaries: Parser[(Int, Int)]    = """^\s*(\d+)\s+(\d+)\s*$""".r ^^ { Boundaries(_._1, _._2) }

}


object TestSimpleParser extends FileParser {
  def main(args: Array[String]) = {
    parse(boundaries, "1 2") match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    }
  }
}
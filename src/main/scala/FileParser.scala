import model.{Actions, Boundaries}

import scala.io.Source
import java.io.{FileReader, FileNotFoundException, IOException}
import scala.util.parsing.combinator._

class FileParser extends RegexParsers {

  val REGEX_LAWN = "^\\s*(\\d+)\\s+(\\d+)\\s*$"
  val REGEX_MOWER = "^\\s*(\\d+)\\s+(\\d+)\\s+([NESW])\\s*$"

    def number: Parser[Int]    = """(0|[1-9]\d*)""".r ^^ { _.toInt }
    def instructions: Parser[String]   = """(A|G|D)+""".r ^^ { _.toString }
    def mower: Parser[String]   = """^\s*(\d+)\s+(\d+)\s+([NESW])\s*$""".r ^^ { case mower => mower }
    def boundaries: Parser[Boundaries]    = number ~ number ^^ { case northLimit ~ eastLimit => Boundaries(northLimit, eastLimit) }
    def actions: Parser[Actions]    = instructions ^^ { case instructions => Actions(instructions) }
}

object TestSimpleParser extends FileParser {
  def main(args: Array[String]) = {
    parse(boundaries, "1 2") match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    }

    parse(actions, "GAADAA") match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    }

    parse(mower, "1 1 N") match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    }
  }
}
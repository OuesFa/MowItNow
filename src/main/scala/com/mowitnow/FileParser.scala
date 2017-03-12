package com.mowitnow

import com.mowitnow.model.{Actions, Boundaries}
import scala.util.parsing.combinator._
import scala.io.Source


class FileParser extends RegexParsers {

  val REGEX_LAWN = "^\\s*(\\d+)\\s+(\\d+)\\s*$"
  val REGEX_MOWER = "^\\s*(\\d+)\\s+(\\d+)\\s+([NESW])\\s*$"
  val REGEX_NUMBER = "(0|[1-9]\\d*)"
  val REGEX_INSTRUCTION = "(A|G|D)+"
  val REGEX_ORIENTATION = "^[NESW]$"

    def number: Parser[Int] = REGEX_NUMBER.r ^^ { _.toInt } //new Regex("[a-z]+")
    def actions: Parser[String] = REGEX_INSTRUCTION.r ^^ { _.toString }
    def orientation: Parser[Char] = REGEX_ORIENTATION.r ^^ { _.toString.head }
    def mowerString: Parser[String] = REGEX_MOWER.r ^^ { case mower => mower }
    def bb: Parser[String] = REGEX_LAWN.r ^^ { _.toString}//case expr => Boundaries(expr.toString.head.toInt, expr.toString.last.toInt) }
    def boundaries: Parser[Boundaries] = number ~ number ^^ { case northLimit ~ eastLimit => Boundaries(northLimit, eastLimit) }
    def mower: Parser[Mower.Mower] = number ~ number ~ orientation ^^ { case x ~ y ~ orientation => new Mower.Mower((x,y), orientation) }
    //def actions: Parser[Actions] = instructions ^^ { case instructions => Actions(instructions) }
}

object TestSimpleParser extends FileParser {
  def main(args: Array[String]) = {
    val iterator = Source.fromFile("file").getLines()
    var m = new Mower.Mower((0,0), 'N')
    //val limits = iterator.take(1).toList
    //val bounds = Boundaries(limits.head.toInt,limits.head.toInt)
    for (line <- iterator) {
      parse(bb, line) match {
        case Success(matched, _) => println(matched)//.northLimit.toString + "  LIMITS " + matched.eastLimit.toString)
        case Failure(msg, _) => print("")//("FAILURE: " + msg)
        case Error(msg, _) => println("ERROR: " + msg)
      }

      parse(actions, line) match {
        case Success(matched, _) => {
          matched foreach (action => m.move(action))
          println(m + " " + m.boundaries.toString)
        }
        case Failure(msg, _) => print("")//("FAILURE: " + msg)
        case Error(msg, _) => println("ERROR: " + msg)
      }

      parse(mower, line) match {
        case Success(matched, _) => m = matched//; m.boundaries = bounds
        case Failure(msg, _) => print("")//("FAILURE: " + msg)
        case Error(msg, _) => println("ERROR: " + msg)
      }
    }
  }
}
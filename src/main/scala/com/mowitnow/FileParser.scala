package com.mowitnow

import java.util.logging.Logger

import com.mowitnow.model.{Actions, Boundaries}

import scala.io.Source
import scala.util.parsing.combinator._


object FileParser extends RegexParsers {

  val log = Logger.getLogger("FileParser")

  val DIGIT_REGEX = "(0|[1-9]\\d*)"
  val INSTRUCTION_REGEX = "(F|L|R)+"
  val ORIENTATION_REGEX = "^[NESW]$"

  def digit: Parser[Int] = DIGIT_REGEX.r ^^ (_.toInt)

  def instruction: Parser[String] = INSTRUCTION_REGEX.r ^^ (_.toString)

  def orientation: Parser[Char] = ORIENTATION_REGEX.r ^^ (_.toString.head)

  def boundaries: Parser[Boundaries] = digit ~ digit ^^ { case northLimit ~ eastLimit => Boundaries(northLimit, eastLimit) }

  def mower: Parser[Mower] = digit ~ digit ~ orientation ^^ { case x ~ y ~ orientation => new Mower(x, y, orientation) }

  def actions: Parser[Actions] = instruction ^^ (i => Actions(i))


  def parse(filePath: String): (Boundaries, Iterator[Actions], Iterator[Mower]) = {
    val lines = Source.fromFile(filePath).getLines()
    val parsedBoundaries = parseBoundaries(lines.next())

    val (actionLines, mowerLines) = lines.zipWithIndex.partition(lineWithIndex => lineWithIndex._2 % 2 != 0)
    val parsedActions = actionLines.map(_._1).flatMap(parseAction)
    val parsedMowers = mowerLines.map(_._1).flatMap(parseMower)

    (parsedBoundaries, parsedActions, parsedMowers)

  }

  parse("mowers")

  private def parseBoundaries(line: String): Boundaries = {
    parse(boundaries, line) match {
      case Success(matched, _) => matched
      case Failure(msg, _) => throw new IllegalArgumentException(s"Boundaries parsing failure: $msg")
      case Error(msg, _) => throw new IllegalArgumentException(s"Boundaries parsing error:  $msg")
    }
  }

  private def parseMower(line: String): Option[Mower] = {
    parse(mower, line) match {
      case Success(matched, _) => Some(matched)
      case Failure(msg, _) => log.warning(s"Mower parsing failure: $msg"); None
      case Error(msg, _) => log.warning(s"Mower parsing error:  $msg"); None
    }
  }

  private def parseAction(line: String): Option[Actions] = {
    parse(actions, line) match {
      case Success(matched, _) => Some(matched)
      case Failure(msg, _) => log.warning(s"Action parsing failure: $msg"); None
      case Error(msg, _) => log.warning(s"Action parsing error: $msg"); None
    }
  }
}
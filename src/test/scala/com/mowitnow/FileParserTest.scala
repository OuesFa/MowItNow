package com.mowitnow

import com.mowitnow.model.{Actions, Boundaries}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, GivenWhenThen, Matchers}

class FileParserTest extends FlatSpec with BeforeAndAfterEach with GivenWhenThen with Matchers {

  behavior of "FileParser"

  it should "correctly parse valid input" in {
    Given("path of file to parse")
    val path = getClass.getResource("/valid_input").getPath

    When("parse input")
    val (boundaries, actions, mowers) = FileParser.parse(path)

    Then("get valid boundaries & mower & actions")
    boundaries shouldEqual Boundaries(5, 5)
    actions.next() shouldEqual Actions("RFL")
    mowers.next().toString shouldEqual new Mower(0, 0, 'N').toString
  }

  it should "correctly parse invalid input" in {
    Given("path of file to parse")
    val path = getClass.getResource("/invalid_input").getPath

    When("parse input")
    val exception = intercept[IllegalArgumentException] {
      FileParser.parse(path)
    }

    Then("get boundaries parsing failure")
    exception.getMessage should include("Boundaries parsing failure")
  }
}

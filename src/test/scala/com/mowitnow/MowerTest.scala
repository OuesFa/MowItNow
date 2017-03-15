package com.mowitnow

import com.mowitnow.model.{Actions, Boundaries}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, GivenWhenThen, Matchers}

class MowerTest extends FlatSpec with BeforeAndAfterEach with GivenWhenThen with Matchers {

  val firstMower = new Mower(1, 2, 'N')
  val boundaries = Boundaries(5, 5)

  "mower (1, 2, 'N') final position" should "be (1,2) W if moved left" in {
    Given("A mower & specific boundaries")

    When("move it left")
    val movedMower = firstMower.move('L', boundaries)

    Then("get to right position")
    val expectedPosition = "1 2 W"
    movedMower.toString shouldEqual expectedPosition
  }


  "invalid orientation" should "trigger specific exception" in {
    Given("random character")
    val invalidOrientation = '&'
    When("creating mower")
    val caught = intercept[IllegalArgumentException] {
      new Mower(0, 0, invalidOrientation)
    }
    Then("exception is thrown")
    caught.getMessage shouldEqual "Invalid orientation."
  }


  "mower (1, 2, 'N') final position" should "be 1 3 N if moved forward" in {
    Given("our first mower")

    When("move it forward")
    val movedMower = firstMower.move('F', boundaries)

    Then("get to right position")
    val expectedPosition = "1 3 N"
    movedMower.toString shouldEqual expectedPosition
  }

  "mower (1, 2, 'N') final position" should "be 1 2 E for actions seq D" in {
    Given("our first mower")

    When("move it right")
    val movedMower = firstMower.move('R', boundaries)

    Then("get to right position")

    val expectedPosition = "1 2 E"
    movedMower.toString shouldEqual expectedPosition
  }

  "mower position coordinates" must "be non negatives" in {
    Given("negative integers")
    val x, y: Int = -1
    When("try to create mower using them")

    a [IllegalArgumentException] should be thrownBy new Mower(x, y, 'E')
  }

  "mower ((3, 3), 'E') final position" should "be (5,1) E for a specific seq of actions" in {
    Given("a second mower & a sequence of actions")
    val secondMower = new Mower(3, 3,'E')
    val actions = Actions("FFRFFRFRRF")

    When("apply actions to mower")
    val movedMowers = Mower.moveAll(boundaries, Iterator(secondMower), Iterator(actions))

    Then("get to right position")
    val expectedPosition = "5 1 E"
    assert(movedMowers.next().toString == expectedPosition)
  }

}

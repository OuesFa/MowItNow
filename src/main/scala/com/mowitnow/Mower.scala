package com.mowitnow

import com.mowitnow.Mower.{actionSet, orientationMap, orientationSet}
import com.mowitnow.model.{Actions, Boundaries}

class Mower(val x: Int, val y: Int, val orientation: Char) {

  require(orientationSet.contains(orientation), throw new IllegalArgumentException("Invalid orientation."))
  require(x >= 0 && y >= 0, throw new IllegalArgumentException("Positions must be positive."))

  override def toString = List(x, y, orientation.toString).mkString(" ")

  // applying an action to a mower
  def move(action: Char, boundaries: Boundaries): Mower = {
    //logger.debug(" moving...")
    require(actionSet.contains(action), throw new IllegalArgumentException("Invalid action."))
    action match {
      case 'L' => new Mower(x, y, orientationMap(orientation))
      case 'R' => new Mower(x, y, orientationMap.map(_.swap).apply(orientation))
      case 'F' => this.moveForward(boundaries)
    }
  }

  // Moving forward in the field
  def moveForward(boundaries: Boundaries): Mower = orientation match {
    case 'N' => if (y < boundaries.northLimit) new Mower(x, y + 1, orientation) else this
    case 'E' => if (x < boundaries.eastLimit) new Mower(x + 1, y, orientation) else this
    case 'S' => if (y > 0) new Mower(x, y - 1, orientation) else this
    case 'W' => if (x > 0) new Mower(x - 1, y, orientation) else this
  }
}

object Mower extends App {

  val (boundaries, actions, mowers) = FileParser.parse("mowers")

  val movedMowers = moveAll(boundaries, mowers, actions)

  println(movedMowers.mkString("\n"))

  def moveAll(boundaries: Boundaries, mowers: Iterator[Mower], actions: Iterator[Actions]): Iterator[Any] = {
    mowers.zip(actions).map {
      case (mower: Mower, actions: Actions) => {
        actions.instructions.fold(mower) {
          case (mowerAcc: Mower, action: Char) => mowerAcc.move(action, boundaries)
          case _ => mower
        }
      }
    }
  }

  def orientationMap: Map[Char, Char] =
    Map(
      'N' -> 'W',
      'W' -> 'S',
      'S' -> 'E',
      'E' -> 'N'
    )

  def actionSet: Set[Char] = Set('L', 'R', 'F')

  def orientationSet: Set[Char] = Set('N', 'S', 'W', 'E')

}
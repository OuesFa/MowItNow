import com.typesafe.scalalogging.Logger

object Mower extends App {

  def logger = Logger("mower logger")
  // set up an orientation map
  def oMap: Map[Char,Char] = Map('N' -> 'W',
                                'W' -> 'S',
                                'S' -> 'E',
                                'E' -> 'N')

  // Sets of class constraints
  def actionSet = Set('G', 'D', 'A')
  def orientationSet = Set('N', 'S', 'W', 'E')

  class Mower(val pos: (Int, Int), val orientation: Char) {

    var p: (Int, Int) = pos
    var o: Char = orientation
    require(orientationSet.contains(o), throw new IllegalArgumentException("Invalid orientation."))
    require(p._1>=0 && p._2>=0, throw new IllegalArgumentException("Position numbers must be non-negative."))
    override def toString = List(p.toString(),o).mkString(" ")

    // applying an action to a mower
    def move(action: Char): Unit = {
      logger.debug(" moving...")
      require(actionSet.contains(action), throw new IllegalArgumentException("Invalid action."))
      action match {
        case 'G' => o = oMap(o)
        case 'D' => o = oMap.map(_.swap).apply(o)
        case 'A' => moveForward()
      }
    }
    def moveForward(): Unit = o match {
      case 'N' => p = (p._1, p._2+1)
      case 'E' => p = (p._1+1, p._2)
      case 'S' => p = (p._1, p._2-1)
      case 'W' => p = (p._1-1, p._2)
    }
  }

  logger.debug("Reading instructions ...")
  val m = new Mower((1, 2), 'N')
  println(m)
  "GAGAGAGAA" foreach (action => m.move(action))
  println(m)
  println
  val m1 = new Mower((3, 3), 'E')
  println(m1)
  "AADAADADDA" foreach (action => m1.move(action))
  println(m1)
}
import org.scalatest.FlatSpec
import com.mowitnow.Mower.Mower

class MowerSpec extends FlatSpec {

  val firstMower = new Mower((1, 2), 'N')
  "GAGAGAGAA" foreach (action => firstMower.move(action))

  "first mower final position" should "be (1,3) N for actions seq GAGAGAGAA" in {
    assert(firstMower.toString == "(1,3) N")
  }

  "mower position coordinates" must "be non negatives" in {
    assert(firstMower.pos._1 >= 0 && firstMower.pos._2 >= 0)
  }

  val secondMower = new Mower((3, 3), 'E')
  "AADAADADDA" foreach (action => secondMower.move(action))

  "second mower final position" should "be (5,1) E for actions seq AADAADADDA" in {
    assert(secondMower.toString == "(5,1) E")
  }

}

import org.scalatest.{FunSuite, BeforeAndAfter}
import scala.io.Source

class MowerFileSpec extends FunSuite with BeforeAndAfter {
  var source: Source = _
  /*
    after { source.close }

    // assumes the file has the string "foo" as its first line
    test("1 - foo file") {
      source = Source.fromFile("./hello")
      val lines = FileUtils.getLinesUppercased(source)
      assert(lines(0) == "FOO")
    }

    test("2 - foo string") {
      source = Source.fromString("foo\n")
      val lines = FileUtils.getLinesUppercased(source)
      assert(lines(0) == "FOO")
    }
  */
}

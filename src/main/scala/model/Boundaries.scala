package model

case class Boundaries(northLimit: Int, eastLimit: Int) {
  override def toString = "Boundaries <" + northLimit + "> " + "<" + eastLimit + "> "
}
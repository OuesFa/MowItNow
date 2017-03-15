package com.mowitnow.model

case class Boundaries(northLimit: Int, eastLimit: Int) {
  override def toString: String = "Boundaries <" + northLimit + "> " + "<" + eastLimit + "> "
}
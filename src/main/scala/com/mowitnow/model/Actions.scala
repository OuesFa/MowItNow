package com.mowitnow.model

case class Actions(instructions: String) {
  override def toString: String = instructions.mkString(" -> ")
}
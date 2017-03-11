package model

case class Actions(instructions: String) {
   override def toString = instructions.mkString(" -> ")
 }
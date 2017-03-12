package com.mowitnow.utils

class Constants {
  val REGEX_LAWN = "^\\s*(\\d+)\\s+(\\d+)\\s*$"
  val REGEX_MOWER = "^\\s*(\\d+)\\s+(\\d+)\\s+([NESW])\\s*$"
  val REGEX_NUMBER = "(0|[1-9]\\d*)"
  val REGEX_INSTRUCTION = "(A|G|D)+"
  val REGEX_ORIENTATION = "^[NESW]$"
}

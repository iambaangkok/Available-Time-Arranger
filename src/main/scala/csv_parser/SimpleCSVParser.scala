package csv_parser

import scala.util.matching.Regex

object SimpleCSVParser{
  private val pattern: Regex = "([0-9a-zA-Z- ]*),([0-9a-zA-Z- ]+),([0-9a-zA-Z- ]+),([0-9a-zA-Z- ]+),([0-9a-zA-Z- ]+)".r

  def parse(input : String, pattern : Regex = SimpleCSVParser.pattern): Unit = {

    val dataFrame: List[List[String]] = List()

    for (patternMatch <- pattern.findAllMatchIn(input)) {
      val row : List[String] = List(patternMatch.group(1), patternMatch.group(2), patternMatch.group(3), patternMatch.group(4), patternMatch.group(5))
//      println(s"Active: ${patternMatch.group(1)} Name: ${patternMatch.group(2)} DoW: ${patternMatch.group(3)} FTS: ${patternMatch.group(4)} FTE: ${patternMatch.group(5)}")
    }


  }
}

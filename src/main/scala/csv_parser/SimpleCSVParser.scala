package csv_parser

import scala.util.matching.Regex

object SimpleCSVParser {
  private val alphaNumeric: Regex = "[0-9a-zA-Z- ]".r
  private val numericInteger: Regex = "[0-9]".r
  private val pattern: Regex =
    s"(1,($alphaNumeric+),($alphaNumeric+),($numericInteger+),($numericInteger+))".r

  def parse(
      input: String,
      pattern: Regex = SimpleCSVParser.pattern,
      omitFirstLine: Boolean = true
  ): List[List[String]] = {

    def getList(
        matches: Iterator[Regex.Match],
        parsed: List[List[String]]
    ): List[List[String]] = {
      if (matches.hasNext) {
        val patternMatch = matches.next()
        val row: List[String] = List(
          patternMatch.group(2),
          patternMatch.group(3),
          patternMatch.group(4),
          patternMatch.group(5)
        )

        row :: getList(matches, parsed)
      } else {
        parsed
      }
    }

    if (omitFirstLine) {
      getList(pattern.findAllMatchIn(input).drop(1), List())
    } else {
      getList(pattern.findAllMatchIn(input), List())
    }
  }
}

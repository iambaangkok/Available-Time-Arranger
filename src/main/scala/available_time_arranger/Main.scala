package available_time_arranger

import csv_parser.SimpleCSVParser
import file_reader.FileReader

object Main extends App{

  private val lines : String = FileReader.readFile("data/freetime.csv")

  println(lines)

  private val parsed = SimpleCSVParser.parse(lines);

  println(parsed)
}

package available_time_arranger

import csv_parser.SimpleCSVParser
import file_reader.FileReader

object Main extends App{

  private val fileReader: FileReader = new FileReader("data/freetime.csv")

  println(fileReader.getLines())

  private val csvParser = new SimpleCSVParser(fileReader.getLines());

  csvParser.parse();
}

package available_time_arranger

import csv_parser.SimpleCSVParser
import file_reader.FileReader

import scala.annotation.tailrec

object Main extends App {

  private val DAYS_OF_WEEK = List("M", "T", "W", "Th", "F", "S", "Su")
  type DowTimePeople = (String, TimeRange, List[String])
  private def main(): Unit = {

    val lines: String = FileReader.readFile(
      "data/When are you available to play badminton_ - ลงเวลา.csv"
    )

    val parsed = SimpleCSVParser.parse(lines, omitFirstLine = false)

    val availableTimeAndPeople = findAvailableTimeAndPeople(parsed)

    println()
    println("Available time and people each day:")
    for (pair <- availableTimeAndPeople) {
      println(pair)
    }

    val daysWithMaxPeople = getOnlyDaysWithMaxPeople(availableTimeAndPeople)

    println()
    println("Only days with max people:")
    for (dowTimePeople <- daysWithMaxPeople) {
      println(dowTimePeople)
    }
  }

  private def getOnlyDaysWithMaxPeople(
      dowTimePeoples: List[DowTimePeople]
  ): List[DowTimePeople] = {
    val maxPeopleCount = dowTimePeoples.maxBy(_._3.length)._3.length
    dowTimePeoples.filter((x: (String, TimeRange, List[String])) =>
      x._3.length == maxPeopleCount
    )
  }

  private def findAvailableTimeAndPeople(
      parsedCSV: List[List[String]]
  ): List[DowTimePeople] = {
    DAYS_OF_WEEK.map((dow: String) => {
      val timeData = parsedCSVToTimeDataList(parsedCSV, dow)

      val availableTime = findAvailableTime(timeData, new TimeRange())
      val availablePeople = findAvailablePeople(availableTime, timeData)

      (dow, availableTime, availablePeople)
    })
  }

  private def parsedCSVToTimeDataList(
      parsedCSV: List[List[String]],
      dow: String
  ): List[TimeData] = {
    parsedCSV
      .filter((l: List[String]) => l(1) == dow)
      .map((l: List[String]) =>
        new TimeData(l.head, l(1), l(2).toInt, l(3).toInt)
      )
  }
  @tailrec
  private def findAvailableTime(
      timeData: List[TimeData],
      acc: TimeRange
  ): TimeRange = {
    // Algorithm
    //  create a range from 0 to 2400
    //  intersect everyone on that day
    //  the remaining range is the available time

    if (timeData.isEmpty) {
      acc
    } else {
      val curr = timeData.head
      val currTimeRange = new TimeRange(curr.timeStart, curr.timeEnd)
      findAvailableTime(timeData.tail, acc.intersect(currTimeRange))
    }
  }

  private def findAvailablePeople(
      range: TimeRange,
      timeData: List[TimeData]
  ): List[String] = {
    timeData
      .filter((data: TimeData) => range.in(data.timeRange))
      .map((data: TimeData) => data.name)
  }

  main()
}

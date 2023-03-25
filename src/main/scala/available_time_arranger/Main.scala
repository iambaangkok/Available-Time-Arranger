package available_time_arranger

import csv_parser.SimpleCSVParser
import file_reader.FileReader

import scala.annotation.tailrec

object Main extends App {

  private val DAYS_OF_WEEK = List("M", "T", "W", "Th", "F", "S", "Su")
  type Dow = String
  type Name = String
  type DowTimePeople = (Dow, TimeRange, List[Name])
  type DowTimePeopleV2 = (Dow, TimeRanges, List[Name])
  private def main(): Unit = {

    // Read file
    val lines: String = FileReader.readFile(
      "data/When are you available to play badminton_ - ลงเวลา_2.csv"
    )

    // Parse CSV
    val parsed = SimpleCSVParser.parse(lines, omitFirstLine = false)

    val availableTimeAndPeople = findAvailableTimeAndPeopleV2(parsed)
    val daysWithMaxPeople = getOnlyDaysWithMaxPeople(availableTimeAndPeople)
    val sortedByPeopleCount = availableTimeAndPeople.sortBy(_._3.length).reverse
    // Outputs

    printDowTimePeopleList(
      availableTimeAndPeople,
      Some("Available time and people each day:")
    )
//    printDowTimePeopleList(daysWithMaxPeople, Some("Only days with max people:"))
//    printDowTimePeopleList(sortedByPeopleCount, Some("Sorted by people count:"))

  }

  private def printDowTimePeopleList(
      dowTimePeopleList: List[DowTimePeople],
      message: Option[String] = None
  ): Unit = {
    println(message.getOrElse(""))
    for (dowTimePeople <- dowTimePeopleList) {
      println(dowTimePeople)
    }
    println()
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

  private def findAvailableTimeAndPeopleV2(
      parsedCSV: List[List[String]]
  ): List[DowTimePeople] = {
    // for each day:
    //  turn timeData into TimeEvents
    //  sort TimeEvents by time
    //  iterate TimeEvents to find TimeRanges:
    //    if avail = max number of people
    //      record start
    //    if avail no longer at max
    //      record end
    //      create a TimeRange, add it to TimeRanges
    //

    DAYS_OF_WEEK.map((dow: String) => {
      val timeData = parsedCSVToTimeDataList(parsedCSV, dow)
      val timeEvents = timeData
        .map(td => new TimeEvent(td.timeStart, 1))
        .concat(timeData.map(td => new TimeEvent(td.timeEnd, -1)))
      val sorted = timeEvents.sortBy(_.time)

      println(sorted)

      val timeRanges = findTimeRanges(sorted, timeData.groupBy(_.name).size)

      println(timeRanges)
      println()

      val availableTime = findAvailableTime(timeData, new TimeRange())
      val availablePeople = findAvailablePeople(availableTime, timeData)

      (dow, availableTime, availablePeople)
    })
  }
  @tailrec
  private def findTimeRanges(
      tes: List[TimeEvent], // should be sorted by time
      max: Int,
      count: Int = 0,
      tr: TimeRange = new TimeRange(-1,-1),
      trs: TimeRanges = new TimeRanges(List[TimeRange]())
  ): TimeRanges = {
    val te = tes.head
    if(tes.nonEmpty){
//      println(tes + " " + max + " " + count + " " + tr + " " + trs)
      val newCount = count + te.event
      if(newCount == max){
        findTimeRanges(tes.tail, max, newCount, new TimeRange(te.time, tr.end), trs)
      }else{
        if(new TimeRange(tr.start, te.time).isValidTime){
          findTimeRanges(tes.tail, max, newCount, new TimeRange(-1,-1), trs.add(new TimeRange(tr.start, te.time)))
        }else{
          findTimeRanges(tes.tail, max, newCount, tr, trs)
        }
      }
    }else{
      trs.get
    }
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

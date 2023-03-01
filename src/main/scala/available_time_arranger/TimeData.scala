package available_time_arranger

class TimeData(
    val name: String,
    val dow: String,
    val timeStart: Int,
    val timeEnd: Int
) {
  override def toString: String = {
    s"TimeData($name, $dow, $timeStart, $timeEnd)"
  }

  def timeRange = new TimeRange(timeStart, timeEnd)
}

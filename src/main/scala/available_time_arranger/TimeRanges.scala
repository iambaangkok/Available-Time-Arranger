package available_time_arranger

class TimeRanges(val timeRanges: List[TimeRange]) {

  def get: TimeRanges = {
    new TimeRanges(timeRanges.sortBy(tr => tr.start))
  }

  def add(tr: TimeRange): TimeRanges = {
    new TimeRanges(tr :: timeRanges)
  }

  // UNIMPLEMENTED
  def intersect(other: TimeRanges): TimeRanges = {
    new TimeRanges(timeRanges)
  }

  override def toString: String = {
    s"TimeRanges[${timeRanges.map(tr => tr.toString)}]"
  }
}

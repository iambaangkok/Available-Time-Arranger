package available_time_arranger

class TimeRange(val start: Int = 0, val end: Int = 2400) {
  def intersect(other: TimeRange): TimeRange = if (
    start.max(other.start) <= end.min(other.end)
  ) {
    new TimeRange(start.max(other.start), end.min(other.end))
  } else {
    new TimeRange(start, end)
  }

  def in(other: TimeRange): Boolean = {
    other.start <= start && end <= other.end
  }

  def isValidTime: Boolean = {
    start >= 0 && start <= 2400 && end >= 0 && end <= 2400 && start < end
  }

  override def toString: String = {
    s"TimeRange($start, $end)"
  }
}

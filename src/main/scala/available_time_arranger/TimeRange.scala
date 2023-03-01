package available_time_arranger

class TimeRange(val start: Int = 0, val end: Int = 2400) {
  def intersect(other: TimeRange): TimeRange = {
    new TimeRange(start.max(other.start), end.min(other.end))
  }

  def in(other: TimeRange): Boolean = {
    other.start <= start && end <= other.end
  }

  override def toString: String = {
    s"TimeRange($start, $end)"
  }
}

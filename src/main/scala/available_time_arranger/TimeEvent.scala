package available_time_arranger

class TimeEvent(val time: Int, val event: Int) {
  override def toString: String = {
    s"TimeEvent($time, $event)"
  }
}

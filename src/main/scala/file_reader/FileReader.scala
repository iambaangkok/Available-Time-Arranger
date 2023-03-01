package file_reader

import scala.annotation.unused
import scala.io.Source

object FileReader {



}

class FileReader(val filePath : String) {
  private val source = Source.fromFile(filePath)
  private val lines = try source.mkString finally source.close()

  def getLines() : String = lines
}

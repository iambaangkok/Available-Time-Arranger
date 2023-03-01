package file_reader

import scala.annotation.unused
import scala.io.Source

object FileReader {

  def readFile(filePath : String) : String = {
    Source.fromFile(filePath).mkString
  }

}

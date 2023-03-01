package file_reader

import scala.annotation.unused
import scala.io.Source

object FileReader {

  def readFile(filePath: String): String = {
    val src = Source.fromFile(filePath)
    val inputString = src.mkString
    src.close()

    inputString
  }

}

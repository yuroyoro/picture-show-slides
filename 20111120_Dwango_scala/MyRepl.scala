import scala.tools.nsc.interpreter._
import scala.tools.nsc.Settings
import java.io.{PrintWriter, FileOutputStream}

object MyREPL extends App{
  val settings = new Settings
  val out = new PrintWriter(Console.out)
  val iLoop = new ILoop(Console.in, out) {
    override def printWelcome() {
      echo("オッパイ(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！")

    }
    override def prompt = "乳輪大納言 > "
  }


  iLoop process settings
}

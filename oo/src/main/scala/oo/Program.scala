package oo

abstract class Program[T](
  protected val readLn: Program.ReadLn,
  protected val write: Program.Write
) {
  def run(): T
}

object Program {
  type ReadEnv = Map[String, String]
  type ReadLn = () => String
  type Write = Any => Unit
}

class EnProgram(readLn: Program.ReadLn, write: Program.Write)
    extends Program[Unit](readLn, write) {

  def run(): Unit = {
    write("What's your name? ")
    val name = readLn()
    write(s"Hello, $name!\n")
  }
}

class EsProgram(readLn: Program.ReadLn, write: Program.Write)
    extends Program[Unit](readLn, write) {

  def run(): Unit = {
    write("¿Cómo te llamas? ")
    val name = readLn()
    write(s"¡Hola, $name!\n")
  }
}

class EnvProgram(readEnv: Program.ReadEnv, readLn: Program.ReadLn, write: Program.Write)
    extends Program[Unit](readLn, write) {

  def run(): Unit = {
    val lang = readEnv("LANG")

    if (lang.startsWith("es"))
      new EsProgram(readLn, write).run()
    else
      new EnProgram(readLn, write).run()
  }
}

object ProgramApp extends App {
  new EnvProgram(sys.env, scala.io.StdIn.readLine, print).run()
}

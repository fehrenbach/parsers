import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

import org.scalameter.utils.Tree
import org.scalameter.{CurveData, Key, Persistor, Reporter}

case class CSVReporter() extends Reporter {
  override def report(result: CurveData, persistor: Persistor): Unit = {}

  override def report(results: Tree[CurveData], persistor: Persistor): Boolean = {
    val dir = results.context.properties.getOrElse(Key("result-dir"), "tmp")
    val fileName = s"${dateISO(new Date())}.csv"

    val writer = new PrintWriter(s"$dir/$fileName")

    results foreach writeToFile(writer)

    if (writer.checkError()) throw new Exception("Stuff went wrong")

    writer.close()
    true
  }

  // from org.scalameter.reporting.DsvReporter
  val dateISO: (Date => String) = {
    val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    df.setTimeZone(TimeZone.getTimeZone("UTC"))
    (date) => df.format(date)
  }

  def writeToFile(writer: PrintWriter)(result: CurveData) = {
    val curve = result.context.properties.getOrElse(Key("curve"), "unknown").toString
    val scope = result.context.properties.getOrElse(Key("scope"), "unknown").toString

    result.measurements.foreach(measurement => {
      val params = measurement.params
      measurement.data.complete foreach(datapoint => {
        writer.format("%s;%s;%s;%e%n", curve, scope, params, datapoint: java.lang.Double)
      })
    })
  }
}

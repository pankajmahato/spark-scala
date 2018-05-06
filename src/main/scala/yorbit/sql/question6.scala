package yorbit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object question6 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val session = SparkSession.builder().appName("crime").master("local[*]").getOrCreate()

    val dataFrameReader = session.read

    val responses = dataFrameReader
      .option("header", "true")
      .option("inferSchema", value = true)
      .csv("in/Crimes_-_2001_to_present.csv")

    val responseWithSelectedColumns = responses.select("Description", "Primary Type")

    val groupedDataset = responseWithSelectedColumns.groupBy("Description")
    groupedDataset.count().show(20, false)
  }
}
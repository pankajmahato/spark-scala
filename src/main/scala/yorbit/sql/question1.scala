package yorbit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object question1 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val session = SparkSession.builder().appName("crime").master("local[*]").getOrCreate()

    val dataFrameReader = session.read

    val responses = dataFrameReader
      .option("header", "true")
      .option("inferSchema", value = true)
      .csv("in/Crimes_-_2001_to_present.csv")

    val responseWithSelectedColumns = responses.select("FBI Code")

    val groupedDataset = responseWithSelectedColumns.groupBy("FBI Code")
    groupedDataset.count().show()
  }
}
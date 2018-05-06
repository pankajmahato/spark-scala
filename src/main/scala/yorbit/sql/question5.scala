package yorbit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_unixtime, unix_timestamp}

object question5 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val session = SparkSession.builder().appName("crime").master("local[*]").getOrCreate()

    val dataFrameReader = session.read

    val responses = dataFrameReader
      .option("header", "true")
      .option("inferSchema", value = true)
      .csv("in/Crimes_-_2001_to_present.csv")

    val responseWithSelectedColumns = responses.select("Date")

    val responseWithMonth = responseWithSelectedColumns.withColumn("Month", from_unixtime(unix_timestamp(col("Date"), "MM/dd/yyyy hh:mm:ss a"), "MMMMM"))

    val groupedDataset = responseWithMonth.groupBy("Month")
    groupedDataset.count().show()

  }
}
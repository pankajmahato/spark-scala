package yorbit.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object question3 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val session = SparkSession.builder().appName("crime").master("local[*]").getOrCreate()

    val dataFrameReader = session.read

    val responses = dataFrameReader
      .option("header", "true")
      .option("inferSchema", value = true)
      .csv("in/Crimes_-_2001_to_present.csv")

    val responseWithSelectedColumns = responses.select("District", "Primary Type")

    val filterData = responseWithSelectedColumns.filter(responseWithSelectedColumns.col("Primary Type").===("THEFT"))

    val groupedDataset = filterData.groupBy("District")
    groupedDataset.count().show()
  }
}
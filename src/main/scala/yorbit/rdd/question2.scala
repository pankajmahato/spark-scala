package yorbit.rdd

import com.sparkTutorial.commons.Utils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object question2 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("crime").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val data = sc.textFile("in/Crimes_-_2001_to_present.csv")
    val cleanData = data.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    val filteredData = cleanData.filter((line: String) =>
      line.split(Utils.COMMA_DELIMITER)(5) == "NARCOTICS" && line.split(Utils.COMMA_DELIMITER)(17) == "2015")

    println("Total Count of Narcotics in 2015: " + filteredData.collect().length)

  }
}
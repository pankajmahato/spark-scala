package yorbit.rdd

import com.sparkTutorial.commons.Utils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object question4 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("crime").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val data = sc.textFile("in/Crimes_-_2001_to_present.csv")
    val cleanData = data.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    val theftData = cleanData.map((line: String) => (line.split(Utils.COMMA_DELIMITER)(17), 1)).reduceByKey((x, y) => x + y)

    println("No of Crime in each Year")
    println("Year  :  Crime Count")
    val theftCount = theftData.reduceByKey((x, y) => x + y)
    for ((code, total) <- theftData.collect()) println(code + " : " + total)

  }
}
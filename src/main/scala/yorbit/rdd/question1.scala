package yorbit.rdd

import com.sparkTutorial.commons.Utils
import com.sparkTutorial.pairRdd.aggregation.reducebykey.housePrice.AvgCount
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object question1 {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("crime").setMaster("local[1]")
    val sc = new SparkContext(conf)

    val data = sc.textFile("in/Crimes_-_2001_to_present.csv")
    val cleanData = data.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }

    val cleanDataRdd = cleanData.map((line: String) => (line.split(Utils.COMMA_DELIMITER)(14), 1))

    val fbiCodeCount = cleanDataRdd.reduceByKey((x, y) => x + y)
    for ((code, total) <- fbiCodeCount.collect()) println(code + " : " + total)

  }
}
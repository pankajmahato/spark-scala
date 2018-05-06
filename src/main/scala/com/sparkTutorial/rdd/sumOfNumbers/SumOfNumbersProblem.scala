package com.sparkTutorial.rdd.sumOfNumbers

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object SumOfNumbersProblem {

  def main(args: Array[String]) {

    /* Create a Spark program to read the first 100 prime numbers from in/prime_nums.text,
       print the sum of those numbers to console.

       Each row of the input file contains 10 prime numbers separated by spaces.
     */
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setAppName("wordCounts").setMaster("local[3]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("in/prime_nums.text")
    val numbers = lines.flatMap(line => line.split("\\s+"))

    val validNum = numbers.filter(num => !num.isEmpty)

    val intNum = validNum.map(num => num.toInt);

    val sum = intNum.reduce((x, y) => x + y)

    println(sum)
  }
}

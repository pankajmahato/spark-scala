package com.sparkTutorial.rdd.airports

import com.sparkTutorial.commons.Utils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object AirportsByLatitudeProblem {

  def main(args: Array[String]) {

    /* Create a Spark program to read the airport data from in/airports.text,  find all the airports whose latitude are bigger than 40.
       Then output the airport's name and the airport's latitude to out/airports_by_latitude.text.

       Each row of the input file contains the following columns:
       Airport ID, Name of airport, Main city served by airport, Country where airport is located, IATA/FAA code,
       ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format

       Sample output:
       "St Anthony", 51.391944
       "Tofino", 49.082222
       ...
     */
    Logger.getLogger("org").setLevel(Level.ERROR);
    val conf=new SparkConf().setAppName("airport lat").setMaster("local");
    val sc=new SparkContext(conf);

    val airports=sc.textFile("in/airports.text");
    val airportsByLat=airports.filter(line=>line.split(Utils.COMMA_DELIMITER)(6).toDouble>40);

    val airportsNameAndLat = airportsByLat.map(line => {
      val splits = line.split(Utils.COMMA_DELIMITER)
      splits(1) + ", " + splits(6)
    })
    airportsNameAndLat.saveAsTextFile("out/airports_by_latitude.text")
  }
}

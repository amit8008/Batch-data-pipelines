package com.amit.etl.batch

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.util.Properties

object postgresToMysql extends App {

  // Getting values from application.conf
  val config: Config = ConfigFactory.load()
  val postgresDBConfig = config.getConfig("com.amit.database.postgresqldb")
  val mysqlDBConfig = config.getConfig("com.amit.database.mysqldb")

  // connection properties of postgresql
  val psqlConnProperties = new Properties()
  psqlConnProperties.put("user", postgresDBConfig.getString("username"))
  psqlConnProperties.put("password", postgresDBConfig.getString("password"))

  // connection properties of mysql
  val mysqlConnProperties = new Properties()
  mysqlConnProperties.put("user", mysqlDBConfig.getString("username"))
  mysqlConnProperties.put("password", mysqlDBConfig.getString("password"))


  // Sparksession creation to start spark operations
  val spark = SparkSession
    .builder()
    .appName("This is ETL pipline to load postgres data to mysql")
    .master("local[*]")
    .config("spark.sql.shuffle.partition", "5")
    .getOrCreate()

  // Sales table from booksdb
  val booksSalesDb = spark.read
    .jdbc(postgresDBConfig.getString("dbUrl"),
      postgresDBConfig.getString("tableName"),
      psqlConnProperties)

  booksSalesDb.show(false)
  booksSalesDb.printSchema()
  println(s"Books sales table count - ${booksSalesDb.count()}")

  // Writing sales data to mysql
  booksSalesDb.write
    .mode(SaveMode.Overwrite)
    .jdbc(mysqlDBConfig.getString("dbUrl"),
        mysqlDBConfig.getString("tableName"),
        mysqlConnProperties)

}

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.util.Properties

object GettingDataBooksDbDataPSql extends App {

  val spark = SparkSession
    .builder
    .appName("Loading books db data PostgreSQL to spark")
    .master("local[*]")
    .config("spark.shuffle.partition", "5")
    .getOrCreate()

  import spark.implicits._

  val config: Config = ConfigFactory.load()

  println(s"User: ${config.getString("postgresqldb.username")}")
  println(s"password: ${config.getString("postgresqldb.password")}")
  println(s"dburl: ${config.getString("postgresqldb.dbUrl")}")
  println(s"dbname: ${config.getString("postgresqldb.dbName")}")

  val connectionProperties = new Properties()
  connectionProperties.put("user", config.getString("postgresqldb.username"))
  connectionProperties.put("password", config.getString("postgresqldb.password"))

  val booksSaleDf = spark.read
    .jdbc(config.getString("postgresqldb.dbUrl"),
      config.getString("postgresqldb.dbName"),
      connectionProperties)
    .drop(col("ordered"))

  booksSaleDf.show(false)

  /**
   * Dumping books sales data into csv file
   */
//  booksSaleDf.write
//    .option("sep", "|")
////    .option("header", "true")
//    .mode(SaveMode.Overwrite)
//    .csv("src/main/resources/booksdb/sales")

}

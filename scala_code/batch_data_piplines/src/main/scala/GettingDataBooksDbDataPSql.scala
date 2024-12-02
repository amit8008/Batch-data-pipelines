import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

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

  val connectionProperties = new Properties()
  connectionProperties.put("user", config.getString("postgresqldb.username"))
  connectionProperties.put("password", config.getString("postgresqldb.password"))

  val booksSaleDf = spark.read
    .jdbc(config.getString("postgresqldb.dbUrl"), config.getString("postgresqldb.dbName"), connectionProperties)

  booksSaleDf.show(false)

}

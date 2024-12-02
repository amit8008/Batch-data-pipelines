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

  val connectionProperties = new Properties()
  connectionProperties.put("user", "amitsingh")
  connectionProperties.put("password", "amitsingh123")

  val booksSaleDf = spark.read
    .jdbc("jdbc:postgresql://localhost:5432/booksdb", "public.sales", connectionProperties)

  booksSaleDf.show(false)

}

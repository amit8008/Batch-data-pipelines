package com.amit.database

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.log4j.Logger

import java.sql.Connection

object SchemaExtractor extends App {
  // Getting values from application.conf
  val config: Config = ConfigFactory.load()
  val postgresDBConfig = config.getConfig("com.amit.database.postgresqldb")

  // Logger
  val logger = Logger.getLogger(getClass.getName)

  // Create the C3P0 connection pool (Singleton)
  lazy val cpds = new ComboPooledDataSource()
  cpds.setDriverClass(postgresDBConfig.getString("driver"))
  cpds.setJdbcUrl(postgresDBConfig.getString("dbUrl"))
  cpds.setUser(postgresDBConfig.getString("username"))
  cpds.setPassword(postgresDBConfig.getString("password"))
  // C3P0 Pool Configuration (tune these as needed)
  cpds.setMinPoolSize(3)
  cpds.setAcquireIncrement(5)
  cpds.setMaxPoolSize(20)
  cpds.setMaxStatements(100) // Statement caching
  cpds.setMaxStatementsPerConnection(50)
  cpds.setMaxIdleTime(1800) // 30 minutes
  cpds.setIdleConnectionTestPeriod(60) // Check idle connections every 60 seconds

  def getConnection: Connection = cpds.getConnection

  def getTableSchema(tableName: String): Map[String, String] = {
    val columns = getConnection.getMetaData.getColumns(null, null, tableName, null)
    var schema = Map.empty[String, String]
    while (columns.next()){
      logger.info(s"COLUMN_NAME - ${columns.getString("COLUMN_NAME")}")
      logger.info(s"TYPE_NAME - ${columns.getString("TYPE_NAME")}")
      schema += columns.getString("COLUMN_NAME") -> columns.getString("TYPE_NAME")
    }
    schema
  }

  println(getTableSchema("sales"))

  getConnection.close()
}

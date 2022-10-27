package io.keepcoding.spark.exercise.streaming

import java.sql.Timestamp
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

import org.apache.spark.sql.{DataFrame, SparkSession}

case class AntennaMessage(timestamp: Timestamp, id: String, metric: String, value: Long)

trait StreamingJob {

  val spark: SparkSession

  def readFromKafka(kafkaServer: String, topic: String): DataFrame

  def parserJsonData(dataFrame: DataFrame): DataFrame

  def readUsersMetadata(jdbcURI: String, jdbcTable: String, user: String, password: String): DataFrame

  def enrichDevicesWithMetadata(antennaDF: DataFrame, metadataDF: DataFrame): DataFrame

  def countBytesPerAntenna(dataFrame: DataFrame): DataFrame

  def countBytesPerUser(dataFrame: DataFrame): DataFrame

  def countBytesPerApp(dataFrame: DataFrame): DataFrame

  def writeToJdbc(dataFrame: DataFrame, jdbcURI: String, jdbcTable: String, user: String, password: String): Future[Unit]

  def writeToStorage(dataFrame: DataFrame, storageRootPath: String): Future[Unit]
/*
  def run(args: Array[String]): Unit = {
    val Array(kafkaServer, topic, jdbcUri, jdbcMetadataTable, aggJdbcTable, jdbcUser, jdbcPassword, storagePath) = args
    println(s"Running with: ${args.toSeq}")

    val kafkaDF = readFromKafka(kafkaServer, topic)
    val parsedDF = parserJsonData(kafkaDF)
    val metadataDF = readUsersMetadata(jdbcUri, jdbcMetadataTable, jdbcUser, jdbcPassword)
    val enrichedDF = enrichDevicesWithMetadata(parsedDF, metadataDF)
    val storageFuture = writeToStorage(parsedDF, storagePath)
    val aggBytesPerAntenna = countBytesPerAntenna(enrichedDF)
    val aggFuture = writeToJdbc(aggBytesPerAntenna, jdbcUri, aggJdbcTable, jdbcUser, jdbcPassword)

    Await.result(Future.sequence(Seq(aggFuture, storageFuture)), Duration.Inf)

    spark.close()
  }
*/
}

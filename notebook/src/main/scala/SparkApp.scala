import org.apache.spark.sql.SparkSession

object SparkApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SparkScalaApp").master("local[*]").getOrCreate()
    val data = Seq((1, "Alice"), (2, "Bob"), (3, "Cathy"))
    val df = spark.createDataFrame(data).toDF("id", "name")

    df.show()

    spark.stop()
  }
}
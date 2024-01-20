name := """Solr9Utils"""
scalaVersion := "2.12.6"
version := "1.0"

val sparkVersion = "3.1.2"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "8.11.2",
  "com.johnsnowlabs.nlp" %% "spark-nlp" % "3.3.1",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion
)

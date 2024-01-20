name := """Solr9Utils"""
scalaVersion := "2.12.6"
version := "1.0"

val sparkVersion = "3.1.2"
val solrVersion = "9.4.1"
val sparkNLPVersion = "3.3.1"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % solrVersion,
  "com.johnsnowlabs.nlp" %% "spark-nlp" % sparkNLPVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion
)

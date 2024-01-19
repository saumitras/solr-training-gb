name := """Solr8LLM"""

scalaVersion := "2.12.6"

version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "8.11.2",
  "com.johnsnowlabs.nlp" %% "spark-nlp" % "3.3.1",
  "org.apache.spark" %% "spark-core" % "3.1.2",
  "org.apache.spark" %% "spark-sql" % "3.1.2",
  "org.apache.spark" %% "spark-mllib" % "3.1.2"
)
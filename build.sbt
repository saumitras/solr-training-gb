name := """SolrTrainingGB"""

scalaVersion := "2.11.8"

version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "6.6.2",
  "commons-io" % "commons-io" % "2.4",
  "com.ximpleware" % "vtd-xml" % "2.11"
)

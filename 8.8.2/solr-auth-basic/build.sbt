name := """solr-auth-basic"""

scalaVersion := "2.12.6"

version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.solr" % "solr-solrj" % "8.8.2",
  "commons-codec" % "commons-codec" % "1.15"
)
package index

import org.apache.solr.client.solrj.impl.{CloudHttp2SolrClient, Http2SolrClient}
import org.apache.solr.common.SolrInputDocument
import java.util.Optional
import scala.collection.JavaConverters._

object IndexAuth extends App {

  // Before running this, upload security.json (located in home dir of this project) to zookeeper using command below:
  //    bin/solr zk cp ./security.json zk:security.json -z localhost:2181

  val zkHostList = List("localhost:2181").asJava
  val userName = "solr"
  val password = "SolrRocks"
  val collectionName = "test1"
  val numDocs = 5

  println("Creating solr client")
  val http2ClientBuilder = new Http2SolrClient.Builder
  http2ClientBuilder.withBasicAuthCredentials(userName, password).build()

  val client:CloudHttp2SolrClient = new CloudHttp2SolrClient.Builder(zkHostList,  Optional.empty())
    .withInternalClientBuilder(http2ClientBuilder)
    .build()

  println("Set a default collection")
  client.setDefaultCollection(collectionName)

  val corpus = List(
    "Illegal request, responding with status '400 Bad Request': Unsupported HTTP method: The HTTP method started with 0x16 rather than any known HTTP method from 10.15.40.249:2480. Perhaps this was an HTTPS request sent to an HTTP endpoint?",
    "MLManager was not able to truncate existing data from AE's operational tables stored in AE_ML_DB in H2. Until old entries are deleted from AE, new model or ae config changes can't be deployed.",
    "Exact reason for the error will be visible in the exception message. Most common reason is unreachable H2 host, either because of misconfiguration or problem with exposed service port. Other possible reasons for this are unreachable H2 host, incorrect H2 config in deployment.json, unavailable H2, change in DDL, timeouts in case of huge data, or referential integrity bugs. Ref integrity errors needs to be assigned to devs. Remaining errors needs to be fixed by devops.",
    "MLManager failed to parse deployment json. Deployment.json located in mlops repo at https://github.com/glassbeam/mlops/blob/master/config/dashboard.json is central file to configure all infra and decide which ML services will be deployed in a given environment. The format of this file is fixed and if you add any new unsupported field or if there is a syntax error, you will get this error. All downstream applications will fail to start until you resolve this issue. The downstream application which are already running will continue to run, but will fail to sync with latest changes, so treat this issue as fatal.",
    "First ensure that deployment.json is in valid JSON format with no syntax issues like missing commas, unclosed brackets. Then ensure that mlmanager support all the fields which are configure in that file. It might be possible that mlmanager is running on old version and doesn't understand latest format of deployment.json. If issue still persist, share mlmanager URL and commit ID of deployment.json or mlops repo which you used while starting mlmanager, with dev.",
    "Prometheus HTTP service couldn't be started for MLManager service. Due to this metrics will not be picked up by central prometheus scraper and alerts will not be raised by alert manager.",
    "Most likely reason for this error is that the port designated for prometheus server is being used by another service. Another less likely reason is host box resource starvation."
  )
  println("Adding document")
  for (i <- 1 to numDocs) {
    val doc = new SolrInputDocument()
    doc.addField("id", s"doc-$i-${System.nanoTime()}")
    client.add(doc)
  }

  println("Committing")
  client.commit()

  println("Closing solr client connection")
  client.close()

  println("Done")

}

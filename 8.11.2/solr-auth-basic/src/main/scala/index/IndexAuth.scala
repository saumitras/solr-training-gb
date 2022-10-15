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

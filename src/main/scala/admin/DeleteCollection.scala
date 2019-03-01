package admin


import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.request.CollectionAdminRequest

object DeleteCollection extends App {

  //create solrcloud client
  println("Connecting to zk")
  val ZK_HOST = "localhost:2181/gb1"
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build

  val deleteReq = CollectionAdminRequest.deleteCollection("col1")

}

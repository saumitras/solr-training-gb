package admin

import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.request.CollectionAdminRequest

object CreateCollection extends App {

  //create solrcloud client
  println("Connecting to zk")
  val ZK_HOST = "localhost:2181/gb1"
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build

  //get collection creation request
  val NEW_COLLECTION_NAME = "col32"
  val CONFIGSET_NAME = "config1"
  val NUM_SHARDS = 1
  val NUM_REPLICAS = 1
  val createReq = CollectionAdminRequest.createCollection(NEW_COLLECTION_NAME, CONFIGSET_NAME, NUM_SHARDS, NUM_REPLICAS)

  //execute creation request
  println("Creating collection. Query = " + createReq.getParams)
  val resp = client.request(createReq)
  println(resp)

}

package admin

import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.request.CollectionAdminRequest
import common.AppConfig

object ReloadCollection extends App {

  //create solrcloud client
  println("Connecting to zk")
  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build

  //get collection reload request
  val createReq = CollectionAdminRequest.reloadCollection(AppConfig.COLLECTION_NAME)

  //execute reload request
  println("Reloading collection. Query: " + createReq.getParams)
  val resp = client.request(createReq)
  println(resp)

  println("Done")

}

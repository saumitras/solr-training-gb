package admin


import common.AppConfig

import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.request.CollectionAdminRequest

object DeleteCollection extends App {

  //create solrcloud client
  println("Connecting to zk")
  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT  //"localhost:2181/gb1"
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build

  //delete collection
  println("Deleting collection")
  val deleteReq = CollectionAdminRequest.deleteCollection(AppConfig.COLLECTION_NAME)
  val status = deleteReq.process(client).isSuccess

  println(s"Deletion status = $status")



}

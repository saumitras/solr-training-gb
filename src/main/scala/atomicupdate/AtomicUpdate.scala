package atomicupdate

import common.AppConfig
import org.apache.solr.client.solrj.impl.{CloudSolrClient, HttpSolrClient}
import org.apache.solr.common.SolrInputDocument

import scala.collection.JavaConverters._

object AtomicUpdate extends App {

  /*val SOLR_CORE_URL = AppConfig.SOLR_HOST + AppConfig.CORE_NAME
  val client = new HttpSolrClient.Builder(SOLR_CORE_URL).build()*/

  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build
  client.setDefaultCollection("so_collection")

  //send doc with all the fields
/*  println("Sending complete doc to solr")
  val doc = new SolrInputDocument()
  doc.setField("id", "atomic1")
  doc.setField("postType", "Q")
  doc.setField("owner", "username123")
  doc.setField("body", "part 1 of body")
  doc.setField("postScore", 1)
  doc.setField("creationDate", "2018-03-10T00:00:00Z")
  doc.setField("lastActivityDate", "2018-03-10T00:00:00Z")
  doc.setField("commentCount", 1)

  client.add(doc)
  client.commit()*/


  //modify only body field of the doc
  println("Sending partial doc to solr")
  val partialDoc = new SolrInputDocument()
  partialDoc.setField("id", "atomic1")
  partialDoc.setField("body", Map("add" -> "part 2 of body field added through partial update").asJava)
  partialDoc.setField("postScore", Map("inc" -> 10).asJava)

  client.add(partialDoc)
  client.commit()

  client.close()
  println("Done")



}

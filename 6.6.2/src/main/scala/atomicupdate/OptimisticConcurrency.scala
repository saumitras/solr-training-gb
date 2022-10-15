package atomicupdate

import common.AppConfig
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.common.SolrInputDocument

object OptimisticConcurrency extends App {

  /*
    - If _version_ > 1,
        then the _version_ in the document must match the _version_ in the index.
    - If _version_ == 1,
        then the document must simply exist. In this case, no version matching occurs, but if the document does not exist, the updates will be rejected.
    - If _version_ < 0,
        then the document must not exist. In this case, no version matching occurs, but if the document exists, the updates will be rejected.
    - If _version_ == '0',
        then it doesn’t matter if the versions match or if the document exists or not.
  */


  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT
  val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build
  client.setDefaultCollection("so_collection")


  //send doc with all the fields
  println("Sending doc with custom version value")
  val doc = new SolrInputDocument()
  doc.setField("id", "versiontest1")
  doc.setField("postType", "Q")
  doc.setField("owner", "username123")
  doc.setField("body", "part 1 of body")
  doc.setField("postScore", 1)
  doc.setField("creationDate", "2018-03-10T00:00:00Z")
  doc.setField("lastActivityDate", "2018-03-10T00:00:00Z")
  doc.setField("commentCount", 1)

  doc.setField("_version_", "0")

  client.add(doc)
  client.commit()

  client.close()
  println("Done")




}

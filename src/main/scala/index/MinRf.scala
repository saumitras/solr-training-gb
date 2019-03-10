package index


import java.sql.Timestamp
import java.text.SimpleDateFormat

import collection.JavaConverters._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.common.SolrInputDocument
import common.AppConfig
import common.Types.StackPost
import org.apache.solr.client.solrj.request.UpdateRequest
import stackexchange.DataStream


object MinRf extends App {

  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT
  val SOLR_CORE_URL = AppConfig.SOLR_HOST + AppConfig.CORE_NAME

  start()

  def start() = {
    println("Creating solr client")
    //create cloud client
    val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build
    client.setDefaultCollection(AppConfig.COLLECTION_NAME)

    //get all posts
    println("Getting all stackoverflow posts")
    val posts = DataStream.fetchAll()

    val docs = posts.map(postToSolrDoc).asJavaCollection

    //creaete an uptateRequest which will return min_rf in response header
    println("Sending posts to solr witn MIN_RF")
    val req = new UpdateRequest()
    req.setParam(UpdateRequest.MIN_REPFACT, "2")
    req.add(docs)
    val resp = client.request(req)
    println("Response: " + resp)

    val replicationAchieved = client.getMinAchievedReplicationFactor(AppConfig.COLLECTION_NAME, resp))
    println("Actual replication achieved: " + replicationAchieved)

    //commit the changes
    println("Committing the changes")
    client.commit()                             //hardcommit

    //close client connection
    println("Closing solr client connection")
    client.close()

    println("Done")
  }



  def postToSolrDoc(p:StackPost) = {

    val doc = new SolrInputDocument()

    //required fields
    doc.setField("id", p.site + p.id)
    doc.setField("postType", if(p.postTypeId == "1") "Q" else "A")
    doc.setField("creationDate", getSolrDate(p.creationDate))
    doc.setField("score", p.score)
    doc.setField("body", p.body)
    doc.setField("owner", p.ownerUserId)
    doc.setField("lastActivityDate", getSolrDate(p.lastActivityDate))
    doc.setField("commentCount", p.commentCount)

    //optional fields
    p.tags.foreach(t => doc.setField("tags", t.asJavaCollection))
    p.parentId.foreach(pid => doc.setField("parentId", pid))

    doc

  }

  def getSolrDate(ts:Timestamp) = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").format(ts)



}
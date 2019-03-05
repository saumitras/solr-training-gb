package index


import java.sql.Timestamp
import java.text.SimpleDateFormat

import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.common.SolrInputDocument
import common.AppConfig
import common.Types.StackPost
import stackexchange.DataStream


object Index extends App {

  val ZK_HOST = AppConfig.ZK_HOST
  val SOLR_CORE_URL = AppConfig.SOLR_HOST + AppConfig.CORE_NAME

  start()

  def start() = {
    println("Creating solr client")
    //create cloud client
    //val client = new CloudSolrClient.Builder().withZkHost(ZK_HOST).build
    //client.setDefaultCollection("so_collection")

    //create single node client
    val client = new HttpSolrClient.Builder(SOLR_CORE_URL).build()

    //get all posts
    println("Getting all stackoverflow posts")
    val posts = DataStream.fetchAll()

    //index all post in solr
    println("Sending posts to solr")

    //send one doc at a time
    /*posts.foreach { p =>
      val solrDoc = postToSolrDoc(p)
      client.add(solrDoc)
    }*/

    //....OR batch multiple docs
    val docs = posts.map(postToSolrDoc)
    client.add(docs)

    //commit the changes
    println("Committing the changes")

    //client.commit()                             //hardcommit
    client.commit(false, false, true)         //softcommit

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
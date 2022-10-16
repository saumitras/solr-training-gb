package index

import org.apache.http.{HttpHost, HttpRequest, HttpRequestInterceptor}
import org.apache.http.auth.{AuthScope, AuthState, Credentials, UsernamePasswordCredentials}
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.protocol.{HttpContext, HttpCoreContext}
import org.apache.solr.client.solrj.impl.{CloudSolrClient, HttpClientUtil}
import org.apache.solr.common.SolrInputDocument

import java.util.Optional
import scala.collection.JavaConverters._

object IndexAuth882 extends App {

  // Before running this, upload security.json (located in home dir of this project) to zookeeper using command below:
  //    bin/solr zk cp ./security.json zk:security.json -z localhost:2181

  val zkHostList = List("localhost:2181").asJava
  val userName = "solr"
  val password = "SolrRocks"
  val collectionName = "test1"
  val numDocs = 5

  println("Add request interceptor")
  HttpClientUtil.addRequestInterceptor(new SolrPreemptiveAuthInterceptor(userName, password))

  println("Creating solr client")
  val client = new CloudSolrClient.Builder(zkHostList, Optional.empty())
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

class SolrPreemptiveAuthInterceptor(username:String, password:String) extends HttpRequestInterceptor {
  override def process(httpRequest: HttpRequest, context: HttpContext): Unit = {
    val authState = context.getAttribute(HttpClientContext.TARGET_AUTH_STATE).asInstanceOf[AuthState]

    if (authState.getAuthScheme == null) {
      val targetHost:HttpHost =  context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST).asInstanceOf[HttpHost]
      val authScope:AuthScope = new AuthScope(targetHost.getHostName, targetHost.getPort)

      val credsProvider = context.getAttribute(HttpClientContext.CREDS_PROVIDER).asInstanceOf[CredentialsProvider]

      var creds = credsProvider.getCredentials(authScope)
      if (creds == null) {
        creds = getCredentials(authScope)
      }
      authState.update(new BasicScheme(), creds)
    }
  }

  def  getCredentials(authScope:AuthScope):Credentials = {
    val creds = new UsernamePasswordCredentials(username, password);

    val credsProvider = new BasicCredentialsProvider()
    credsProvider.setCredentials(authScope, creds)

    credsProvider.getCredentials(authScope)
  }
}

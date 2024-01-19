package index

import index.dv.TextToVector
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.SolrRequest.METHOD
import org.apache.solr.client.solrj.impl.Http2SolrClient
import org.apache.solr.common.SolrInputDocument

import java.sql.Timestamp
import java.text.SimpleDateFormat
import scala.collection.JavaConverters._

object QueryVectors extends App {

  val solrUrl = "http://localhost:8983/solr/llm"
  val client = new Http2SolrClient.Builder(solrUrl).build()

  val queries = List(
    //"H2 is not reachable",
    "metrics server",
  )

  val queriesAsVector = TextToVector.getVectorRepresentationOfTextUsingBERT(queries)

  queriesAsVector.foreach { query =>
    val solrQuery = new SolrQuery()

    val data = query.map(_.toFloat).toList.mkString(",")

    solrQuery.setQuery(s"""{!knn f=data_vector topK=3}[$data]""")


    val response = client.query(solrQuery, METHOD.POST)
    val results = response.getResults

    results.forEach( doc => {
      println(doc.get("data_txt"))
    })

  }

  client.close()

}

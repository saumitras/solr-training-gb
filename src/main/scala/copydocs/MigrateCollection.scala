package copydocs

import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.CloudSolrClient
import org.apache.solr.client.solrj.request.CollectionAdminRequest
import org.apache.solr.common.SolrDocument
import org.apache.solr.common.SolrInputDocument
import scala.collection.JavaConversions._

object MigrateCollection {

  val sourceZk = "localhost:2181"
  val targetZk = "localhost:2182"

  val PAGE_SIZE = 200

  val sourceClient = new CloudSolrClient(sourceZk)
  val targetClient = new CloudSolrClient(targetZk)

  val configSetNames = Map(
    "gdi-gdi-podv10" -> Map("logvault" -> "gdi-gdi-podv10", "section" -> "S---gdi-gdi-podv10", "event" -> "E---gdi-gdi-podv10")
  )

  val collections = List(
    "gdi-gdi-podv10",
    "S---gdi-gdi-podv10___1541116800___1541721599",
    "S---gdi-gdi-podv10___1537488000___1538092799",
    "S---gdi-gdi-podv10___1535068800___1535673599",
    "S---gdi-gdi-podv10___1542326400___1542931199",
    "S---gdi-gdi-podv10___1538697600___1539302399",
    "S---gdi-gdi-podv10___1541721600___1542326399",
    "S---gdi-gdi-podv10___1535673600___1536278399",
    "S---gdi-gdi-podv10___1540512000___1541116799",
    "S---gdi-gdi-podv10___1536278400___1536883199",
    "S---gdi-gdi-podv10___1538092800___1538697599",
    "S---gdi-gdi-podv10___1539302400___1539907199",
    "S---gdi-gdi-podv10___1539907200___1540511999",
    "S---gdi-gdi-podv10___1536883200___1537487999"
  )


  def toSolrInputDocument(solrDocument: SolrDocument) = {
    val solrInputDocument = new SolrInputDocument
    for (name <- solrDocument.getFieldNames) {
      solrInputDocument.addField(name, solrDocument.getFieldValue(name))
    }
    solrInputDocument
  }

  def getConfigName(collection:String) = {
    val MPS = collection.replaceAll("S---","").replaceAll("E---","").replaceAll("___.*","")
    if(collection.startsWith("S---")) {
      configSetNames(MPS)("section")
    } else if(collection.startsWith("E---")) {
      configSetNames(MPS)("event")
    } else if(collection == MPS) {
      configSetNames(MPS)("logvault")
    } else {
      MPS
    }

  }


  def createCollection(collection:String):Boolean = {
    println(s"Creating collection $collection")

    val req = new CollectionAdminRequest.Create()
    req.setCollectionName(collection)
    req.setReplicationFactor(1)
    req.setNumShards(1)
    req.setConfigName(getConfigName(collection))

    val resp =  req.process(targetClient).isSuccess
    println(s"CreationStatus=$resp for collection=$collection")
    resp
  }


  def getDocCount(c:String):Long = {
    println(s"Getting count of docs in $c")
    sourceClient.setDefaultCollection(c)
    val query = new SolrQuery()
    query.setParam("q","*:*")
    query.setRows(0)

    val res = sourceClient.query(query)
    val count = res.getResults.getNumFound

    println(s"NumDocs in $c = $count")
    count
  }

  def copyData(collection:String) = {

    sourceClient.setDefaultCollection(collection)
    targetClient.setDefaultCollection(collection)

    val maxDoc = getDocCount(collection)

    var docCount = 0
    while(docCount < maxDoc) {
      println(s"START=$docCount")
      val query = new SolrQuery()
      query.setParam("q","*:*")
      query.setSort("namespace_id", SolrQuery.ORDER.desc)
      query.setRows(PAGE_SIZE)
      query.setStart(docCount)

      val resp = sourceClient.query(query)
      val docs = resp.getResults.iterator()

      while(docs.hasNext) {
        val doc = docs.next()
        doc.removeFields("_version_")

        targetClient.add(toSolrInputDocument(doc))
      }

      docCount += PAGE_SIZE
    }

    targetClient.commit()

  }


  def start() = {
    collections.foreach { c =>
      println(s"\nProcessing $c...")
      getDocCount(c)
    }
  }


  start()

  targetClient.close()
  sourceClient.close()

}


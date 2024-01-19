package index

import index.dv.TextToVector
import org.apache.solr.client.solrj.impl.{CloudHttp2SolrClient, Http2SolrClient}
import org.apache.solr.common.SolrInputDocument

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util
import java.util.Optional
import scala.collection.JavaConverters._

object IndexVectors extends App {

  val solrUrl = "http://localhost:8983/solr/llm"
  val client = new Http2SolrClient.Builder(solrUrl).build()

  val corpus = List(
    "Illegal request, responding with status '400 Bad Request': Unsupported HTTP method: The HTTP method started with 0x16 rather than any known HTTP method from 10.15.40.249:2480. Perhaps this was an HTTPS request sent to an HTTP endpoint?",
    "MLManager was not able to truncate existing data from AE's operational tables stored in AE_ML_DB in H2. Until old entries are deleted from AE, new model or ae config changes can't be deployed.",
    "Exact reason for the error will be visible in the exception message. Most common reason is unreachable H2 host, either because of misconfiguration or problem with exposed service port. Other possible reasons for this are unreachable H2 host, incorrect H2 config in deployment.json, unavailable H2, change in DDL, timeouts in case of huge data, or referential integrity bugs. Ref integrity errors needs to be assigned to devs. Remaining errors needs to be fixed by devops.",
    "MLManager failed to parse deployment json. Deployment.json located in mlops repo at https://github.com/glassbeam/mlops/blob/master/config/dashboard.json is central file to configure all infra and decide which ML services will be deployed in a given environment. The format of this file is fixed and if you add any new unsupported field or if there is a syntax error, you will get this error. All downstream applications will fail to start until you resolve this issue. The downstream application which are already running will continue to run, but will fail to sync with latest changes, so treat this issue as fatal.",
    "First ensure that deployment.json is in valid JSON format with no syntax issues like missing commas, unclosed brackets. Then ensure that mlmanager support all the fields which are configure in that file. It might be possible that mlmanager is running on old version and doesn't understand latest format of deployment.json. If issue still persist, share mlmanager URL and commit ID of deployment.json or mlops repo which you used while starting mlmanager, with dev.",
    "Prometheus HTTP service couldn't be started for MLManager service. Due to this metrics will not be picked up by central prometheus scraper and alerts will not be raised by alert manager.",
    "Most likely reason for this error is that the port designated for prometheus server is being used by another service. Another less likely reason is host box resource starvation."
  )

  val vectors = TextToVector.getVectorRepresentationOfTextUsingBERT(corpus)

  def getSolrDate(ts:Timestamp) = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").format(ts)

  for (i <- 0 until vectors.size - 1) {
    val doc = new SolrInputDocument()

    val data = vectors(i).map(_.toFloat).toList.asJava

    doc.addField("id", s"doc-$i")
    doc.addField("data_txt", corpus(i))
    doc.addField("data_vector", data)
    doc.addField("addedOn_dt", getSolrDate(new Timestamp(System.currentTimeMillis())))

    println(s"Adding " + doc.getFieldValue("id"))
    client.add(doc)
  }

  println("Committing")
  client.commit()

  println("Closing solr client connection")
  client.close()

  println("Done")

}

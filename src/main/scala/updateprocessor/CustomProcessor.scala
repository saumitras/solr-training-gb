package updateprocessor

import org.apache.solr.request.SolrQueryRequest
import org.apache.solr.response.SolrQueryResponse
import org.apache.solr.update.AddUpdateCommand
import org.apache.solr.update.processor.{UpdateRequestProcessor, UpdateRequestProcessorFactory}

/*
 Add following in solrconfig.xml

 <updateRequestProcessorChain name="customchain" default="true">
   <processor class="com.gb.PopularPostProcessorFactory" >
   <processor class="solr.LogUpdateProcessorFactory" />
   <processor class="solr.RunUpdateProcessorFactory" />
 </updateRequestProcessorChain>

*/

class PopularPostProcessorFactory extends UpdateRequestProcessorFactory {
  override def getInstance(solrQueryRequest: SolrQueryRequest, solrQueryResponse: SolrQueryResponse,
                           next: UpdateRequestProcessor): UpdateRequestProcessor = new PopularPostProcessor(next)
}

class PopularPostProcessor(val next: UpdateRequestProcessor) extends UpdateRequestProcessor(next) {
  override def processAdd(cmd: AddUpdateCommand): Unit = {
    val doc = cmd.getSolrInputDocument
    val v = doc.getFieldValue("postScore")
    if (v != null) {
      val postScore = v.toString.toInt
      if (postScore > 100) doc.addField("isPopular", "true")
    }
    // pass it up the chain
    super.processAdd(cmd)
  }
}


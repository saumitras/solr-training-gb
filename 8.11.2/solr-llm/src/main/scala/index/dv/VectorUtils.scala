package index.dv
import com.johnsnowlabs.nlp.embeddings.{BertEmbeddings, WordEmbeddingsModel}
import com.johnsnowlabs.nlp.{DocumentAssembler, EmbeddingsFinisher}
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.annotators.sbd.pragmatic.SentenceDetector
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object VectorUtils extends App {

  val resp = getVectorRepresentationOfTextUsingBERT("sentence")
//  println(resp(0).mkString(","))
//  println(resp.length)


  def getVectorRepresentationOfTextUsingBERT(text: String) = {
    val spark = SparkSession.builder()
      .appName("BERT Example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val documentAssembler = new DocumentAssembler()
      .setInputCol("text")
      .setOutputCol("document")

    val sentenceDetector = new SentenceDetector()
      .setInputCols("document")
      .setOutputCol("sentence")

    val tokenizer = new Tokenizer()
      .setInputCols("sentence")
      .setOutputCol("token")

    val bertEmbeddings = BertEmbeddings.pretrained("bert_base_uncased", "en")
      .setInputCols("document", "token")
      .setOutputCol("bert")

    val embeddingsFinisher = new EmbeddingsFinisher()
      .setInputCols("bert")
      .setOutputCols("finished_bert")
      .setOutputAsVector(true)

    val pipeline = new Pipeline()
      .setStages(Array(documentAssembler, sentenceDetector, tokenizer, bertEmbeddings, embeddingsFinisher))

    val data = Seq(text).toDF("text")
    val model = pipeline.fit(data)
    val result = model.transform(data)


// This will return an `Array[Seq[Double]]`, where each `Seq[Double]` is the embedding vector for a token in the text. The size of the response vector is the total number of tokens in the text multiplied by the dimension of the embeddings.
//    val vectors = result.select("finished_bert")
//      .collect()
//      .map(row => row.getAs[Seq[DenseVector]](0).map(_.toArray).flatten)
//
// If you want to get a single vector of size 768 for the entire text, you need to aggregate the embeddings of all tokens. One common way to do this is by averaging the embeddings.


// Extract the embeddings from the WordEmbeddings

    val vectors = result.select("finished_bert")
      .collect()
      .flatMap(row => row.getAs[Seq[DenseVector]](0).map(_.toArray))

    // Average the embeddings
    val avgVector = vectors.transpose.map(_.sum / vectors.length)

    avgVector


//    result.select("finished_bert")
//        .collect()
//        .map(row => row.getAs[Seq[DenseVector]](0).map(_.toArray).flatten)
  }

}
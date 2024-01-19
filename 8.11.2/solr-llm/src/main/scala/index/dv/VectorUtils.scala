package index.dv
import com.johnsnowlabs.nlp.embeddings.{BertEmbeddings, WordEmbeddingsModel}
import com.johnsnowlabs.nlp.{DocumentAssembler, EmbeddingsFinisher}
import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.annotators.sbd.pragmatic.SentenceDetector
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object VectorUtils extends App {

  val resp = getVectorRepresentationOfTextUsingBERT("This is a test sentence").mkString("Array(", ", ", ")")
  println(resp)

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

    // Extract the embeddings from the WordEmbeddings
    val vectors = result.select("finished_bert")
      .collect()
      .map(row => row.getAs[Seq[Array[Float]]](0))

    vectors
  }

}
package index.dv

import com.johnsnowlabs.nlp.annotators.Tokenizer
import com.johnsnowlabs.nlp.annotators.sbd.pragmatic.SentenceDetector
import com.johnsnowlabs.nlp.embeddings.BertEmbeddings
import com.johnsnowlabs.nlp.{DocumentAssembler, EmbeddingsFinisher}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.sql.SparkSession

object TextToVector extends App {

  val resp = getVectorRepresentationOfTextUsingBERT(List("sentence1", "sentence2"))
  println(resp)
//  println(resp(0).mkString(","))
//  println(resp.length)


def getVectorRepresentationOfTextUsingBERT(texts: List[String]) = {
  val spark = SparkSession.builder()
    .appName("VectorUtils")
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

  val data = texts.toDF("text")
  val model = pipeline.fit(data)
  val result = model.transform(data)

  val vectors = result.select("finished_bert")
    .collect()
    .flatMap(row => row.getAs[Seq[DenseVector]](0).map(_.toArray))

  // Average the embeddings for each text
  val avgVectors = vectors.grouped(vectors.length / texts.length).map(vecGroup => vecGroup.transpose.map(_.sum / vecGroup.length)).toList

  spark.stop()

  avgVectors
}
}
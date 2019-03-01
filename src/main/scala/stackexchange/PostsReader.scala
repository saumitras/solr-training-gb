package stackexchange

import com.ximpleware.{AutoPilot, VTDGen}
import java.nio.file.{Files, Paths}
import java.sql.Timestamp

import scala.collection.mutable.ListBuffer


case class StackPost(id:String, postTypeId:String, creationDate:String, score:Int, body:String,
                     ownerUserId:String, lastActivityDate:String, commentCount:Int)


object PostsReader extends App {

  val startTime = System.currentTimeMillis()
  val data = Files.readAllBytes(Paths.get("/home/sam/training/solr/datasets/ai/Posts.xml"))
  readFile(data)
  val totalTime = System.currentTimeMillis() - startTime
  println("Time in millisecond = " + totalTime)

  def readFile(data: Array[Byte]):List[StackPost] = {

    val vg = new VTDGen()
    vg.setDoc(data)
    vg.parse(true)
    val vn = vg.getNav()
    val ap = new AutoPilot()
    ap.selectXPath("/posts/row")
    ap.bind(vn)

    var posts = ListBuffer[StackPost]()

    while (ap.evalXPath != -1) {

      try {
        val id = vn.toNormalizedString(vn.getAttrVal("Id"))
        val postTypeId = vn.toNormalizedString(vn.getAttrVal("PostTypeId"))
        val creationDate = vn.toNormalizedString(vn.getAttrVal("CreationDate"))
        val score = vn.toNormalizedString(vn.getAttrVal("Score")).toInt
        val body = vn.toNormalizedString(vn.getAttrVal("Body"))
        val ownerUserId = vn.toNormalizedString(vn.getAttrVal("OwnerUserId"))
        val lastActivityDate = vn.toNormalizedString(vn.getAttrVal("LastActivityDate"))
        val commentCount = vn.toNormalizedString(vn.getAttrVal("CommentCount")).toInt

        val post = StackPost(
          id = id,
          postTypeId = postTypeId,
          creationDate = creationDate,
          score = score,
          body = body,
          ownerUserId = ownerUserId,
          lastActivityDate = lastActivityDate,
          commentCount = commentCount
        )

        println(s"Adding post id = $id")
        posts += post

      } catch  {
        case ex:Exception =>
          println(s"Exception while parsing post. Message=${ex.getMessage}")
      }

    }

    ap.resetXPath

    println(s"Processed ${posts.size} posts")
    posts.toList

  }

}

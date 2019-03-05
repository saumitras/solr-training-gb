package stackexchange

import com.ximpleware.{AutoPilot, VTDGen}
import java.nio.file.{Files, Paths}
import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import common.Types.StackPost

import scala.collection.mutable.ListBuffer

class PostsReader(site:String) extends App {

  //TODO: check for site existence

  def getAllPosts() = {
    getPostData()
  }

  private def getPostData() = {
    println("Reading posts for site=$site")

    val startTime = System.currentTimeMillis()
    val data = Files.readAllBytes(Paths.get(getClass.getResource(s"/dataset/$site/Posts.xml").toURI))
    val posts = processPosts(data)
    val totalTime = System.currentTimeMillis() - startTime
    println(s"Processed ${posts.size} posts in $totalTime ms")
    posts

  }

  private def processPosts(data: Array[Byte]):List[StackPost] = {
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

        val tagsIndex = vn.getAttrVal("Tags")
        val tags = if(tagsIndex == -1) None else
          Some(vn.toNormalizedString(tagsIndex).replaceAll("""&lt;""","").replaceAll("""<""","").split(">").filter(_.nonEmpty).toList)
        
        val parentIdIndex = vn.getAttrVal("ParentId")
        val parentId = if(parentIdIndex == -1) None else Some(vn.toNormalizedString(parentIdIndex))

        val post = StackPost(
          site = site,
          id = id,
          postTypeId = postTypeId,
          creationDate = strToTimestamp(creationDate),
          score = score,
          body = body,
          ownerUserId = ownerUserId,
          lastActivityDate = strToTimestamp(lastActivityDate),
          commentCount = commentCount,
          tags = tags,
          parentId = parentId
        )

        println(s"Adding post id = $id")
        posts += post

      } catch  {
        case ex:Exception =>
          println(s"Exception while parsing post. Message=${ex.getMessage}")
      }

    }

    //ap.resetXPath
    posts.toList

  }


  def strToTimestamp(dateStr:String) = new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS").parse(dateStr).getTime)

}

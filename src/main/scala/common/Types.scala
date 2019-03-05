package common

import java.sql.Timestamp

object Types {

  case class StackPost(site:String, id:String, postTypeId:String, creationDate:Timestamp, score:Int, body:String, ownerUserId:String,
                       lastActivityDate:Timestamp, commentCount:Int, tags:Option[List[String]], parentId:Option[String])
}

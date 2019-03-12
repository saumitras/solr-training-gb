package zk

import org.apache.zookeeper._
import org.apache.zookeeper.KeeperException.NodeExistsException

import scala.collection.JavaConversions._


object ZkOps extends App {

  val ZK_HOST = "localhost"
  val ZK_PORT = 2181

  val watcher = new Watcher {
    override def process(event: WatchedEvent): Unit = {
      if(event.getPath != null) println(s"Watch triggered for path=${event.getPath} and type=${event.getType}")
    }
  }

  println("Create client")
  val client = new ZooKeeper(ZK_HOST, ZK_PORT, watcher)

  println("Create zNodes with some dummy data")

  try {
    client.create("/node1", "some data".getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    client.create("/node1/child1", "some child data".getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
  } catch {
    case ex:NodeExistsException =>
      println("Node already exists. " + ex.getMessage)
    case ex:Exception =>
      ex.printStackTrace()
  }

  println("Get tree for a node")
  ZKUtil.listSubTreeBFS(client, "/node1").toList.foreach(println)

  println("Getting stats of node")
  val stats = client.exists("/node1",false)
  println(s"ctime=${stats.getCtime}, version=${stats.getVersion}, length=${stats.getDataLength}, numChild=${stats.getNumChildren}")


  println("Getting data of node")
  val data = client.getData("/node1", watcher, stats)
  println(new String(data))

  println("Get child of zNode and set a watch")
  val children = client.getChildren("/node1", watcher).toList
  println(children)


  println("Delete the child")
  val pathToDelete = "/node1/child1"
  client.delete(pathToDelete, client.exists(pathToDelete, false).getVersion)

  println("Recursive delete")
  ZKUtil.deleteRecursive(client, "/node1")

  println("Done. Sleeping now.")
  Thread.sleep(300 * 1000)

}

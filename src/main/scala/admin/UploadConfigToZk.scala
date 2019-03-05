package admin

import java.io.File
import java.nio.file.Paths

import common.AppConfig
import org.apache.commons.io.IOUtils

import collection.JavaConverters._
import collection.JavaConversions._
import org.apache.solr.client.solrj.impl.{CloudSolrClient, ZkClientClusterStateProvider}

object UploadConfigToZk extends App {

  //get zk state provider object
  println("Connecting to zk...")
  val ZK_HOST = AppConfig.ZK_HOST.split(",").toSeq.asJavaCollection  //Seq("localhost:2181","localhost:2182","localhost:2183").asJavaCollection
  val CH_ROOT = AppConfig.ZK_CHROOT
  val zkStateManager = new ZkClientClusterStateProvider(ZK_HOST, CH_ROOT)
  zkStateManager.connect()

  //get configSet as Path
  println("Getting configset from resources")
  val CONFIGSET_NAME = "config1"
  val CONFIGSET_LOCATION = "/configset/conf1/"
  val configSetPath = new File("src/main/resources/" + CONFIGSET_LOCATION).toPath

  //upload named configSet
  println("Uploading config")
  zkStateManager.uploadConfig(configSetPath, CONFIGSET_NAME)
  println("Uploaded")

  zkStateManager.close()
  println("End")
}

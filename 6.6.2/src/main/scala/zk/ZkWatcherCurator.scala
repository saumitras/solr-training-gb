package zk

import common.AppConfig
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.framework.recipes.cache.{PathChildrenCache, PathChildrenCacheEvent, PathChildrenCacheListener}
import org.apache.curator.framework.state.{ConnectionState, ConnectionStateListener}
import org.apache.curator.retry.ExponentialBackoffRetry

object ZkWatcherCurator extends App {

  val ZK_HOST = AppConfig.ZK_HOST + AppConfig.ZK_CHROOT
  val retryPolicy = new ExponentialBackoffRetry(1000, 3)
  val zNode = "/node1"

  println("Initialize curator client and wait for it to connect to zk")
  val curator = CuratorFrameworkFactory.newClient(ZK_HOST, retryPolicy)
  curator.start()
  curator.getZookeeperClient.blockUntilConnectedOrTimedOut()

  println("Add listener for client state change")
  curator.getConnectionStateListenable.addListener(new ConnectionStateListener {
    override def stateChanged(client: CuratorFramework, newState: ConnectionState): Unit = {
      println("Curator client state changed. New state: " + newState)
    }
  })

  println("Add pathCacheListener for a node")
  val pathCache = new PathChildrenCache(curator, zNode, true)
  pathCache.start(StartMode.BUILD_INITIAL_CACHE)
  pathCache.getListenable.addListener(new PathChildrenCacheListener {
    override def childEvent(client: CuratorFramework, event: PathChildrenCacheEvent): Unit = {
      println(s"Change type=${event.getType}, path=${event.getData.getPath}")
    }
  })

  println("Done. Sleeping now.")
  Thread.sleep(300 * 1000)

}

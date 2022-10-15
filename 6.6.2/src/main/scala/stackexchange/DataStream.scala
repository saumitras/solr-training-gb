package stackexchange

object DataStream {

  def fetchAll() = {
    val postsReader = new PostsReader("ai")
    postsReader.getAllPosts()
  }

}

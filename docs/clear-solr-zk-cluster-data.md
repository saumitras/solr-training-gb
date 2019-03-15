TO clear zk data
================

- Go to zookeeper data dir
  - cd ~/softwares/zookeeper/data/  //on local/QA
  - cd /home/gbd/zookeeper/data/
- rm -rf version-2
- rm -rf zookeeper_server.pid

**NEVER delete `myid` file**

To clear solr data
==================

- Go to solr data dir
   - cd /home/gbd/solr/data/   							//on production
   - cd /softwares/solr/solr-gb-cloud/server/solr   	//on test/QA/local
- rm -rf *shard*

**NEVER delete `solr.xml` file**
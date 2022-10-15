#### Split the shard

Give `async=<REQUEST_ID>` at the end. `REQUEST_ID` can be any random unique number. 

```
http://localhost:6555/solr/admin/collections?action=splitshard
&collection=E---siemens-siemens-podv50___1557964800___1558223999
&shard=shard1
&async=1000
```

#### Check request status 

````
http://localhost:6555/solr/admin/collections?action=REQUESTSTATUS&requestid=1000
````

```
<response>
<lst name="responseHeader">
    <int name="status">0</int>
    <int name="QTime">2</int>
</lst>
<lst name="status">
    <str name="state">running</str>
    <str name="msg">found [1000] in running tasks</str>
</lst>
</response>
```

Status can also be checked directly in zookeeper by looking at `/overseer/collection-queue-work/` zNode:

```
[zk: localhost:2181(CONNECTED) 4] get /overseer/collection-queue-work/qn-0001292666
{
  "collection":"E---siemens-siemens-podv50___1557964800___1558223999",
  "shard":"shard1",
  "async":"1000",
  "operation":"splitshard"}
cZxid = 0x3001ec164
ctime = Mon May 20 19:55:47 UTC 2019
mZxid = 0x3001ec164
mtime = Mon May 20 19:55:47 UTC 2019
pZxid = 0x3001ec164
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 138
numChildren = 0
```

You can also directly monitor the size of newly created shards to see progress. 

```
gbp@gbp-solr-41:

$ du -sh ~/solr/data/E---siemens-siemens-podv50___1557964800___1558223999_shard1_*
19G	/home/gbp/solr/data/E---siemens-siemens-podv50___1557964800___1558223999_shard1_0_replica1
28K	/home/gbp/solr/data/E---siemens-siemens-podv50___1557964800___1558223999_shard1_1_replica1
73G	/home/gbp/solr/data/E---siemens-siemens-podv50___1557964800___1558223999_shard1_replica1
```

After splitting is done, add a replica for each newly created shard on a *separate machine*:

#### Add replica for each shard on a new node

```
localhost:6555/solr/admin/collections?action=ADDREPLICA
&collection=E---siemens-siemens-podv50___1557964800___1558223999
&shard=shard1_0_0
&node=gbp-solr-42.ec2-east1.glassbeam.com:8983_solr
```

This has to be done for each shard dependending on how many replicas are needed. Mostly there are 2 replicas. So for each shard, execute above query 2 time. Make sure to change `node` for each replica

After replicas are created, delete original shard replicas which are co-located on same machine


#### Delete original shard replicas

Delete those replicas which were created on original machine to distribute the load. Name of replica can be obtained by `clusterstatus` API: `http://localhost:6555/solr/admin/collections?action=clusterstatus&wt=json`

```
localhost:6555/solr/admin/collections?action=DELETEREPLICA
&collection=E---siemens-siemens-podv50___1557964800___1558223999
&shard=shard1_0_1
&replica=core_node5
```

#### Delete inaactive shards

After splitting, source shards status will become inactive. Verify that in `clusterstatus` API response. Delete all inactive shards in the end as cleanup.

```
localhost:6555/solr/admin/collections?action=DELETESHARD
&shard=shard1
&collection=E---siemens-siemens-podv50___1557964800___1558223999
```




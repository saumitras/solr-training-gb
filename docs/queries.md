**Select all**

```http://localhost:8983/solr/so_collection/select?q=*:*```

**Change number of rows returned in result set**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=20```

**Pagination**

```http://localhost:8983/solr/so_collection/select?q=*:*&start=20&rows=10```

**Change fields to be returned**

```http://localhost:8983/solr/so_collection/select?q=*:*&fl=id,body,postScore```

**Search on a single field**

```http://localhost:8983/solr/so_collection/select?q=body:training```

**Search on multiple fields**

```http://localhost:8983/solr/so_collection/select?q=body:"training data" AND postType:A```

```http://localhost:8983/solr/so_collection/select?q=body:"training data" OR postType:A```

**NOT query**

```http://localhost:8983/solr/so_collection/select?q=NOT postType:A```

**Sort by a single field**

```http://localhost:8983/solr/so_collection/select?q=*:*&fl=id,postScore,commentCount&sort=postScore DESC```

**Sort by multiple field**

```http://localhost:8983/solr/so_collection/select?q=*:*&fl=id,postScore,commentCount&sort=postScore DESC,commentCount DESC```


**Facet on single field**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&facet=true&facet.field=postType&facet.field=tags```

**Facet on multiple fields**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&facet=true&facet.field=postType&facet.field=tags```

**Change facet limit**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&facet=true&facet.field=postType&facet.field=tags&facet.limit=10```

**Change facet method**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&facet=true&facet.field=postType&facet.method=enum```

**Get numeric stats of a field**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&stats=true&stats.field=postScore```

**Range query**

```http://localhost:8983/solr/so_collection/select?q=postScore:[1 TO 10]&fl=id,body,postScore```


**Facet range query**

```http://localhost:8983/solr/so_collection/select?q=*:*&rows=0&facet=true&facet.range=creationDate&facet.range.start=2016-03-22T01:33:06Z&facet.range.end=2019-04-01T00:00:00Z&facet.range.gap=%2B1YEAR```

**Fuzzy search**

http://localhost:8983/solr/so_collection/select?q=body:improvee~0.7

**Proximity search**

http://localhost:8983/solr/so_collection/select?q=body:"databases sqlite"~2

**Change query parser**

```http://localhost:8983/solr/so_collection/select?defType=dismax&q=data&qf=body```

**Boosting a keyword**

```http://localhost:8983/solr/so_collection/select?defType=dismax&q=data&qf=body&bq=body:algorithm^5.0```

**Group query**

```http://localhost:8983/solr/so_collection/select?q=*:*&group=true&group.field=postType```

**Terms query**

```http://localhost:8983/solr/so_collection/terms?terms.fl=body&terms.prefix=a&terms.limit=20```

**More-Like-This query**

```http://localhost:8983/solr/so_collection/select?q=id:ai1&mlt.count=5&mlt=true&mlt.fl=body```

**Delete ALL documents**

```http://localhost:8983/solr/so_collection/update?stream.body=<delete><query>*:*</query></delete>&commit=true```


**Delete document by ID**

```http://localhost:8983/solr/so_collection/update?stream.body=<delete><query>id:doc1</query></delete>&commit=true```

**Delete document by QUERY**

```http://localhost:8983/solr/so_collection/update?<delete><query>postType:"Q" OR postScore:[1 TO 10]</query></delete>&commit=true```

**Delete docs for a time range from a single collection**

```http://localhost:8983/solr/so_collection/update?stream.body=<delete><query>creationDate:[2017-03-20T00:00:00Z TO 2017-04-01T23:23:59Z]</query></delete>&commit=true```


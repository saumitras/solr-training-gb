Timerange of collection, defined by **ColSize** H2 config is used to control the size and number of collections.

Below is a generic equation to get initial col-size value for any MPS:

`ColSize = floor(MAX_SHARD_SIZE * NUM_SHARDS / DATA_SIZE_PER_DAY_IN_GB)`

**MAX_SHARD_SIZE** is as follows:
  - 2 for nodes with 8gb RAM
  - 3 for nodes with 16gb RAM
  - 4 for 32gb RAM
  - 5 for >32gb RAM

For example, if MPS is creating 1.2gb index per day, and number of nodes in solr cluster are 4, then colSize config for a machine with 32gb RAM can be calculated as:

  - MAX_SHARD_SIZE_IN_GB = 4
  - NUM_SHARDS = 2 (with replication = 2)
  - Number of days(colSize) = `floor(4 * 2 / 1.2)` = 6 days

Note: If size of an MPS come up less than 2 or greater than 60 days, then it should be reviewed manually.
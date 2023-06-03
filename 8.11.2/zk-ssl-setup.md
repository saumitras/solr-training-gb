## Generate CA, Truststore and Keystore 

**1. Generate CA**
```
openssl req -new -x509 -keyout ca-key -out ca-cert -days 3650
```

**2. Create Truststore**
```
keytool -keystore zookeeper.truststore.jks -alias ca-cert -import -file ca-cert
```

**3. Create Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -validity 3650 -genkey -keyalg RSA -ext SAN=dns:localhost
```

**4. Create certificate signing request (CSR)**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -certreq -file ca-request-zookeeper
```

**5. Sign the CSR**
```
openssl x509 -req -CA ca-cert -CAkey ca-key -in ca-request-zookeeper -out ca-signed-zookeeper -days 3650 -CAcreateserial
```

**6. Import the CA into Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias ca-cert -import -file ca-cert
```

**7. Import the signed certificate from step 5 into Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -import -file ca-signed-zookeeper
```



## zoo.cfg

```
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/home/sam/softwares/solr/ssl/apache-zookeeper-3.6.4-bin/data
autopurge.snapRetainCount=3
autopurge.purgeInterval=12
admin.serverPort=8081

clientPort=2181
secureClientPort=2182
4lw.commands.whitelist=*

authProvider.x509=org.apache.zookeeper.server.auth.X509AuthenticationProvider
serverCnxnFactory=org.apache.zookeeper.server.NettyServerCnxnFactory
ssl.trustStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.truststore.jks
ssl.trustStore.password=secret
ssl.keyStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.keystore.jks
ssl.keyStore.password=secret
ssl.clientAuth=need
```

## zookeeper-client.properties

```
zookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty
zookeeper.ssl.client.enable=true
zookeeper.ssl.protocol=TLSv1.2

zookeeper.ssl.truststore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.truststore.jks
zookeeper.ssl.truststore.password=secret
zookeeper.ssl.keystore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.keystore.jks
zookeeper.ssl.keystore.password=secret
```

## CLIENT_JVMFLAGS to be added in zkCli.sh 

```
export CLIENT_JVMFLAGS="
-Dzookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty 
-Dzookeeper.client.secure=true 
-Dzookeeper.ssl.keyStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.keystore.jks 
-Dzookeeper.ssl.keyStore.password=secret
-Dzookeeper.ssl.trustStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.truststore.jks
-Dzookeeper.ssl.trustStore.password=secret"
```

## Connect and create a test zkNode

When connecting via kafka's zookeeper shell, configure `zookeeper-client.properties` and use command:
```
./zookeeper-shell.sh localhost:2182 -zk-tls-config-file /home/sam/softwares/solr/ssl/apache-zookeeper-3.6.4-bin/conf/zookeeper-client.properties 
```

When connecting via zookeeper's zkCli.sh, add `CLIENT_JVMFLAGS` vars in zkCli.sh and use command:

```
~/softwares/solr/ssl/apache-zookeeper-3.6.4-bin/bin/zkCli.sh -server localhost:2182
```

Verify that writes are working

```
$ ./zookeeper-shell.sh localhost:2182 -zk-tls-config-file /home/sam/softwares/solr/ssl/apache-zookeeper-3.6.4-bin/conf/zookeeper-client.properties 
Connecting to localhost:2182
Welcome to ZooKeeper!
JLine support is disabled

WATCHER::

WatchedEvent state:SyncConnected type:None path:null

ls /
[zookeeper]

create '/sam'
Created /sam

ls /
[sam, zookeeper]

```

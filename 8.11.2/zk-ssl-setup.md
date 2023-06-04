## Generate CA, Truststore and Keystore 

**1. Generate CA**
```
openssl req -new -x509 -keyout ca-key -out ca-cert -days 3650
```

Sample output

```
Generating a 2048 bit RSA private key
.......................................+++
......................................................................................................................................................................+++
writing new private key to 'ca-key'
Enter PEM pass phrase:
Verifying - Enter PEM pass phrase:
-----
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Country Name (2 letter code) [XX]:IN
State or Province Name (full name) []:UP
Locality Name (eg, city) [Default City]:HDI
Organization Name (eg, company) [Default Company Ltd]:GB
Organizational Unit Name (eg, section) []:ML
Common Name (eg, your name or your server's hostname) []:localhost
Email Address []:saumitra.srivastav@glassbeam.com
```

**2. Create Truststore**
```
keytool -keystore zookeeper.truststore.jks -alias ca-cert -import -file ca-cert
```

Sample output

```
$ keytool -keystore sam.truststore.jks -alias ca-cert -import -file ca-cert
Enter keystore password:  
Re-enter new password: 
Owner: EMAILADDRESS=saumitra.srivastav@glassbeam.com, CN=localhost, OU=ML, O=GB, L=HDI, ST=UP, C=IN
Issuer: EMAILADDRESS=saumitra.srivastav@glassbeam.com, CN=localhost, OU=ML, O=GB, L=HDI, ST=UP, C=IN
Serial number: 87079ebccb53a53a
Valid from: Sun Jun 04 05:29:24 UTC 2023 until: Wed Jun 01 05:29:24 UTC 2033
Certificate fingerprints:
	 SHA1: 51:9A:E0:B9:D0:99:BC:F3:7E:3D:8F:DC:A7:83:E7:57:21:8F:AD:41
	 SHA256: D9:51:89:4F:15:F1:AD:A6:C0:E2:B4:90:1A:BF:75:04:2F:1F:11:DC:98:F3:47:6D:1D:DD:31:F0:25:A1:35:56
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3

Extensions: 

#1: ObjectId: 2.5.29.35 Criticality=false
AuthorityKeyIdentifier [
KeyIdentifier [
0000: FA 4B ED B9 47 B4 7A 81   5C 07 CA 4F A6 1C 40 E9  .K..G.z.\..O..@.
0010: 80 52 BB FE                                        .R..
]
]

#2: ObjectId: 2.5.29.19 Criticality=false
BasicConstraints:[
  CA:true
  PathLen:2147483647
]

#3: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: FA 4B ED B9 47 B4 7A 81   5C 07 CA 4F A6 1C 40 E9  .K..G.z.\..O..@.
0010: 80 52 BB FE                                        .R..
]
]

Trust this certificate? [no]:  yes
Certificate was added to keystore

```

**3. Create Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -validity 3650 -genkey -keyalg RSA -ext SAN=dns:localhost
```

Sample output
```
$ keytool -keystore sam.keystore.jks -alias sam -validity 3650 -genkey -keyalg RSA -ext SAN=dns:localhost
Enter keystore password:  
Re-enter new password: 
What is your first and last name?
  [Unknown]:  Saumitra
What is the name of your organizational unit?
  [Unknown]:  ML
What is the name of your organization?
  [Unknown]:  GB
What is the name of your City or Locality?
  [Unknown]:  HDI
What is the name of your State or Province?
  [Unknown]:  UP
What is the two-letter country code for this unit?
  [Unknown]:  IN
Is CN=Saumitra, OU=ML, O=GB, L=HDI, ST=UP, C=IN correct?
  [no]:  yes
```

**4. Create certificate signing request (CSR)**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -certreq -file ca-request-zookeeper
```

Sample output
```
$ keytool -keystore sam.keystore.jks -alias sam -certreq -file ca-request-sam
Enter keystore password:  
```

**5. Sign the CSR**
```
openssl x509 -req -CA ca-cert -CAkey ca-key -in ca-request-zookeeper -out ca-signed-zookeeper -days 3650 -CAcreateserial
```

Sample output
```
$ openssl x509 -req -CA ca-cert -CAkey ca-key -in ca-request-sam -out ca-signed-sam -days 3650 -CAcreateserial
Signature ok
subject=/C=IN/ST=UP/L=HDI/O=GB/OU=ML/CN=Saumitra
Getting CA Private Key
Enter pass phrase for ca-key:
```

**6. Import the CA into Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias ca-cert -import -file ca-cert
```

Sample output
```
$ keytool -keystore sam.keystore.jks -alias ca-cert -import -file ca-cert
Enter keystore password:  
Owner: EMAILADDRESS=saumitra.srivastav@glassbeam.com, CN=localhost, OU=ML, O=GB, L=HDI, ST=UP, C=IN
Issuer: EMAILADDRESS=saumitra.srivastav@glassbeam.com, CN=localhost, OU=ML, O=GB, L=HDI, ST=UP, C=IN
Serial number: 87079ebccb53a53a
Valid from: Sun Jun 04 05:29:24 UTC 2023 until: Wed Jun 01 05:29:24 UTC 2033
Certificate fingerprints:
	 SHA1: 51:9A:E0:B9:D0:99:BC:F3:7E:3D:8F:DC:A7:83:E7:57:21:8F:AD:41
	 SHA256: D9:51:89:4F:15:F1:AD:A6:C0:E2:B4:90:1A:BF:75:04:2F:1F:11:DC:98:F3:47:6D:1D:DD:31:F0:25:A1:35:56
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3

Extensions: 

#1: ObjectId: 2.5.29.35 Criticality=false
AuthorityKeyIdentifier [
KeyIdentifier [
0000: FA 4B ED B9 47 B4 7A 81   5C 07 CA 4F A6 1C 40 E9  .K..G.z.\..O..@.
0010: 80 52 BB FE                                        .R..
]
]

#2: ObjectId: 2.5.29.19 Criticality=false
BasicConstraints:[
  CA:true
  PathLen:2147483647
]

#3: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: FA 4B ED B9 47 B4 7A 81   5C 07 CA 4F A6 1C 40 E9  .K..G.z.\..O..@.
0010: 80 52 BB FE                                        .R..
]
]

Trust this certificate? [no]:  yes
Certificate was added to keystore
You have mail in /var/spool/mail/gbt

```

**7. Import the signed certificate from step 5 into Keystore**
```
keytool -keystore zookeeper.keystore.jks -alias zookeeper -import -file ca-signed-zookeeper
```

Sample output
```
$ keytool -keystore sam.keystore.jks -alias sam -import -file ca-signed-sam
Enter keystore password:  
Certificate reply was installed in keystore
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

## zookeeper-client.properties for kafka zookeeper-connect.sh

```
zookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty
zookeeper.ssl.client.enable=true
zookeeper.ssl.protocol=TLSv1.2

zookeeper.ssl.truststore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.truststore.jks
zookeeper.ssl.truststore.password=secret
zookeeper.ssl.keystore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.keystore.jks
zookeeper.ssl.keystore.password=secret
```

## CLIENT_JVMFLAGS to be added in zookeeper zkCli.sh 

```
export CLIENT_JVMFLAGS="
-Dzookeeper.clientCnxnSocket=org.apache.zookeeper.ClientCnxnSocketNetty 
-Dzookeeper.client.secure=true 
-Dzookeeper.ssl.keyStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.keystore.jks 
-Dzookeeper.ssl.keyStore.password=secret
-Dzookeeper.ssl.trustStore.location=/home/sam/softwares/solr/ssl/store2/ssl/zookeeper.truststore.jks
-Dzookeeper.ssl.trustStore.password=secret"
```

## SOLR_ZK_CREDS_AND_ACLS to be added in solr's zkCli.sh

Location: solr-8.11.2/server/scripts/cloud-scripts/zkcli.sh

```
SOLR_ZK_CREDS_AND_ACLS="
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



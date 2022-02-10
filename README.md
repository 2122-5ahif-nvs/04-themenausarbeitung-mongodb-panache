# MongoDB with Panache (Cluster)

## Setup MongoDB Replica Set

### docker-compose.yml
```dockerfile
version: "2.2"

services:
  mongo1:
    container_name: mongo1
    image: mongo:4.4
    volumes:
      - ~/mongors/data1:/data/db
      - ./rs-init.sh:/scripts/rs-init.sh
    networks:
      - mongors-network
    ports:
      - 27017:27017
    links:
      - mongo2
      - mongo3
    restart: "no"
    entrypoint: [ "/usr/bin/mongod", "--bind_ip_all", "--replSet", "dbrs" ]
  mongo2:
    container_name: mongo2
    image: mongo:4.4
    volumes:
      - ~/mongors/data2:/data/db
    networks:
      - mongors-network
    ports:
      - 27022:27017
    restart: always
    entrypoint: [ "/usr/bin/mongod", "--bind_ip_all", "--replSet", "dbrs" ]
  mongo3:
    container_name: mongo3
    image: mongo:4.4
    volumes:
      - ~/mongors/data3:/data/db
    networks:
      - mongors-network
    ports:
      - 27023:27017
    restart: always
    entrypoint: [ "/usr/bin/mongod", "--bind_ip_all", "--replSet", "dbrs" ]

networks:
  mongors-network:
    driver: bridge
    
```

### rs-init.sh
```shell
#!/bin/bash

mongo <<EOF
var config = {
    "_id": "dbrs",
    "version": 1,
    "members": [
        {
            "_id": 1,
            "host": "mongo1:27017",
            "priority": 3
        },
        {
            "_id": 2,
            "host": "mongo2:27017",
            "priority": 2
        },
        {
            "_id": 3,
            "host": "mongo3:27017",
            "priority": 1
        }
    ]
};
rs.reconfig(config, {"force": true});
rs.status();
EOF
```
### start-db.sh
```shell
#!/bin/bash

docker-compose up -d

sleep 5

docker exec mongo1 /scripts/rs-init.sh
```

## start MongoDB Replica Set
```shell
./start-db.sh
```

Output
```shell
Creating network "04-themenausarbeitung-mongodb-panache_mongors-network" with driver "bridge"
Creating mongo2 ... done
Creating mongo3 ... done
Creating mongo1 ... done
MongoDB shell version v4.4.12
connecting to: mongodb://127.0.0.1:27017/?compressors=disabled&gssapiServiceName=mongodb
Implicit session: session { "id" : UUID("35320c2e-02b7-4882-8d34-c1da97ad8da6") }
MongoDB server version: 4.4.12
{
        "ok" : 1,
        "$clusterTime" : {
                "clusterTime" : Timestamp(1644480913, 1),
                "signature" : {
                        "hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
                        "keyId" : NumberLong(0)
                }
        },
        "operationTime" : Timestamp(1644480657, 1)
}
{
        "set" : "dbrs",
        "date" : ISODate("2022-02-10T11:20:56.595Z"),
        "myState" : 2,
        "term" : NumberLong(94),
        "syncSourceHost" : "",
        "syncSourceId" : -1,
        "heartbeatIntervalMillis" : NumberLong(2000),
        "majorityVoteCount" : 2,
        "writeMajorityCount" : 2,
        "votingMembersCount" : 3,
        "writableVotingMembersCount" : 3,
        "optimes" : {
                "lastCommittedOpTime" : {
                        "ts" : Timestamp(0, 0),
                        "t" : NumberLong(-1)
                },
                "lastCommittedWallTime" : ISODate("1970-01-01T00:00:00Z"),
                "appliedOpTime" : {
                        "ts" : Timestamp(1644480657, 1),
                        "t" : NumberLong(92)
                },
                "durableOpTime" : {
                        "ts" : Timestamp(1644480657, 1),
                        "t" : NumberLong(92)
                },
                "lastAppliedWallTime" : ISODate("2022-02-10T08:10:57.037Z"),
                "lastDurableWallTime" : ISODate("2022-02-10T08:10:57.037Z")
        },
        "lastStableRecoveryTimestamp" : Timestamp(1644480657, 1),
        "members" : [
                {
                        "_id" : 1,
                        "name" : "mongo1:27017",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 5,
                        "optime" : {
                                "ts" : Timestamp(1644480657, 1),
                                "t" : NumberLong(92)
                        },
                        "optimeDate" : ISODate("2022-02-10T08:10:57Z"),
                        "lastAppliedWallTime" : ISODate("2022-02-10T08:10:57.037Z"),
                        "lastDurableWallTime" : ISODate("2022-02-10T08:10:57.037Z"),
                        "syncSourceHost" : "",
                        "syncSourceId" : -1,
                        "infoMessage" : "",
                        "configVersion" : 940893,
                        "configTerm" : -1,
                        "self" : true,
                        "lastHeartbeatMessage" : ""
                },
                {
                        "_id" : 2,
                        "name" : "mongo2:27017",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 0,
                        "optime" : {
                                "ts" : Timestamp(1644480913, 1),
                                "t" : NumberLong(93)
                        },
                        "optimeDurable" : {
                                "ts" : Timestamp(1644480913, 1),
                                "t" : NumberLong(93)
                        },
                        "optimeDate" : ISODate("2022-02-10T08:15:13Z"),
                        "optimeDurableDate" : ISODate("2022-02-10T08:15:13Z"),
                        "lastAppliedWallTime" : ISODate("2022-02-10T08:15:13.760Z"),
                        "lastDurableWallTime" : ISODate("2022-02-10T08:15:13.760Z"),
                        "lastHeartbeat" : ISODate("2022-02-10T11:20:56.594Z"),
                        "lastHeartbeatRecv" : ISODate("1970-01-01T00:00:00Z"),
                        "pingMs" : NumberLong(0),
                        "lastHeartbeatMessage" : "",
                        "syncSourceHost" : "",
                        "syncSourceId" : -1,
                        "infoMessage" : "",
                        "configVersion" : 862820,
                        "configTerm" : 93
                },
                {
                        "_id" : 3,
                        "name" : "mongo3:27017",
                        "health" : 1,
                        "state" : 2,
                        "stateStr" : "SECONDARY",
                        "uptime" : 0,
                        "optime" : {
                                "ts" : Timestamp(1644480913, 1),
                                "t" : NumberLong(93)
                        },
                        "optimeDurable" : {
                                "ts" : Timestamp(1644480913, 1),
                                "t" : NumberLong(93)
                        },
                        "optimeDate" : ISODate("2022-02-10T08:15:13Z"),
                        "optimeDurableDate" : ISODate("2022-02-10T08:15:13Z"),
                        "lastAppliedWallTime" : ISODate("2022-02-10T08:15:13.760Z"),
                        "lastDurableWallTime" : ISODate("2022-02-10T08:15:13.760Z"),
                        "lastHeartbeat" : ISODate("2022-02-10T11:20:56.594Z"),
                        "lastHeartbeatRecv" : ISODate("1970-01-01T00:00:00Z"),
                        "pingMs" : NumberLong(0),
                        "lastHeartbeatMessage" : "",
                        "syncSourceHost" : "",
                        "syncSourceId" : -1,
                        "infoMessage" : "",
                        "configVersion" : 862820,
                        "configTerm" : 93
                }
        ],
        "ok" : 1,
        "$clusterTime" : {
                "clusterTime" : Timestamp(1644480913, 1),
                "signature" : {
                        "hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
                        "keyId" : NumberLong(0)
                }
        },
        "operationTime" : Timestamp(1644480657, 1)
}
bye
```
## Start Quarkus
```shell
./mvnw clean compile quarkus:dev
```

### Execute http Request
in the project root, navigate to [/request/demo.http](/request/demo.http)
```http request
GET http://localhost:8080/persons
Accept: application/json

###

POST http://localhost:8080/persons
Content-Type: application/json

{
  "first": "daniel",
  "last": "düsentrieb"
}

###
```
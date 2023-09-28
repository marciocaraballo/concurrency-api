#! /bin/bash

if [ ! -d "./target" ] 
then
    echo "Build not found, will build target jar"
    mvn package
fi

java -cp target/concurrency-api-1.0-SNAPSHOT.jar algorithms.App
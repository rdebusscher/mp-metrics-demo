#!/usr/bin/env bash

mvn clean package
docker stop service
docker rm service
docker rmi wjax/service
docker build -t wjax/service .
docker run -d -p 8080:8080 --name service --net demo-net wjax/service

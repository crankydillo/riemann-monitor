#!/bin/sh
SHARE=$(pwd)/share
CONTAINER_NAME=riemann

docker run --name $CONTAINER_NAME -v $SHARE:/root/share --net=host riemann-server

docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME

#!/bin/sh
SHARE=$(pwd)/share
CONTAINER_NAME=riemann-server

docker run --name $CONTAINER_NAME -v $SHARE:/root/share --net=host riemann riemann /root/share/riemann.config

docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME

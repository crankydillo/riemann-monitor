#!/bin/sh

# copy-paste:(

SHARE=$(pwd)/share
CONTAINER_NAME=riemann-validate

docker run --name $CONTAINER_NAME -v $SHARE:/root/share riemann riemann test /root/share/riemann.config

docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME

#! /usr/bin/bash

while :
do
	DOCKER_ID=$(docker build -t pka2xml:1.0.0 . && docker run -d -it pka2xml:1.0.0)
	docker exec -it $DOCKER_ID pka2xml -d network.pkt network.xml
	docker cp $DOCKER_ID:/workspace/network.xml ../network.xml
	docker kill $DOCKER_ID
	echo cycle complete
done

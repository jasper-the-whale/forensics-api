#!/usr/bin/env sh

projectName="forensics-api"

./gradlew clean build -x test

docker build -f Dockerfile -t ${projectName} .
docker images

#!/usr/bin/env sh

# Run `chmod +x ./run-local.sh` if running application for fire to modify execution permissions

./gradlew clean build -x test
cd build/libs
java -jar forensics-api.jar
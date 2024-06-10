#!/bin/bash
./gradlew clean build -x test
docker buildx build --platform linux/amd64 --load --tag shinnyriver/taba:0.0.1 .
docker push shinnyriver/taba:0.0.1
#!/bin/bash
echo '1. Builing image'
./build_container.sh

echo '2. Launching test'
ARGS="-m 3 -d /app/keystore_location/dictionary.txt -savetail /app/keystore_location/tail.log 50"
# ARGS="-m 1 -savetail /app/keystore_location/tail.log 50"
./start.sh "$(pwd)/tests/test.keystore" "$ARGS"

echo '3. Removing test container:'
docker container rm keystore-recovery.test.keystore
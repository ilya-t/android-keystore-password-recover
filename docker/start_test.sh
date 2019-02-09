ARGS="-m 3 -d /app/keystore_location/dictionary.txt -savetail /app/keystore_location/tail.log 500"
./start.sh $(pwd)/tests/test.keystore "$ARGS"
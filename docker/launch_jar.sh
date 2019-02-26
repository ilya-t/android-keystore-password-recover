#/bin/ash
KEYSTORE_FILE_NAME=$1
ARGS=$2
LOGTAIL=/app/keystore_location/tail.log
LAST_PASS=$(tail $LOGTAIL -n 100 | grep Current | sed 's/Current Pass: //' | sed 's/ || est.*//' | tail -n 1)
START_ARG=""

if [ "$LAST_PASS" != "" ]; then
    echo "Found last password: '$LAST_PASS'"
    START_ARG="-start '$LAST_PASS'"
fi

echo "java -jar /app/android-keystore-password-recover/build/AndroidKeystoreBrute.jar \
-k /app/keystore_location/$KEYSTORE_FILE_NAME \
$ARGS $START_ARG"
java -jar /app/android-keystore-password-recover/build/AndroidKeystoreBrute.jar \
-k /app/keystore_location/$KEYSTORE_FILE_NAME \
$ARGS $START_ARG
# Usage: pass path to keystore file as first argument!
KEYSTORE_FILE=$1
PATH_TO_KEYSTORE=$(dirname $KEYSTORE_FILE)
KEYSTORE_FILE_NAME=$(basename $KEYSTORE_FILE)
METHOD=1

CONTAINER_COMMAND="java -jar /app/android-keystore-password-recover/build/AndroidKeystoreBrute.jar -m $METHOD -k /app/keystore_location/$KEYSTORE_FILE_NAME -savetail /app/keystore_location/tail.log 50"
CONTAINER_NAME="keystore-recovery.$KEYSTORE_FILE_NAME"

docker run --name $CONTAINER_NAME \
--rm \
--volume $PATH_TO_KEYSTORE:/app/keystore_location \
--interactive \
--tty android-keystore-password-recover:1.07 \
$CONTAINER_COMMAND
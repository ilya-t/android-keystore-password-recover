if [ "$#" -ne 2 ]; then
  echo 'Usage: pass path to keystore file as first argument and' 
  echo 'as second all other arguments for AndroidKeystoreBrute.jar (excluding path to keystore)'
  echo 'For example:'
  echo '    ./start.sh /path/to/keystore.file "-m 1 -savetail /app/keystore_location/tail.log 50"'
  exit 1
fi

KEYSTORE_FILE=$1
ARGS=$2

PATH_TO_KEYSTORE=$(dirname $KEYSTORE_FILE)
KEYSTORE_FILE_NAME=$(basename $KEYSTORE_FILE)

CONTAINER_COMMAND="java -jar /app/android-keystore-password-recover/build/AndroidKeystoreBrute.jar -k /app/keystore_location/$KEYSTORE_FILE_NAME $ARGS"
CONTAINER_NAME="keystore-recovery.$KEYSTORE_FILE_NAME"

docker run --name $CONTAINER_NAME \
--rm \
--volume $PATH_TO_KEYSTORE:/app/keystore_location \
--interactive \
--tty android-keystore-password-recover:1.07 \
$CONTAINER_COMMAND
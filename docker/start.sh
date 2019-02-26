#!/bin/bash
if [ "$#" -ne 2 ]; then
  echo 'Usage: pass path to keystore file as first argument and' 
  echo 'as second all other arguments for AndroidKeystoreBrute.jar'
  echo '(excluding path to keystore and -start that is added automatically).'
  echo 'For example:'
  echo '    ./start.sh /path/to/keystore.file "-m 1 -savetail /app/keystore_location/tail.log 50"'
  exit 1
fi

KEYSTORE_FILE=$1
ARGS=$2

PATH_TO_KEYSTORE=$(dirname $KEYSTORE_FILE)
KEYSTORE_FILE_NAME=$(basename $KEYSTORE_FILE)
IMAGE=android-keystore-password-recover:1.07
CONTAINER_NAME="keystore-recovery.$KEYSTORE_FILE_NAME"

echo "Starting container: $CONTAINER_NAME"
docker run --name $CONTAINER_NAME \
--volume $PATH_TO_KEYSTORE:/app/keystore_location \
--tty \
--interactive \
$IMAGE \
/bin/ash /app/launch_jar.sh $KEYSTORE_FILE_NAME "$ARGS"

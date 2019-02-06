rm -rf ./build
mkdir ./build
set -e
javac -d ./build *.java
cd build/
jar cvfm AndroidKeystoreBrute.jar ../manifest.mf AndroidKeystoreBrute/*.class


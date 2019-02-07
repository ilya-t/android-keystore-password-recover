mkdir tmp
cp -f ../* tmp
rm -rf tmp/docker
docker build . --tag android-keystore-password-recover:1.07
rm -rf ./tmp
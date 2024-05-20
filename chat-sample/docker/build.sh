#!/bin/bash

root_dir=`pwd`
cur_time=$(date "+%Y%m%d_%H%M%S")
rm -rf *.jar

cd $root_dir/../frontend/
frontend_dir=`pwd`
rm -rf dist

cd $root_dir/../backend/
backend_dir=`pwd`
rm -rf target
mkdir -p src/main/resources/static/
mkdir -p src/main/resources/templates/
rm -rf src/main/resources/static/*
rm -rf src/main/resources/templates/*

cd $frontend_dir
npm install
npm run build
cp -r dist/static     $backend_dir/src/main/resources/static/
cp -r dist/index.html $backend_dir/src/main/resources/templates/

cd $backend_dir
mvn package
cp target/chat.jar $root_dir/
cp src/main/resources/application.yml $root_dir/

if [ $1 == "docker" ]; then
  sudo docker build -t chat-sample:v1.0.0 .
fi

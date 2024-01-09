#!/bin/bash

root_dir=`pwd`

#buld frontend
cd ${root_dir}/frontend
npm install
npm run build

#copy frontend to backend
cd ${root_dir}
cp -rf frontend/build/* backend/src/main/resources/static
mv backend/src/main/resources/static/index.html backend/src/main/resources/templates/index.ftl

#build backend
cd ${root_dir}/backend
mvn package

# build docker
cd ${root_dir}
docker build -t chat-sample:1.0.0 .

echo "success to build image"

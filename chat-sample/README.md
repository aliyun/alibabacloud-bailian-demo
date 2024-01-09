# 阿里云百炼大模型对话示例

## 前提条件
1. 安装nodejs 16+, jdk8+

2. 开通阿里云百炼、创建应用。参考阿里云百炼的开发文档，通过阿里云控制台，创建Access Key、Access Secret Key；然后创建应用获取到Agent Key和App Id
   https://help.aliyun.com/document_detail/2400264.html?spm=a2c4g.2400264.0.0.7c2e630bAVIBmR

### 编译和打包

1. 编译前端页面

```
cd frontend
npm install
npm run build
```

2. 将前端页面拷贝至后端工程

```
cp -rf frontend/build/* backend/src/main/resources/static
mv backend/src/main/resources/static/index.html backend/src/main/resources/templates/index.ftl
```

3. 打包后端工程

```
cd backend
mvn package

查看打包应用
ll target/chat-sample-dist.tar.gz
```

### 启动应用

1. 解压安装包

```
tar zxvf chat-sample-dist.tar.gz
```

2. 修改配置, 在conf的application.yml中修改Access Key Id、Access Secret Key、Agent Key和 AppId。确保Access Key ID和Secret安全性
   在启动时, 通过环境变量的方式设置。

3. 启动服务

```
cd chat-sample/bin
export ACCESS_KEY_ID=${ACCESS_KEY_ID} ACCESS_KEY_SECRET=${ACCESS_KEY_SECRET}
./service.sh start
```

4. 停止服务

```
cd chat-sample/bin
./service.sh stop
```

5. 查看日志

```
cd $HOME/chat-sample/logs

#1. 监控日志
tail -f monitor.log

#2. 响应trace日志
tail -f trace.log

#3. 应用日志
tail -f application.log
```

## docker构建和启动

```
./build_docker.sh
docker run -d -p 8080:8080 -e ACCESS_KEY_ID=${ACCESS_KEY_ID} -e ACCESS_KEY_SECRET=${ACCESS_KEY_SECRET} chat-sample:1.0.0
```

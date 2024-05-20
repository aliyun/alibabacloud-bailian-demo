# 阿里云百炼大模型对话示例

## 前提条件
1. 安装nodejs 16+, jdk8+
2. 开通阿里云百炼并创建应用，请参考文档[创建应用](https://help.aliyun.com/document_detail/2782159.html?spm=a2c4g.2400264.0.0.7c2e1ff5T3ztOy)。
3. 获取到API-KEY和APP-ID，请参考文档[获取API-KEY和APP-ID](https://help.aliyun.com/document_detail/2782167.html?spm=a2c4g.2782232.0.0.fd0f7c90FC67Fv)。

### 编译和打包

1. 编译启动前端页面

```
注意: 前后端分离本地测试，需要修改文件frontend/src/components/Index.vue如下
const url = "http://127.0.0.1:8080/v1/completions";
// const url = window.location.protocol + "//" + window.location.host + "/v1/completions";

cd frontend
npm install

# serve with hot reload at localhost:8080
npm run dev
```

2. 启动后端服务
```
注意: 在编译启动后端服务前，先修改application.yml中的appId和apiKey。

cd backend
mvn package
java -jar target/chat-sample.jar
```





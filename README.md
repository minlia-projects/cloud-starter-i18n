# 国际化模块

根据系统不同语种自动返回对应的译文  

当缓存可用时将所有国际化值存入缓存  
不可用时直接从数据库里取出，放入本地guava cache  

## TODO  
添加parameter语言请求参数配置项  

## Done  
Header区域请求头配置项  

## 依赖项目  
cloud-starter-context  

## 添加依赖项:  
```pom
<dependency>
  <groupId>com.minlia.cloud.starter</groupId>
  <artifactId>cloud-starter-i18n</artifactId>
  <version>2.0.0.RELEASE</version>
</dependency>
```
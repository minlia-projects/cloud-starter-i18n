# 国际化模块

根据系统不同语种自动返回对应的译文  

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.minlia.cloud.starter/cloud-starter-stateful/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.minlia.cloud.starter/cloud-starter-i18n/) 
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt) 
[![Build Status](https://travis-ci.org/minlia-projects/cloud-starter-i18n.svg?branch=master)](https://travis-ci.org/minlia-projects/cloud-starter-i18n)
[![Waffle.io - Columns and their card count](https://badge.waffle.io/minlia-projects/cloud-starter-i18n.svg?columns=all)](https://waffle.io/minlia-projects/cloud-starter-i18n)

当缓存可用时将所有国际化值存入缓存  
不可用时直接从数据库里取出，放入本地guava cache  

## TODO  
添加parameter语言请求参数配置项  

## Done  
Header区域请求头配置项  

## 依赖项目  
cloud-starter-context  

## 集成到自已的项目时添加依赖项  
```pom
<dependency>
  <groupId>com.minlia.cloud.starter</groupId>
  <artifactId>cloud-starter-i18n</artifactId>
  <version>2.0.0.RELEASE</version>
</dependency>
```
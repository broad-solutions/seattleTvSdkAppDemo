#seattleTvSdk 广告SDK 对接文档
欢迎使用Broad Solutions开发的广告SDK！本文档将指导您如何在您的应用程序中集成SeattleSdk的广告功能。

## 1. 概述

SeattleSdk广告SDK提供了简单而强大的方式在您OTT盒子的应用程序中显示广告。您可以通过以下步骤将广告集成到您的应用中：

- 添加 SeattleSdk广告SDK 依赖
- 初始化广告SDK
- 请求广告
- 显示广告

## 2. 添加依赖

要开始使用 SeattleSdk广告SDK，首先需要在您的应用程序中添加SDK 的依赖。请将以下依赖添加到您的
build.gradle 文件中：

    dependencies {
             implementation 'com.cloudinfinitegroup:seattle_tv_sdk:1.0'
    }

配置一下maven，此处需要将username和password改成你自己在github上的账号的账号和github token。

    项目根目录下settings.gradle 配置 maven
     pluginManagement {
            repositories {
                google()
                mavenCentral()
                maven {
                 url = uri("https://github.com/broad-solutions/seattleTvSdk/raw/main")
               }
            }
        }
        dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            repositories {
                google()
                mavenCentral()
                maven maven {
                 url = uri("https://github.com/broad-solutions/seattleTvSdk/raw/main")
              }
            }
        }

## 3. 初始化 SDK

在您的应用程序中的合适位置初始化SeattleSdk广告SDK。

- 在使用SDK前，请让贵司的商务先向我们商务代表申请client_id,client_secret。
- 获取TOKEN的方法如下：
  val
  url=http://clientapi.pubadding.com/oauth/token?client_id=$client_id&client_secret=$client_secret&grant_type=client_credentials
  val client = OkHttpClient.Builder()
  .hostnameVerifier { _, _ -> true } // 禁用主机名验证
  .build()
  val request = Request.Builder()
  .url("http://clientapi.pubadding.com/oauth/token?client_id=$client_id&client_secret=$client_secret&grant_type=client_credentials")
  .build()
  取access_token 为token
  {
  access_token: String,
  token_type: String,
  expires_in: Int
  }

## 4. 广告类型和用途说明

- 开屏广告：视频广告，适用于App启动时，在Spash里展现广告内容，此时不需要传入视频链接。
- Banner广告：视频广告，适用于APP进入主界面后，可以在任何位置显示广告，此时不需要传入视频链接。
- 组合广告：视频广告，适用于APP进入主界面后，一般支持视频播放过程中的前、中和后贴广告，所以此时必须传入视频链接，一般适用于播放影视作品时。

### 4.1 开屏广告

    TvAdSdk.setAdType(TvAdSdk.AdType.SPLASH)
    TvAdSdk.sdkStart(_this,"",R.id.SplashAdContaniner,GlobalVariables.token,_this.packageName,R.drawable.splash)

### 4.2 组合广告

    TvAdSdk.setAdType(TvAdSdk.AdType.SECTION)

### 4.3 Banner广告

`TvAdSdk.setAdType(TvAdSdk.AdType.BANNER)`



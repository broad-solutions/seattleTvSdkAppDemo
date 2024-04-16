#TvSdk 广告 SDK 对接文档
欢迎使用 TvSdk 广告 SDK！本文档将指导您如何在您的应用程序中集成 TvSdk 的广告功能。

## 1. 概述

TvSdk 广告 SDK 提供了简单而强大的方式在您的应用程序中显示广告。您可以通过以下步骤将广告集成到您的应用中：

- 添加 TvSdk 广告 SDK 依赖
- 初始化广告 SDK
- 请求广告
- 显示广告

## 2. 添加依赖

要开始使用 TvSdk 广告 SDK，首先需要在您的应用程序中添加 SDK 的依赖。请将以下依赖添加到您的
build.gradle 文件中：

    dependencies {
             implementation 'com.cloudinfinitegroup:seattle_tv_sdk:1.0'
    }

配置一下maven

    项目根目录下settings.gradle 配置 maven
     pluginManagement {
            repositories {
                google()
                mavenCentral()
                maven {
                    url = uri("https://maven.pkg.github.com/broad-solutions/seattleTvSdk")
                    credentials {
                        username = "xinghunbuxiu"
                        password = "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"
                    }
                }
            }
        }
        dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            repositories {
                google()
                mavenCentral()
                maven {
                    url = uri("https://maven.pkg.github.com/broad-solutions/seattleTvSdk")
                    credentials {
                        username = "xinghunbuxiu"
                        password = "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"
                    }
                }
            }
        }

## 3. 初始化 SDK

在您的应用程序中的合适位置 初始化TvSdk 广告 SDK。

- 请向我们申请两个ID (client_id,client_secret)
- 获取TOKEN
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

## 4. 请求和显示广告

- 开屏广告
- 组合广告
- Banner广告

## # 5. 开屏广告

    TvAdSdk.setAdType(TvAdSdk.AdType.SPLASH)
    TvAdSdk.sdkStart(_this,"",R.id.SplashAdContaniner,GlobalVariables.token,_this.packageName,R.drawable.splash)

### 组合广告

    TvAdSdk.setAdType(TvAdSdk.AdType.SECTION)

### Banner广告

`TvAdSdk.setAdType(TvAdSdk.AdType.BANNER)`



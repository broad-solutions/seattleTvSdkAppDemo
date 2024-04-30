#seattleTvSdk 广告SDK 对接文档
欢迎使用Cloud Infinitegroup开发的广告SDK！本文档将指导您如何在您的应用程序中集成SeattleSdk的广告功能。

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
        implementation 'com.cloudinfinitegroup:seattle_tv_sdk:1.1'
    }

maven配置: 项目根目录下settings.gradle

    pluginManagement {
         repositories {
            google()
            mavenCentral()
         }
    }
    dependencyResolutionManagement {
         repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
              repositories {
                  google()
                  mavenCentral()
                  maven {
                      url = uri("https://github.com/broad-solutions/seattleTvSdk/raw/main")
                  }
              }
    }
## 3. 初始化 SDK

在您的应用程序中的合适位置初始化SeattleSdk广告SDK。

- 在使用SDK前，请让贵司的商务先向我们商务代表申请client_id,client_secret。
- 获取TOKEN的方法如下：
```
      TvAdSdk.getAuthorized(client_id, client_secret){ result->
           这里自己解析 取access_token 为token 然后 初始化sdk
           token:刚解析的 token
           packageName 当前的包名
  	   1.0 版本 调用 不需要 Mobile ads 直接用这个初始化
         TvAdSdk.init(token, packageName)
  	   1.1 版本 增加参数  context
  	 TvAdSdk.initV2(this, token, packageName) { result ->
  	   建议在这里等待初始化结果在执行操 
  	 }
      }
      以下是 返回的对象
      {
        access_token: String,
        token_type: String,
        expires_in: Int
      }
```
##4. 在合适的布局布局中增加 TvSdkView TvMobileAdView
```
    <com.cloudinfinitegroup.seattle_tv_sdk.ui.TvSdkView
       android:id="@+id/tvSdkView"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>
	   
	   //1.1 增加Mobile Ads  注意 不支持 Android Tv
    <com.cloudinfinitegroup.seattle_tv_sdk.ui.TvMobileAdView
        android:id="@+id/mobileSdk"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|center"
        android:layout_marginStart="10dp"
        android:layout_marginEnd="10dp"
        android:nextFocusUp="@+id/tv_sdk_view"
        android:descendantFocusability="afterDescendants"
        android:focusable="true"
        android:focusableInTouchMode="true" />
```
### TvSdkView 方法说明

     fun startAd(
            adType: TvAdSdk.AdType = TvAdSdk.AdType.SPLASH,
            contentUrl: String = "",
            repeatMode: Int = RepeatMode.REPEAT_MODE_OFF,
            listener: AdListener? = null
        )
	
    fun pauseAd() {
       暂停播放
    }

    fun resumeAd() {
      重新播放
    }

    fun destroyAd() {
      播放器消亡
    }

    fun setPlayersMuted(muted: Boolean) {
        播放器静音
    }
    adType:
         TvAdSdk.AdType.SPLASH 开屏广告
	    TvAdSdk.AdType.SECTION 组合广告
	    TvAdSdk.AdType.BANNER Banner广告
    contentUrl : 视频链接
    repeatMode:播放模式暂时不需要
    listener:播放监听



###TvMobileAdView 使用说明
```
loadAd {
adUnitId = "/6499/example/banner"  //固定式横幅测试ID
//mobile ads 广告类型
adType = TvAdSdk.MobilAdType.BANNER //目前只支持横幅
// 尺寸说明 具体可参考goggle 的使用说明
https://developers.google.com/ad-manager/mobile-ads-sdk/android/banner?hl=zh-cn
adSize = AdSize
//这里可以增加Mobile ads 横幅的效果 如选中后增加边框
adWarp = {
it.focus("video横幅")
}
}

//可以放入自己的广告
addAdView(focus)
```

###WebSdk使用说明（1.1新增）
```
class AdActivity : AppCompatActivity(), AnalyticsDelegate by AnalyticsDelegateImpl()
//让自己的 Activity 实现 AnalyticsDelegate by AnalyticsDelegateImpl()
val mySdk = SeattleSdk(this)
调用 loadAd 这个方法函数
fun loadAd(
container: ViewGroup,
category: String = "",//跳转的分类可选
otherParam: String = "",//其他参数可选
callback: (String) -> Unit //初始化回调信息
)
```

## 5. 广告类型和用途说明

- 开屏广告：视频广告，适用于App启动时，在Spash里展现广告内容，此时不需要传入视频链接。
- Banner广告：视频广告，适用于APP进入主界面后，可以在任何位置显示广告，此时不需要传入视频链接。
- 组合广告：视频广告，适用于APP进入主界面后，一般支持视频播放过程中的前、中和后贴广告，所以此时必须传入视频链接，一般适用于播放影视作品时。
- 横幅广告：适用于App 的顶部、底部边缘，列表中





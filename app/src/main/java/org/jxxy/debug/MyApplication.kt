package org.jxxy.debug

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.igexin.sdk.PushManager
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener
import kotlinx.coroutines.*
import org.jxxy.debug.common.util.awaitResumed
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.push.card.PushCardManager
import org.jxxy.debug.theme.floatball.service.SuspendWindowService
import org.jxxy.debug.theme.floatball.utils.Utils
import java.lang.ref.WeakReference

// 这个非必要不要动
class MyApplication : BaseApplication() {
    companion object {
        private const val ICON_FONT = "iconfont.ttf"

        // 这个BASE_URL是我自己写的后端项目的网址，等林学长的项目上云后再改成他的
        private const val BASE_URL = "http://120.26.118.142:9988/"
    }

    override fun httpBaseUrl(): String = BASE_URL
    val scope by lazy {
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                if (BuildConfig.DEBUG) {
                    throwable.printStackTrace()
                }
                Logger.e("CoroutineExceptionHandler:${throwable.stackTraceToString()}")
            }
        )
    }

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private val APP_ID = "wx7bb6a1d767108ca1"

    // IWXAPI 是第三方app和微信通信的openApi接口
    private var api: IWXAPI? = null

    override fun iconFontPath(): String = ICON_FONT
    override fun onCreate() {
        super.onCreate()
        webSdkInit()
        pushInit()
        regToWx()
        with(HttpManager.Builder()) {
            baseUrl(httpBaseUrl())
            this.timeout(60)
            HttpManager.init(this)
        }
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                scope.launch {
                    withContext(Dispatchers.Main) {
                        (activity as? LifecycleOwner)?.lifecycle?.awaitResumed()
                        PushCardManager.instance.onActivityResume(activity::class.java.simpleName, WeakReference(activity))
                    }
                }
            }
            override fun onActivityPaused(activity: Activity) {
                PushCardManager.instance.onActivityPause(activity::class.java.simpleName)
            }
            override fun onActivityStopped(activity: Activity) {
            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }
            override fun onActivityDestroyed(activity: Activity) {
            }
        })
        Thread(PythonThread(context())).start()
    }
    private fun webSdkInit() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
//        QbSdk.initX5Environment(
//            context(),
//            object : PreInitCallback {
//                override fun onCoreInitFinished() {
//                    // 内核初始化完成，可能为系统内核，也可能为系统内核
//                    Log.d("corekit", " onViewInitFinished is ")
//                }
//
//                /**
//                 * 预初始化结束
//                 * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
//                 * @param isX5 是否使用X5内核
//                 */
//                override fun onViewInitFinished(p0: Boolean) {
//                    Log.d("corekit", " onViewInitFinished is $p0")
//                }
//            }
//        )
        // 监听内核下载
        QbSdk.setTbsListener(object : TbsListener {
            override fun onDownloadFinish(i: Int) {
                Log.d("duo_shine", "tbs内核下载完成回调: $i")
                // tbs内核下载完成回调
                // 但是只有i等于100才算完成，否则失败
                // 此时大概率可能由于网络问题
                // 如果失败可增加网络监听器
            }

            override fun onInstallFinish(i: Int) {
                Log.d("duo_shine", "内核安装完成回调: $i")
                // 内核安装完成回调，通常到这里也算安装完成，但是在
            }

            override fun onDownloadProgress(i: Int) {
                // 下载进度监听
                Log.d("duo_shine", "下载进度监听: $i")
            }
        })
        // 重置化sdk，这样就清除缓存继续下载了
        QbSdk.setDownloadWithoutWifi(true)
        val map = HashMap<String?, Any?>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        if (PersistenceUtil.getValue<Boolean>("onCoreInitFinished") == false && PersistenceUtil.getValue<Boolean>("onCoreInitFinished") == null) {
            QbSdk.reset(this)
        }
        QbSdk.initX5Environment(
            this,
            object : QbSdk.PreInitCallback {
                /**
                 * 内核初始化完毕
                 */
                override fun onCoreInitFinished() {
                    Log.d("duo_shine", "内核初始化完毕: ")
                }

                /**
                 * WebView验证完毕
                 * 是否使用X5内核，true 表示可以成功使用X5内核，false 表示使用了系统内核
                 * @param b
                 */
                override fun onViewInitFinished(b: Boolean) {
                    Log.d("duo_shine", "WebView验证完毕: $b")
                }
            }
        )
    }

    fun pushInit() {
        PushManager.getInstance().setDebugLogger(this) { s -> Log.i("PUSH_LOG", s)
        Log.d("TESTPUSHTESTPUSH1","$s")}
        PushManager.getInstance().initialize(this)
        val cid = PushManager.getInstance().getClientid(this)
        Log.d("TESTPUSHTESTPUSH2","$cid")
    }

    fun regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true)

        // 将应用的appId注册到微信
        api?.registerApp(APP_ID)

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                // 将该app注册到微信
                api?.registerApp(APP_ID)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))

//        WxShareUtils.shareTextToWx(this,APP_ID,"测试测试", SendMessageToWX.Req.WXSceneSession)
    }
}

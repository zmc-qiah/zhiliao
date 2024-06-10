package org.jxxy.debug.theme.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.load
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.corekit.widget.statusBar.StatusBarUtil
import org.jxxy.debug.theme.AIAssistantSettings
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.databinding.ActivityEmoDetectorBinding
import org.jxxy.debug.theme.dialog.EmoDialog
import org.jxxy.debug.theme.floatball.utils.ViewModleMain
import org.jxxy.debug.theme.voice.VoiceService
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraActivity
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCamera2View
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Core
import org.opencv.core.Mat

class EMODetectorActivity : CameraActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
   private val TAG = "EMODetector"
    private val python: Python by lazy { Python.getInstance()}
    private val model: PyObject by lazy {python.getModule("my_module")}
    private val cameraThread: PyObject by lazy { model["camera"]!! }
    private val view: ActivityEmoDetectorBinding by lazy { bindLayout() }
    private val getEmotion: PyObject by lazy { cameraThread["getDetectCamera"]!!}
    private lateinit var camera: JavaCamera2View
    private var cnt = 0
    private val baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            Log.d(TAG, "onManagerConnected: $status")
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    camera?.let {
                        it.setCvCameraViewListener(this@EMODetectorActivity)
                        it.enableView()
                    }
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        StatusBarUtil.setTranslucentStatus(
            this,
            true
        )
        initView()
        subscribeUi()
    }
     override fun getCameraViewList(): MutableList<out CameraBridgeViewBase> {
        OpenCVLoader.initDebug()
        Log.d(TAG, "getCameraViewList: ")
        val list = ArrayList<CameraBridgeViewBase>()
        list.add(camera)
        return list
    }
    fun bindLayout(): ActivityEmoDetectorBinding {
        return ActivityEmoDetectorBinding.inflate(layoutInflater)
    }
    fun initView() {
        "第一次打开需要进行一定的加载".toast()
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!) {
            this.startService(Intent(this, VoiceService::class.java))
        }
        camera = view.camera
        camera.visibility = CameraBridgeViewBase.VISIBLE
        camera.setCvCameraViewListener(this)
        view.closeBtn.load(ResourceUtil.getString(R.string.url_close))
        view.closeBtn.singleClick {
            val dialog = EmoDialog()
            dialog.show(fragmentManager as FragmentManager)
            dialog.way = {
                finish()
            }
        }
        view.backIcon.singleClick {
            finish()
        }
    }
    fun subscribeUi() {
    }
    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "initDebug: ture")
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            Log.d(TAG, "onResume: fase")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback)
        }
    }
    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        val mgba = inputFrame!!.rgba()
        val mgbaBytes = ByteArray(mgba.width() * mgba.height() * mgba.channels())
        mgba.get(0, 0, mgbaBytes)
        val mGray = inputFrame!!.gray()
        Core.rotate(mgba, mgba, Core.ROTATE_90_COUNTERCLOCKWISE)
        Core.rotate(mGray, mGray, Core.ROTATE_90_COUNTERCLOCKWISE)
        val grayBytes = ByteArray(mGray.width() * mGray.height() * mGray.channels())
        mGray.get(0, 0, grayBytes)
        val index = getEmotion?.call(grayBytes, mGray.width(), mGray.height(), mGray.channels())
        if (index?.toString()?.contains("emoji") ?: false) {
            index?.toString()?.let {
                Log.d(TAG, "onCameraFrame: ${it}")
                GlobalScope.launch(Dispatchers.Main) {
                    a(it)
                }
                AIAssistantSettings.setEmo(it)
            }
        }
        // 前置摄像头
        Core.flip(mgba, mgba, 1);
//        // 后置摄像头
//        Core.rotate(mgba, mgba, Core.ROTATE_90_CLOCKWISE)
//        Core.rotate(mGray, mGray, Core.ROTATE_90_CLOCKWISE)
        return mgba
    }


    override fun onDestroy() {
        super.onDestroy()
        if (camera!=null){
            camera.disableView()
        }
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!) this.stopService(Intent(this, VoiceService::class.java))
    }
    fun a(text:String){
        when(true){
            text.contains("angry")-> {
                view.enjoyIcon.text = ResourceUtil.getString(R.string.emo_a)
            }
            text.contains("happy")-> {
                view.enjoyIcon.text = ResourceUtil.getString(R.string.emo_h)

            }
            text.contains("surprise")-> {
                view.enjoyIcon.text = ResourceUtil.getString(R.string.emo_s)
            }
            text.contains("sad")-> {
                view.enjoyIcon.text = ResourceUtil.getString(R.string.emo_u)
            }
            else -> {
                view.enjoyIcon.text = ResourceUtil.getString(R.string.emo_n)
            }
        }
    }
}
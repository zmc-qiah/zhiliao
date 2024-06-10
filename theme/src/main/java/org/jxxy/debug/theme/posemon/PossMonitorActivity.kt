package org.jxxy.debug.theme.posemon

import android.Manifest
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.SurfaceView
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.addOne
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.databinding.ActivityPoseMonitorBinding
import org.jxxy.debug.theme.floatball.utils.ViewModleMain
import org.jxxy.debug.theme.posemon.camera.CameraSource
import org.jxxy.debug.theme.posemon.data.Camera
import org.jxxy.debug.theme.posemon.data.Device
import org.jxxy.debug.theme.posemon.ml.ModelType
import org.jxxy.debug.theme.posemon.ml.MoveNet
import org.jxxy.debug.theme.posemon.ml.PoseClassifier
import org.jxxy.debug.theme.voice.VoiceService


class PossMonitorActivity : BaseActivity<ActivityPoseMonitorBinding>(){
    companion object {
        private const val FRAGMENT_DIALOG = "dialog"
    }
    /** 为视频画面创建一个 SurfaceView */
    private lateinit var surfaceView: SurfaceView
    /** 修改默认计算设备：CPU、GPU、NNAPI（AI加速器） */
    private var device = Device.GPU
    /** 修改默认摄像头：FRONT、BACK */
    private var selectedCamera = Camera.BACK
    /** 定义几个计数器 */
    private var forwardheadCounter = 0
    private var crosslegCounter = 0
    private var standardCounter = 0
    private var missingCounter = 0
    /** 定义一个历史姿态寄存器 */
    private var poseRegister = "standard"
    /** 设置一个用来显示 Debug 信息的 TextView */
    private lateinit var tvDebug: TextView
    /** 设置一个用来显示当前坐姿状态的 ImageView */
    private lateinit var statusTV: TextView
    private lateinit var tvFPS: TextView
    private lateinit var tvScore: TextView
    private var cameraSource: CameraSource? = null
    private var isClassifyPose = true
    private var isEnd = false
    private val isStartTime = true
    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            seconds++
            updateTimer()
            handler.postDelayed(this, 1000) // 延迟1秒后再次运行
        }
    }
    private lateinit var crosslegPlayer:MediaPlayer
    private lateinit var forwardheadPlayer:MediaPlayer
    private lateinit var standardPlayer:MediaPlayer

    override fun subscribeUi() {
    }
    override fun bindLayout(): ActivityPoseMonitorBinding = ActivityPoseMonitorBinding.inflate(layoutInflater)
    override fun initView() {
        /** 程序运行时保持屏幕常亮 */
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!)                       BaseApplication.context().startService(Intent(this, VoiceService::class.java))

        darkTheme(true)
        tvScore = view.tvScore
        /** 用来显示 Debug 信息 */
        tvDebug = view.tvDebug
        /** 用来显示当前坐姿状态 */
        statusTV = view.statusTV
        tvFPS = view.tvFps
        surfaceView = view.surfaceView
//        initSpinner()
         crosslegPlayer = MediaPlayer().apply {
            setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            val url = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/02/d7828c70-4308-4865-8a05-34ad896a809e.mp3"
            setDataSource(this@PossMonitorActivity, Uri.parse(url));
            prepareAsync();
        }
         forwardheadPlayer = MediaPlayer().apply {
            setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            val url = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/02/def1c566-87ae-496e-b892-7cdd0d3483b6.mp3"
            setDataSource(this@PossMonitorActivity, Uri.parse(url));
            prepareAsync();
        }
         standardPlayer = MediaPlayer().apply {
            setAudioAttributes(AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            val url = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/02/2a5880e8-e63d-4b73-883c-ee83ac3d77a7.mp3"
            setDataSource(this@PossMonitorActivity, Uri.parse(url));
            prepareAsync();
        }
        view.changeDeviceBtn.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked){
                "NNAPI技术在部分机型不设配".toast()
                changeDevice(11)
            }else{
                changeDevice(1)
            }
        }
        view.changeCameraIcon.tag = false
        view.changeCameraIcon.singleClick {
            val tag = it.tag as Boolean
            ObjectAnimator.ofFloat(it, "rotation", 0f, 180f).apply {
                duration = 1000
                interpolator = LinearInterpolator()
            }.start()
            "切换中".toast()
            if (tag){
                changeCamera(0)
            }else{
                changeCamera(1)
            }
            it.tag = !tag
        }
        view.openBtn.tag =true
        view.openBtn.singleClick {
            val tag = it.tag as Boolean
            if (tag){
                it.supportBackgroundTintList = AppCompatResources.getColorStateList(this,org.jxxy.debug.common.R.color.primary_200)
                it.setImageResource(org.jxxy.debug.common.R.drawable.round_white)
                cameraSource?.close()
                cameraSource = null
            }else{
                it.supportBackgroundTintList = AppCompatResources.getColorStateList(this,org.jxxy.debug.common.R.color.white)
                it.setImageResource(org.jxxy.debug.common.R.drawable.round_p_200)
                openCamera()
            }
            it.tag = !tag
        }
        view.cntTV.text = "0"
        if (!isCameraPermissionGranted()) {
            requestPermission1()
        }
        if (!isRecordAudioPermissionGranted()){
            requestRecordAudioPermission()
        }
    }
    private fun isRecordAudioPermissionGranted(): Boolean {
        return checkPermission(
            Manifest.permission.RECORD_AUDIO,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }
    private val requestRecordAudioPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                "录音权限未开启".toast()
            }
        }
    private fun requestRecordAudioPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestRecordAudioPermissionLauncher.launch(permission)
        } else {
            // 权限已经授予，执行需要录音权限的操作
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                /** 得到用户相机授权后，程序开始运行 */
                openCamera()
            } else {
                /** 提示用户“未获得相机权限，应用无法运行” */
                ErrorDialog.newInstance(getString(R.string.tfe_pe_request_permission))
                    .show(supportFragmentManager, FRAGMENT_DIALOG)
            }
        }
    override fun onStart() {
        openCamera()
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!)                        BaseApplication.context().startService(Intent(this, VoiceService::class.java))

        super.onStart()
    }
    override fun onResume() {
        cameraSource?.resume()
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!)                BaseApplication.context().startService(Intent(this, VoiceService::class.java))

        super.onResume()
    }

    override fun onPause() {
        cameraSource?.close()
        cameraSource = null
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!)                            this.stopService(Intent(this, VoiceService::class.java))


        super.onPause()
    }

    /** 检查相机权限是否有授权 */
    private fun isCameraPermissionGranted(): Boolean {
        return checkPermission(
            Manifest.permission.CAMERA,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        /** 音频播放 */
        var crosslegPlayerFlag = true
        var forwardheadPlayerFlag = true
        var standardPlayerFlag = true

        if (isCameraPermissionGranted()) {
            if (cameraSource == null) {
                cameraSource =
                    CameraSource(surfaceView, selectedCamera, object : CameraSource.CameraSourceListener { override fun onFPSListener(fps: Int)
                    {
                            /** 解释一下，tfe_pe_tv 的意思：tensorflow example、pose estimation、text view */
                            tvFPS.text = getString(R.string.tfe_pe_tv_fps, fps)
                        }

                        /** 对检测结果进行处理 */
                        override fun onDetectedInfo(
                            personScore: Float?,
                            poseLabels: List<Pair<String, Float>>?
                        ) {
                            tvScore.text = getString(R.string.tfe_pe_tv_score, personScore ?: 0f)
                            /** 分析目标姿态，给出提示 */
                            if (poseLabels != null && personScore != null && personScore > 0.3) {
                                missingCounter = 0
                                val sortedLabels = poseLabels.sortedByDescending { it.second }
                                when (sortedLabels[0].first) {
                                    "forwardhead" -> {
                                        crosslegCounter = 0
                                        standardCounter = 0
                                        if (poseRegister == "forwardhead") {
                                            forwardheadCounter++
                                        }
                                        poseRegister = "forwardhead"

                                        /** 显示当前坐姿状态：脖子前伸 */
                                        if (forwardheadCounter > 60) {
                                            if (!isEnd) view.cntTV.addOne()
                                            isEnd = true
                                            /** 播放提示音 */
                                            if (forwardheadPlayerFlag) {
                                                forwardheadPlayer.start()
                                            }
                                            standardPlayerFlag = true
                                            crosslegPlayerFlag = true
                                            forwardheadPlayerFlag = false

                                            statusTV.text = ResourceUtil.getString(R.string.forwardhead_confirm)
                                        } else if (forwardheadCounter > 30) {
                                            statusTV.text = ResourceUtil.getString(R.string.forwardhead_not_confirm)
                                        }

                                        /** 显示 Debug 信息 */
                                        tvDebug.text = getString(R.string.tfe_pe_tv_debug, "${sortedLabels[0].first} $forwardheadCounter")
                                    }
                                    "crossleg" -> {
                                        forwardheadCounter = 0
                                        standardCounter = 0
                                        if (poseRegister == "crossleg") {
                                            crosslegCounter++
                                        }
                                        poseRegister = "crossleg"

                                        /** 显示当前坐姿状态：翘二郎腿 */
                                        if (crosslegCounter > 60) {
                                            if (!isEnd) view.cntTV.addOne()
                                            isEnd = true
                                            /** 播放提示音 */
                                            if (crosslegPlayerFlag) {
                                                crosslegPlayer.start()
                                            }
                                            standardPlayerFlag = true
                                            crosslegPlayerFlag = false
                                            forwardheadPlayerFlag = true
                                            statusTV.text = ResourceUtil.getString(R.string.crossleg_confirm)
                                        } else if (crosslegCounter > 30) {
                                            statusTV.text = ResourceUtil.getString(R.string.crossleg_not_confirm)
                                        }

                                        /** 显示 Debug 信息 */
                                        tvDebug.text = getString(R.string.tfe_pe_tv_debug, "${sortedLabels[0].first} $crosslegCounter")
                                    }
                                    else -> {
                                        forwardheadCounter = 0
                                        crosslegCounter = 0
                                        if (poseRegister == "standard") {
                                            standardCounter++
                                        }
                                        poseRegister = "standard"

                                        /** 显示当前坐姿状态：标准 */
                                        if (standardCounter > 30) {
                                            /** 播放提示音：坐姿标准 */
                                            if (isStartTime){
                                                handler.post(runnable)
                                            }
                                            if (isEnd){
                                                resetTimer()
                                                isEnd = false
                                            }
                                            if (standardPlayerFlag) {
                                                standardPlayer.start()
                                            }
                                            standardPlayerFlag = false
                                            crosslegPlayerFlag = true
                                            forwardheadPlayerFlag = true
                                            statusTV.text = ResourceUtil.getString(R.string.standard)
                                        }

                                        /** 显示 Debug 信息 */
                                        tvDebug.text = getString(R.string.tfe_pe_tv_debug, "${sortedLabels[0].first} $standardCounter")
                                    }
                                }
                            }
                            else {
                                missingCounter++
                                if (missingCounter > 30) {
                                    statusTV.text = ResourceUtil.getString(R.string.no_target)
                                }
                                /** 显示 Debug 信息 */
                                tvDebug.text = getString(R.string.tfe_pe_tv_debug, "missing $missingCounter")
                            }
                        }
                    })
                        .apply {
                        prepareCamera()
                    }
                isPoseClassifier()
                lifecycleScope.launch(Dispatchers.Main) {
                    cameraSource?.initCamera()
                }
            }
            createPoseEstimator()
        }

    }

    private fun isPoseClassifier() {
        cameraSource?.setClassifier(if (isClassifyPose) PoseClassifier.create(this) else null)
    }

    /** 在程序运行过程中切换运算设备 */
    private fun changeDevice(position: Int) {
        val targetDevice = when (position) {
            0 -> Device.CPU
            1 -> Device.GPU
            else -> Device.NNAPI
        }
        if (device == targetDevice) return
        device = targetDevice
        createPoseEstimator()
    }

    /** 在程序运行过程中切换摄像头 */
    private fun changeCamera(direaction: Int) {
        val targetCamera = when (direaction) {
            0 -> Camera.BACK
            else -> Camera.FRONT
        }
        if (selectedCamera == targetCamera) return
        selectedCamera = targetCamera

        cameraSource?.close()
        cameraSource = null
        openCamera()
    }

    private fun createPoseEstimator() {
        val poseDetector = MoveNet.create(this, device, ModelType.Thunder)
        poseDetector.let { detector ->
            cameraSource?.setDetector(detector)
        }
    }

    private fun requestPermission1() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                openCamera()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }
    /** 显示报错信息 */
    class ErrorDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(activity)
                .setMessage(requireArguments().getString(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // pass
                }
                .create()
        companion object {

            @JvmStatic
            private val ARG_MESSAGE = "message"

            @JvmStatic
            fun newInstance(message: String): ErrorDialog = ErrorDialog().apply {
                arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止计时器
        handler.removeCallbacks(runnable)
        if (ViewModleMain.isShowSuspendWindow.value == null || !ViewModleMain.isShowSuspendWindow.value!!) this.stopService(Intent(this, VoiceService::class.java))

    }

    private fun updateTimer() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
        view.timeTV.text = formattedTime
    }

    private fun resetTimer() {
        seconds = 0
        updateTimer()
    }
}

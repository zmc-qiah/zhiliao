package org.jxxy.debug.theme.activity

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.adapter.ReadAdapter
import org.jxxy.debug.theme.bean.ReadSomethingInnerResult
import org.jxxy.debug.theme.databinding.ActivityReadPhotoBinding
import org.jxxy.debug.theme.fragment.AiReadThingBottomFragment
import org.jxxy.debug.theme.http.viewModel.OcrViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ReadPhotoActivity : BaseActivity<ActivityReadPhotoBinding>() {

    val viewModel: OcrViewModel by lazy {
        ViewModelProvider(this).get(OcrViewModel::class.java)
    }
    var count = 1
    var type = 1
    override fun bindLayout(): ActivityReadPhotoBinding {
        return ActivityReadPhotoBinding.inflate(layoutInflater)
    }
    var baiKeUrl : String = ""
    lateinit var adapter : ReadAdapter
    override fun initView() {
        view.raedPhotoToolbar.post {
            val positions = IntArray(2)
            view.raedPhotoToolbar.getLocationOnScreen(positions)
            BookMarkUtil.addView(this, positions[1] + view.raedPhotoToolbar.height)
//            BookMarkUtil.addView(this,0)
            BookMarkUtil.get(2,true)
        }
        view.photoIcon.singleClick {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 2);
            setAllisEnabled(false)
            view.readLoading.show()
            view.readLoading.start()
        }

//        view.typeTv.singleClick {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(packageManager) != null) {
//                startActivityForResult(takePictureIntent, 1)
//            }
//            setAllisEnabled(false)
//            view.readLoading.show()
//            view.readLoading.start()
//        }

        view.cameraIcon.singleClick {
            if(type == 1){
                type = 2
                view.nowTypeTv.text = "当前:Ocr"
            }else{
                type = 1
                view.nowTypeTv.text = "当前:识图"
            }
        }

//        view.gotoBaiKeBtn.singleClick {
//            if(baiKeUrl.isNotEmpty()){
//                CommonServiceManager.service<WebService>()?.gotoWebH5(this, baiKeUrl)
////                val intent = Intent(Intent.ACTION_VIEW)
////                intent.setData(Uri.parse(baiKeUrl))
////                startActivity(intent)
//            }else{
//                Toast.makeText(this,"无法识别图片或没有指定百科",Toast.LENGTH_SHORT).show()
//            }

//        }
        view.surfaceview.getHolder().addCallback(cpHolderCallback)

        view.typeTv.singleClick {
            view.readLoading.show()
            view.readLoading.start()
            camera!!.takePicture(null, null) { data, camera ->
                var path: String? = ""
                if (saveFile(data).also { path = it } != null) {
                    Log.d("picPath","$path")
                    viewModel.turnPhotoToUrl(path!!)
                } else {
                    Toast.makeText(this, "保存照片失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun subscribeUi() {
        viewModel.readSomethingLiveData.observe(this) {
            it.onSuccess {
                Log.d("ReadSomething", "${it.toString()}")
                it!!.result[0]?.baike?.baiKeUrl?.let {
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.setData(Uri.parse(it))
//                    startActivity(intent)
                }
                val fragment = AiReadThingBottomFragment(it.result)
                fragment.way = {
                    startPreview()
                }
                fragment.show(supportFragmentManager,"")
//                adapter.clearAndAdd(it.result)
                setAllisEnabled(true)
                view.readLoading.close()
                view.readLoading.hide()
            }
            it.onError { error, response ->
                error?.printStackTrace()
            }
        }
        viewModel.turnPhotoToUrlLiveData.observe(this) {
            it.onSuccess {
                if(type == 1) {
                    viewModel.readSomething(it!!, 1)
                }else{
                    viewModel.ocrUse(it!!)
                }
            }
        }
        viewModel.ocrUseLiveData.observe(this){
            it.onSuccess {
                var string : String = ""
                it?.let {
                    for (i in 0 until it.num){
                        string += it.words[i].words
                    }
                }
                val list = ArrayList<ReadSomethingInnerResult>()
                list.add(ReadSomethingInnerResult(string,"",0.toDouble()))
                val fragment = AiReadThingBottomFragment(list)
                fragment.way = {
                    startPreview()
                }
                stopPreview()
                fragment.show(supportFragmentManager,"")
                setAllisEnabled(true)
                view.readLoading.close()
                view.readLoading.hide()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            1 -> {
//                val photo : Bitmap= data?.getParcelableExtra("data")!!
//                view.aiDrawIv.setImageBitmap(photo);
//            }
//        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
            val file = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image${count++}.jpg")
            val fileOutputStream = FileOutputStream(file)
            Log.d("FilePath",file.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()
            val imageBitmap = data?.extras?.get("data") as Bitmap
            view.readIv.setImageBitmap(imageBitmap)
            viewModel.turnPhotoToUrl(file.path)
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {

            //获取选中文件的定位符
            val uri: Uri = data?.getData()!!
            viewModel.turnPhotoToUrl(Uri2Path(uri)!!)
            //使用content的接口
            val cr = this.getContentResolver()
            try {
                val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                view.readIv.setImageBitmap(bitmap);
            } catch (e: FileNotFoundException) {
            }
        }

        if(resultCode != RESULT_OK){
            setAllisEnabled(true)
            view.readLoading.close()
            view.readLoading.hide()
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private fun Uri2Path(uri: Uri): String? {

        var imagePath: String? = null
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                //Log.d(TAG, uri.toString());
                val id = docId.split(":").toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                //Log.d(TAG, uri.toString());
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            //Log.d(TAG, "content: " + uri.toString());
            imagePath = getImagePath(uri, null)
        }
        return imagePath
    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    fun setAllisEnabled(enabled: Boolean){
        view.typeTv.isEnabled = enabled
        view.photoIcon.isEnabled = enabled
    }

    override fun onDestroy() {
        BookMarkUtil.removeView()
        super.onDestroy()
    }
    private var camera: Camera? = null

    private val cpHolderCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            startPreview()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // 当surface发生任何结构性的变化时（格式或者大小），该方法就会被立即调用
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            stopPreview()
        }
    }

    private fun startPreview() {
        camera = Camera.open()
        val parameters :Camera.Parameters =camera?.getParameters()!!
        parameters.getSupportedPreviewSizes()
        parameters.getSupportedPictureSizes()
        if(parameters.getSupportedPreviewSizes()!=null&& parameters.getSupportedPreviewSizes().size>0){
            parameters.setPreviewSize(parameters.getSupportedPreviewSizes().get(0).width, parameters.getSupportedPreviewSizes().get(0).height);
        }
        Log.d("startPreview","${parameters.getSupportedPreviewSizes().get(0).width}     ${parameters.getSupportedPreviewSizes().get(0).height}")
//        parameters.setPreviewSize(parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).width, parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).height)

        if(parameters.getSupportedPictureSizes()!=null&& parameters.getSupportedPictureSizes().size>0){
            parameters.setPictureSize(parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).width, parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).height);
        }
        parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
        Log.d("startPreview","${parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).width}     ${parameters.getSupportedPictureSizes().get(parameters.getSupportedPictureSizes().size/2).height}")
        try {
            camera?.parameters = parameters
            camera?.setPreviewDisplay(view.surfaceview.holder)
            camera?.setDisplayOrientation(90)
            camera?.startPreview()
            camera?.autoFocus(object : Camera.AutoFocusCallback{
                override fun onAutoFocus(success: Boolean, camera: Camera?) {
                    if(success){
                        camera?.cancelAutoFocus()
                    }
                }
            })
//            camera.cancelAutoFocus()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 停止预览
    private fun stopPreview() {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    private fun saveFile(bytes: ByteArray): String? {
        try {
            val file = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image${count++}.jpg")
            val fos = FileOutputStream(file)
            fos.write(bytes)
            fos.flush()
            fos.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

//    override fun onResume() {
//        startPreview()
//        super.onResume()
//    }

    override fun onPause() {
        stopPreview()
        super.onPause()
    }
}
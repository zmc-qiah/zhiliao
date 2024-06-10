package org.jxxy.debug.common.http.repository

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jxxy.debug.common.http.service.ResourceServiceApi
import org.jxxy.debug.corekit.http.bean.BaseResp
import java.io.File

object ResourceRepository : BaseRepository<ResourceServiceApi>(ResourceServiceApi::class.java) {
    suspend fun addLike(id: Int): BaseResp<Int> = APiService.addLike(value, id)
    suspend fun cancelLike(id: Int): BaseResp<Int> = APiService.cancelLike(value, id)
    suspend fun cancelCollection(id: Int): BaseResp<Int> = APiService.cancelCollection(value, id)
    suspend fun addCollection(id: Int): BaseResp<Int> = APiService.addCollection(value, id)
    suspend fun upLoadIMage(path:String):BaseResp<String> {
        Log.d("TAG", "upLoadIMage1: "+path)
        val file = File(path)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return APiService.uploadImage(imagePart)
    }
}

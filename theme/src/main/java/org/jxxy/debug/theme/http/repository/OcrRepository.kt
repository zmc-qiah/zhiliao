package org.jxxy.debug.theme.http.repository

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.FormBody
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.*
import org.jxxy.debug.theme.http.service.OcrApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OcrRepository {

    val accessToken1 = "24.8ce639355d6867917288685b9ad7cfb0.2592000.1696754515.282335-38624441"
    val accessToken2 = "24.33ad65e2c2c03b6065845d214fec4001.2592000.1696754537.282335-26999725"
    val type = "application/json"
    val service : OcrApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://aip.baidubce.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(OcrApi::class.java)
    }

    suspend fun readSomething(url : String,num : Int) : BaseResp<ReadSomethingResult>{
        var res : ReadSomethingResult? = null
        var bodyBuilder = FormBody.Builder()
        bodyBuilder.add("url",url)
        bodyBuilder.add("baike_num","$num")
        val body = bodyBuilder.build()
        coroutineScope {
            val datas = async {
                service.readSomething(
                    accessToken1,
                    type,
                    body
                )
            }.await()
            Log.d("Read的数据","${datas}")
            datas?.let {
                Log.d("Read的数据","${it}")
                res = it
            }
        }
        Log.d("Embeddings","我进入了")
        Log.d("EmbeddingsRes的数据","$res")
        return BaseResp(0,"", res)
    }

    suspend fun ocrUse(url : String) : BaseResp<OcrRes>{
        var res : OcrRes? = null
        var bodyBuilder = FormBody.Builder()
        bodyBuilder.add("url",url)
        val body = bodyBuilder.build()
        coroutineScope {
            val datas = async {
                service.ocrUse(
                    accessToken2,
                    type,
                    body
                )
            }.await()
            Log.d("Ocr的数据","${datas}")
            datas?.let {
                Log.d("Ocr的数据","${it}")
                res = it
            }
        }
        return BaseResp(0,"", res)
    }
}
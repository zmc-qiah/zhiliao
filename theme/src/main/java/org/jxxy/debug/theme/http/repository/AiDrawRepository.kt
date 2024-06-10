package org.jxxy.debug.theme.http.repository

import org.jxxy.debug.theme.bean.*
import org.jxxy.debug.theme.http.service.AiDrawApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AiDrawRepository {
    val apiKey = "408c1519c1424be57b1a450af53b6eab"
    val service: AiDrawApi by lazy {
        val retrofitAi = Retrofit.Builder()
            .baseUrl("https://ston.6pen.art/release/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitAi.create(AiDrawApi::class.java)
    }

    suspend fun getPictureId(prompt: String): AiDrawResp<AiDrawRes> {
        val body = AiDrawBody(
            prompt,
            3,
            512,
            0,
            AiDrawAddition(cfg_scale = 7, negative_prompt = "minim aliqua qui in sed"),
            512
        )
        return service.getPictureId(apiKey, body)
    }

    suspend fun getPictureById(id: String): AiDrawResp<AiDrawPictureRes> {
        return service.getPictureByID(apiKey,id)
    }
}
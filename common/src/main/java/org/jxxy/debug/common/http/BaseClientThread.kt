package org.jxxy.debug.common.http

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import org.jxxy.debug.corekit.gson.GsonManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

open class BaseClientThread(private val handler: Handler,val ip:String="120.26.118.142",val port:Int = 39000,):Runnable {
    private val TAG = "BaseClientThread"
    private var mSocket: Socket? = null
    private var mBufferedReader: BufferedReader? = null
    private lateinit var mOutputStream: OutputStream
    lateinit var revHandler: Handler
    private var thread:Thread?=null
    private var isReading = true
    var firstMessage :String?=null
    private var isEnd = false
    override fun run() {
        try {
            mSocket = Socket(ip, port)
            mBufferedReader = BufferedReader(InputStreamReader(mSocket!!.getInputStream()))
            mOutputStream = mSocket!!.getOutputStream()
            firstMessage?.let {
                Log.d(TAG, "run: 第一次发送了${it}")
                mOutputStream.write((it + "\n").toByteArray(Charsets.UTF_8))
            }
            // 读取后转发到主线程
            thread= Thread {
                try {
                    var content: String = ""
                    while (isReading && mBufferedReader!!.readLine().also { content = it } != null) {
                        if (content != "error"){
                            val msg = Message.obtain()
                            msg.what = 0
                            msg.obj = content
                            Log.d(TAG, "handleMessage:发给主线程 ${msg.obj}")
                            handler.sendMessage(msg)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            thread!!.start()
            Looper.prepare()
            // 接收到主线程发过来的消息转成json后用socket发到后端
            revHandler = object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    if (msg.what == 0 || msg.what == 1) {
                        try {
                            val gson = GsonManager.instance.gson
                            val json = gson.toJson(msg.obj).replace("\n", "").replace("\r", "")
                            Log.d(TAG, "handleMessage:Socket线程接收到主线程发来的 ${json}")
                            val bytes = (json + "\n").toByteArray(Charsets.UTF_8)

                            mOutputStream.write(bytes)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    if (msg.what == 1){
                        isReading = false
                        mOutputStream.close()
                        mBufferedReader?.close()
                        mSocket?.close()
                    }
                }
            }
            Looper.loop()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "")
        }
    }
    private val lock = Object()
    fun stopThread(last:Any ?= null) {
        if (last == null){
            isEnd = true
        }else {
            synchronized(this){
                val m = Message.obtain()
                m.what = 1
                m.obj = last
                revHandler.sendMessage(m)
            }
        }
    }

}
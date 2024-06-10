package org.jxxy.debug

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import org.jxxy.debug.activity.MainActivity
import org.jxxy.debug.common.service.AppService


@AutoService(AppService::class)
class AppServiceImpl : AppService {
    override fun goHome(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    override fun goMainActivity(context: Context,selected:Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("selected",selected)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    override fun sendReq() {

    }
}

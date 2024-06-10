package org.jxxy.debug.thinkMap

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.service.ThinkMapService
import org.jxxy.debug.thinkMap.activity.ThinkMapActivity

@AutoService(ThinkMapService::class)
class ThinkMapServiceImpl: ThinkMapService {
    override fun drawThinkMap(context: Context, root: Node) {
        val intent = Intent(context,ThinkMapActivity::class.java)
        intent.putExtra("root",root)
        context.startActivity(intent)
    }
    override fun drawThinkMap(context: Context,flag:Int, root: Node) {
        val intent = Intent(context,ThinkMapActivity::class.java)
        intent.addFlags(flag)
        intent.putExtra("root",root)
        context.startActivity(intent)
    }
    override fun goThinkMap(context: Context, id: Int, flag: Int) {
        val intent = Intent(context,ThinkMapActivity::class.java)
        if (flag != -99) intent.addFlags(flag)
        intent.putExtra("rootId",id)
        context.startActivity(intent)    }
}
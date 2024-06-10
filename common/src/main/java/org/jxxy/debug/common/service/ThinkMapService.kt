package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.corekit.common.CommonServiceManager

interface ThinkMapService {
    fun drawThinkMap(context: Context,root: Node = Node("记录灵光瞬间"))
    fun drawThinkMap(context: Context,flag:Int,root: Node = Node("记录灵光瞬间"))
    fun goThinkMap(context: Context,id:Int,flag: Int)
}
fun drawThinkMapNoAI(context: Context,root: Node = Node("记录灵光瞬间")){
    CommonServiceManager.service<ThinkMapService>()?.drawThinkMap(context,root)
}
fun drawThinkMapNoAI(context: Context,flag: Int,root: Node = Node("记录灵光瞬间")){
    CommonServiceManager.service<ThinkMapService>()?.drawThinkMap(context,flag,root)
}
fun goThinkMap(context: Context,id: Int,flag:Int = -99){
    CommonServiceManager.service<ThinkMapService>()?.goThinkMap(context,id,flag)
}


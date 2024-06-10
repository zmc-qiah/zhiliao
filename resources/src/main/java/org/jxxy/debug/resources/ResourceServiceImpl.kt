package org.jxxy.debug.resources

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jxxy.debug.common.service.ResourceService
import org.jxxy.debug.common.service.WebService
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.activity.NewNewResourcesTextActivity
import org.jxxy.debug.resources.activity.NewResourcesVideoActivity
import org.jxxy.debug.resources.activity.ResourceBroadcastActivity
import org.jxxy.debug.resources.activity.ResourceComicActivity
import org.jxxy.debug.resources.http.repository.ResourceRepository
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.util.Mytype

@AutoService(ResourceService::class)
class ResourceServiceImpl : ResourceService {
    val apiService: ResourceRepository by lazy {
        ResourceRepository()
    }
    override fun gotoResourcePage(context: Context, id: Int) {
        GlobalScope.launch {
            val await = async {
                apiService.getResourceById(id)
            }.await()
            await.data.nullOrNot(
                { "资源或网络异常".toast() },
                { gotoResourcePage(context, it) }
            )
        }
    }

    fun gotoResourcePage(context: Context, res: ResourceInfoResponse) {
        when (res.resourceInfo!!.resourceType) {
            Mytype.RESOURCE_VIDEO_INFO -> {
                val intent = Intent(context, NewResourcesVideoActivity::class.java)
                intent.putExtra("resource_info", res)
                context.startActivity(intent)
            }
            Mytype.RESOURCE_TEXT_INFO -> {
                val intent = Intent(context, NewNewResourcesTextActivity::class.java)
                intent.putExtra("resource_info", res)
                context.startActivity(intent)
            }
            Mytype.RESOURCE_H5_INFO ->{
                CommonServiceManager.service<WebService>()?.gotoWebH5(context,res.resourceInfo?.videoUrl!!)
            }
            Mytype.RESOURCE_COMIC_INFO->{
                val intent = Intent(context, ResourceComicActivity::class.java)
                intent.putExtra("resource_info", res)
                context.startActivity(intent)
            }
            Mytype.RESOURCE_BROADCAST_INFO->{
                val intent = Intent(context, ResourceBroadcastActivity::class.java)
                intent.putExtra("resource_info", res)
                context.startActivity(intent)
            }
            else -> {
                "资源异常".toast()
            }
        }
    }
}

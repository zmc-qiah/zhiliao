package orgjxxy.debug.identity


import android.content.Context
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.IdentityService
import org.jxxy.debug.corekit.util.startActivity
import orgjxxy.debug.identity.activity.IdentityStartActivity


@AutoService(IdentityService::class)
class IdentityServiceImpl:IdentityService {
    override fun goSelectIdentity(context: Context?) {
        context?.startActivity<IdentityStartActivity>()
    }
}
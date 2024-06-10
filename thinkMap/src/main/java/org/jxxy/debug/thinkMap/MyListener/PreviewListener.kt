package org.jxxy.debug.thinkMap.MyListener

import android.content.Context
import android.view.View
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.common.util.iBridgeViewLifecycle

class PreviewListener(val context: Context,val list:List<LocalMedia>) : OnItemClickListener {
    override fun onItemClick(v: View?, position: Int) {
        PictureSelector.create(context)
            .openPreview()
            .setImageEngine(GlideEngine.createGlideEngine())
            .setAttachViewLifecycle(iBridgeViewLifecycle)
            .startActivityPreview(position, true, ArrayList<LocalMedia>(list))
    }
    override fun openPicture() {
    }
}
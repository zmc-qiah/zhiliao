package org.jxxy.debug.thinkMap.MyListener

import com.luck.picture.lib.entity.LocalMedia

interface ModifyDialogListener {
    fun submit(text: String, list: List<LocalMedia>)
}

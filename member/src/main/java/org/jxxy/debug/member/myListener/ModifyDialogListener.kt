package org.jxxy.debug.member.myListener

import com.luck.picture.lib.entity.LocalMedia

interface ModifyDialogListener {
    fun submit(text: String, list: List<LocalMedia>)
}

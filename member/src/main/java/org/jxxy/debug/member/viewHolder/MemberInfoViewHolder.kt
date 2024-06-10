package org.jxxy.debug.member.viewHolder

import android.view.View
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.bean.MemberBrief
import org.jxxy.debug.member.databinding.ItemMemberInfoBinding

class MemberInfoViewHolder(binding: ItemMemberInfoBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemMemberInfoBinding, MemberBrief>(binding) {
    override fun setHolder(entity: MemberBrief) {
        entity.let {
            view.userPhotoIV.load(it.headPhoto, true)
            view.userNameTV.text = it.userName
            view.userDescribeTV.text = it.userInfo
            PersistenceUtil.putValue("userName",entity.userName)
            view.settingIcon.singleClick {
                onClick(it)
            }
            view.userDetailBtn.singleClick {
                onClick(it)
            }
            view.userNameTV.singleClick {
                onClick(it)
            }
            view.userDescribeTV.singleClick {
                onClick(it)
            }
            view.userPhotoIV.singleClick {
                onClick(it)
            }
        }
    }
}

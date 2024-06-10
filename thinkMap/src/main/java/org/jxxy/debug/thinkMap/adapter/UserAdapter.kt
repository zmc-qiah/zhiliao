package org.jxxy.debug.thinkMap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.thinkMap.bean.UserInfo
import org.jxxy.debug.thinkMap.bean.UserInfo.Companion.userColorList
import org.jxxy.debug.thinkMap.databinding.ItemUserBinding

class UserAdapter:SingleTypeAdapter<UserInfo>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder?  =
        object:SingleViewHolder<ItemUserBinding,UserInfo>(ItemUserBinding.inflate(inflater,parent,false)){
        override fun setHolder(entity: UserInfo) {
            view.iv.loads(entity.headPicture,true ,context)
            view.iv.background = userColorList[layoutPosition%userColorList.size]
        }
    }
    fun remove(entity: String){
        val i =getPosition(entity)
        if (i == -1)return
        data.removeAt(i)
        notifyItemRemoved(i)
        notifyItemRangeChanged(0,data.size-1)
    }

    fun getPosition(userToken: String): Int {
        return data.indexOfFirst {
            it.token == userToken
        }
    }
}
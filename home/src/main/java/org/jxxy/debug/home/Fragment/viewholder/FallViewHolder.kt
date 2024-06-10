package org.jxxy.debug.home.Fragment.viewholder

import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.Fragment.item.Grid
import org.jxxy.debug.home.Fragment.item.bean.FallBean
import org.jxxy.debug.home.databinding.ComponentFallBinding
import org.jxxy.debug.home.databinding.ComponentGridBinding
import org.jxxy.debug.home.databinding.ItemFallZmcBinding

class FallViewHolder (val binding: ItemFallZmcBinding) :
    MultipleViewHolder<FallBean>(binding.root) {
    val TAG = "FallViewHolder"
    override fun setHolder(entity: FallBean) {
        val fallInfo = entity
        binding.videoTv.text = fallInfo?.title
        binding.videoTv3.text = fallInfo?.createTime
        binding.videoTv2.text = fallInfo?.author
        binding.videoTv4.text = fallInfo?.ip
        Log.d(TAG, "setHolder: ${entity}")
       binding.aIv.load(entity.photo)
        binding.root.singleClick {
            fallInfo?.scheme?.navigation(context)
        }
    }
}
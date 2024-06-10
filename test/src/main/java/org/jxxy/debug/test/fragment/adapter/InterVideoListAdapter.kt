package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ItemInteractionVideoBinding
import org.jxxy.debug.test.fragment.activity.InterVideoActivity
import org.jxxy.debug.test.fragment.bean.QuestionVideo

class InterVideoListAdapter : SingleTypeAdapter<QuestionVideo>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return InterVideoViewHolder(ItemInteractionVideoBinding.inflate(inflater, parent, false))
    }

    inner class InterVideoViewHolder(view: ItemInteractionVideoBinding) :
        SingleViewHolder<ItemInteractionVideoBinding, QuestionVideo>(view) {
        override fun setHolder(entity: QuestionVideo) {
//            view.interVideoIv.load(entity.videoPhoto,12)
//            view.interVideoIv.scaleType = ImageView.ScaleType.FIT_XY
            if (!entity.videoPhoto.equals("")) {
                Glide.with(view.interVideoIv).load(entity.videoPhoto).into(view.interVideoIv)
                view.interVideoTv.text = when (entity.id) {
                    1, 2 -> {
                        view.interVideoTv.setBackgroundResource(R.drawable.textview_inter_video)
                        "简单"
                    }
                    3, 4 -> {
                        view.interVideoTv.setBackgroundResource(R.drawable.textview_inter_video_middel)
                        "普通"
                    }
                    else -> {
                        view.interVideoTv.setBackgroundResource(R.drawable.textview_inter_video_hard)
                        "困难"
                    }
                }
                view.interVideoIv.scaleType = ImageView.ScaleType.FIT_XY
//            view.interVideoTv.text = entity.videoTitle
                view.root.singleClick {
                    val intent = Intent(context, InterVideoActivity::class.java)
                    intent.putExtra("id", entity.id)
                    context.startActivity(intent)
                }
            } else {
                view.root.hide()
            }
        }
    }
}
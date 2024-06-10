package org.jxxy.debug.member.viewHolder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.luck.picture.lib.PictureSelectorPreviewFragment
import com.luck.picture.lib.basic.IBridgeViewLifecycle
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.widget.PreviewTitleBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.ForumActivity
import org.jxxy.debug.member.adapter.PhotoAdapter
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.databinding.ItemForumBinding
import org.jxxy.debug.member.databinding.ItemForumCommentBinding
import org.jxxy.debug.member.databinding.ItemForunPictureBinding
import org.jxxy.debug.member.myListener.ForumClickListener
import org.jxxy.debug.member.myListener.ModifyDialogListener
import org.jxxy.debug.member.myListener.OnItemClickListener

class ForumCommentViewHolder(binding: ItemForumCommentBinding) : SingleViewHolder<ItemForumCommentBinding, Forum>(binding) {
    val adapter by lazy { PhotoAdapter(context,4,2) }
    override fun setHolder(entity: Forum) {
        view.root.singleClick {
            if (entity.flag) return@singleClick
            val intent = Intent(context,ForumActivity::class.java)
            intent.putExtra("entity",entity)
            context.startActivity(intent)
        }
        view.contentTV.text = entity.comment
        view.headPhotoIV.load(entity.headPhoto, true)
        view.nameTV.text = entity.userName
        view.likeTV.text = entity.commentLikes.toString()
        view.createTimeTV.text = entity.createTime
        view.pictureRecyclerView.adapter = adapter
        adapter.clearAndAdd(entity.commentPhotos)
        view.pictureRecyclerView.addItemDecoration(SpanItemDecoration(3f,3f,2))
        view.likeIcon.singleClick(increase = true) {
            if (view.likeIcon.text == ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)) {
                view.likeIcon.text = ResourceUtil.getString(R.string.like_full)
                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_yellow))
                view.likeTV.text = (view.likeTV.text.toString().toInt() + 1).toString()
            } else {
                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.black))
                view.likeTV.text = (view.likeTV.text.toString().toInt() - 1).toString()
            }
        }
        view.contentTV.singleClick {
            entity.resourceId?.let {
                goResource(context, it.toInt())
            }
        }
    }
}

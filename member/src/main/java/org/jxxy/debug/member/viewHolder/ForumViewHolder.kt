package org.jxxy.debug.member.viewHolder

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentManager
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.common.util.addOne
import org.jxxy.debug.common.util.minusOne
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.activity.ForumActivity
import org.jxxy.debug.member.adapter.PhotoAdapter
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.databinding.ItemForumBinding
import org.jxxy.debug.member.myListener.ForumClickListener

class ForumViewHolder(binding: ItemForumBinding, val fragmentManager: FragmentManager,val listener: ForumClickListener) : SingleViewHolder<ItemForumBinding, Forum>(binding) {
    val adapter by lazy { PhotoAdapter(context,4,2) }
    override fun setHolder(entity: Forum) {
        view.root.singleClick {
            val intent = Intent(context,ForumActivity::class.java)
            intent.putExtra("entity",entity)
            context.startActivity(intent)
        }
        view.contentTV.text = entity.comment
        view.headPhotoIV.load(entity.headPhoto, true)
        view.nameTV.text = entity.userName
        view.rescourcesTV.text = entity.resourceTitle
        view.rescourcesIV.load(entity.resourcePhoto,8)
        view.likeTV.text = entity.commentLikes.toString()
        view.createTimeTV.text = entity.createTime
        view.contentRV.adapter = adapter
        Log.d("TAG", "setHolder: ${entity.commentPhotos}")
        adapter.clearAndAdd(entity.commentPhotos)
        if (entity.resourceId==1){
            view.rescourcesView.gone()
            view.rescourcesIV.gone()
            view.rescourcesTV.gone()
        }else{
            view.rescourcesView.show()
            view.rescourcesIV.show()
            view.rescourcesTV.show()
        }
        view.contentRV.addItemDecoration(SpanItemDecoration(3f,3f,2))
        view.likeIcon.tag = false
        view.likeIcon.singleClick {
            if (it.tag as Boolean){
                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.like_not_select))
                view.likeTV.minusOne()
            }else{
                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.like_select))
                view.likeTV.addOne()
            }
            it.tag = !(it.tag as Boolean)
        }
        view.headPhotoIV.singleClick {
            ImageClickDialog(it).show(fragmentManager)
        }
        view.rescourcesIV.singleClick {
            ImageClickDialog(it).show(fragmentManager)
        }
        view.rescourcesView.singleClick {
            entity.resourceId?.let {
                goResource(context, it.toInt())
            }
        }
    }
}

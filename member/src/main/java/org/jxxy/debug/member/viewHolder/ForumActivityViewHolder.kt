package org.jxxy.debug.member.viewHolder

import android.content.Intent
import androidx.fragment.app.FragmentManager
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.ForumActivity
import org.jxxy.debug.member.adapter.PhotoAdapter
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.databinding.ItemForumActivityBinding
import org.jxxy.debug.member.databinding.ItemForumBinding
import org.jxxy.debug.member.myListener.ForumClickListener

class ForumActivityViewHolder(binding: ItemForumActivityBinding, val fragmentManager: FragmentManager) : MultipleViewHolder2<ItemForumActivityBinding, Forum>(binding) {
    val adapter by lazy { PhotoAdapter(context,6,3) }
    override fun setHolder(entity: Forum) {
        view.root.singleClick {
            val intent = Intent(context, ForumActivity::class.java)
            intent.putExtra("entity",entity)
            context.startActivity(intent)
        }
        view.contentTV.text = entity.comment
        view.headPhotoIV.load(entity.headPhoto, true)
        view.nameTV.text = entity.userName
        view.rescourcesTV.text = entity.resourceTitle
        view.rescourcesIV.load(entity.resourcePhoto,6)
        view.createTimeTV.text = entity.createTime
        view.contentRV.adapter = adapter
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
        view.contentTV.singleClick {
            entity.resourceId?.let {
                goResource(context, it.toInt())
            }
        }
    }
}

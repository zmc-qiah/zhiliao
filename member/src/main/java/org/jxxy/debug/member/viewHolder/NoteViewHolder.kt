package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.common.service.goThinkMap
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.bean.Note
import org.jxxy.debug.member.bean.Preview
import org.jxxy.debug.member.databinding.ItemNoteBinding
import org.jxxy.debug.member.databinding.ItemPreviewBinding

class NoteViewHolder(binding: ItemNoteBinding) : MultipleViewHolder2<ItemNoteBinding, Note>(binding) {
    override fun setHolder(entity: Note) {
        view.noteNameTV.text = entity.noteContent
        view.noteSource.text = entity.resourceName
        entity.resourceId?.let { it1 ->
            view.noteSource.singleClick { goResource(context, it1.toInt()) }
        }
    }
}
class PreViewViewHolder(binding: ItemPreviewBinding) : MultipleViewHolder2<ItemPreviewBinding, Preview>(binding) {
    override fun setHolder(entity: Preview) {
        if (entity.isShared == 0 ){
            view.bgIV.gone()
            view.lockIcon.show()
        }else{
//            view.bgIV.load(ResourceUtil.getString(R.string.url_preview))
            view.lockIcon.gone()
//            view.bgIV.show()
        }
        if(entity.photoUrl.isNullOrEmpty()) {
            view.preTV.loads("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/d74d1d18-0703-40bb-9990-324277c27a41.jpg",6,context)
        }else {
            view.preTV.loads(entity.photoUrl, 6, context)
        }
        view.titleTV.text = entity.name
        view.contentTV.text = entity.content
        view.root.singleClick {
            goThinkMap(context,entity.id)
        }
    }
}

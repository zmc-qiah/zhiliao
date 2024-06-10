package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.AIBean
import org.jxxy.debug.theme.bean.AiEmoBean
import org.jxxy.debug.theme.databinding.ItemEchelonBinding

class EmoAdapter:SingleTypeAdapter<AiEmoBean>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = EmoViewHolder(ItemEchelonBinding.inflate(
        inflater, parent,false
    ))
}
class EmoViewHolder(view:ItemEchelonBinding):SingleViewHolder<ItemEchelonBinding,AiEmoBean>(view){
    override fun setHolder(entity: AiEmoBean) {
        when(entity.icon){
            "1" ->{
                view.imgIcon.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/91d01e62-56d7-4107-b1bf-cee1c0ff26f3.png",true)
                view.aIcon.text = ResourceUtil.getString(R.string.emo_a)
                view.aIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.red))
            }
            "2"->{
                view.aIcon.text = ResourceUtil.getString(R.string.emo_u)
                view.aIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200))
                view.imgIcon.load(                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/3dfb3712-8e3c-4f5b-97f4-7bf397bd44f3.png"
                    ,true)
            }
            "3"->{
                view.aIcon.text = ResourceUtil.getString(R.string.emo_s)
                view.aIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.purple_500))
                view.imgIcon.load(                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/8bd46375-a5fb-4a52-8ffa-e630c8fa10a8.png"
                    ,true)
            }
            "4"->{
//                view.imgIcon.load(                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/3dfb3712-8e3c-4f5b-97f4-7bf397bd44f3.png"
//                    ,true)
            }
            "5"->{
//                view.imgIcon.load(                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/3dfb3712-8e3c-4f5b-97f4-7bf397bd44f3.png"
//                    ,true)
            }
        }
        view.imgBg.load(entity.photo,16)
        view.tvNickname.text = entity.data
        view.tvDesc.text = entity.content
    }
}
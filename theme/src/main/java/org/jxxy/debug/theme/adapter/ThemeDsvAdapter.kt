package org.jxxy.debug.theme.adapter

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.scheme.SchemeAI
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.widget.IconFont
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.AIBean
import org.jxxy.debug.theme.databinding.ItemCityCardBinding

class ThemeDsvAdapter(val listener:ItemClickListener?=null):SingleTypeAdapter<AIBean>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? =ThemeDsvViewHolder(
    ItemCityCardBinding.inflate(inflater,parent,false),listener
    )
}
class ThemeDsvViewHolder(itemView: ItemCityCardBinding,val listener:ItemClickListener?) : SingleViewHolder<ItemCityCardBinding,AIBean>(itemView){
    val imageView: IconFont
    val textView: TextView
    init {
        imageView = view.cityImage
        textView = view.cityName
    }
    override fun setHolder(entity: AIBean) {
        textView.text = entity.name
        entity.scheme as SchemeAI
        when(entity.scheme.aiType){
            SchemeAI.ADV->{
                imageView.text = ResourceUtil.getString(R.string.theme_adv)
            }
            SchemeAI.OCR->{
                imageView.text = ResourceUtil.getString(R.string.theme_ocr)
            }
            SchemeAI.EMO->{
                imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP,45f)
                imageView.text = ResourceUtil.getString(R.string.theme_emo)
            }
            SchemeAI.POSE->{
                imageView.text = ResourceUtil.getString(R.string.theme_pose)
            }
            SchemeAI.KNOWLEDGE->{
                imageView.text = ResourceUtil.getString(R.string.theme_kn)
            }
            SchemeAI.NAVIGATION->{
                imageView.text = ResourceUtil.getString(R.string.theme_na)
            }
            SchemeAI.PAINT->{
                imageView.text = ResourceUtil.getString(R.string.theme_paint)
            }
            else->{}
        }
        view.cityImage.singleClick {
            Log.d("sdaas", "aa: ")
            listener?.onClick(layoutPosition,entity.scheme)
        }
    }
    fun showText() {
        imageView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
        val parentHeight = (imageView.parent as View).height
        val scale = (parentHeight - textView.height) / imageView.height.toFloat()
        imageView.pivotX = imageView.width * 0.5f
        imageView.pivotY = 0f
        imageView.animate().scaleX(scale)
            .withEndAction {
                textView.visibility = View.VISIBLE
            }
            .scaleY(scale).setDuration(200)
            .start()
    }

    fun hideText() {
        imageView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.dark_grey))
        textView.visibility = View.INVISIBLE
        imageView.animate().scaleX(1f).scaleY(1f)
            .setDuration(200)
            .start()
    }

}
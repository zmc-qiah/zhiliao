package org.jxxy.debug.search.holder
import android.graphics.Color
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.search.bean.SearchEndBook
import org.jxxy.debug.search.databinding.ItemSearchEnd1Binding


class SearchEndBookHolder(val binding: ItemSearchEnd1Binding) :
    MultipleViewHolder<SearchEndBook>(binding.root) {
    private var like: Int = 123
    private var flag: Boolean = true

    override fun setHolder(entity: SearchEndBook) {
        binding.textTv.text = entity.text
        binding.imageImg.load(entity.imageID, 15)
        binding.textTv3.text = entity.text2

        itemView.singleClick {
            entity.schme.navigation(context)
        }

        binding.likenumTv.setOnClickListener() {

            if (flag == true) {
                flag = false
                ++like
                binding.likenumTv.text = like.toString()
                binding.likenumTv.setTextColor(Color.parseColor("#ff669a"))
                binding.likeImage.setTextColor(Color.parseColor("#ff669a"))
            } else {
                flag = true
                --like
                binding.likenumTv.text = like.toString()
                binding.likenumTv.setTextColor(Color.parseColor("#838083"))
                binding.likeImage.setTextColor(Color.parseColor("#838083"))
            }

        }
        binding.likeImage.setOnClickListener() {

            if (flag == true) {
                flag = false
                ++like
                binding.likenumTv.text = like.toString()
                binding.likenumTv.setTextColor(Color.parseColor("#ff669a"))
                binding.likeImage.setTextColor(Color.parseColor("#ff669a"))
            } else {
                flag = true
                --like
                binding.likenumTv.text = like.toString()
                binding.likenumTv.setTextColor(Color.parseColor("#838083"))
                binding.likeImage.setTextColor(Color.parseColor("#838083"))
            }
        }

    }
}

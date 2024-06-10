package org.jxxy.debug.search.holder

import android.content.Intent
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.activity.ProblemActivity
import org.jxxy.debug.search.activity.SearchMiddleActivity
import org.jxxy.debug.search.bean.SearchEndProblem
import org.jxxy.debug.search.databinding.ItemSearchEnd2Binding

class SearchEndProblemHolder(val binding: ItemSearchEnd2Binding) : MultipleViewHolder<SearchEndProblem>(binding.root) {
    override fun setHolder(entity: SearchEndProblem) {
        binding.textTv.text=entity.text
        binding.titleTv.text=entity.title
        itemView.setOnClickListener(){
            context?.startActivity<ProblemActivity>()


        }

    }

}
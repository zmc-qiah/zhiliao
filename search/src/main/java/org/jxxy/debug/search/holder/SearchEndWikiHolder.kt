package org.jxxy.debug.search.holder


import android.content.Intent
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.activity.ProblemActivity
import org.jxxy.debug.search.activity.SearchMiddleActivity
import org.jxxy.debug.search.activity.WikiActivity
import org.jxxy.debug.search.bean.SearchEndWiki

import org.jxxy.debug.search.databinding.ItemSearchEnd0Binding


class SearchEndWikiHolder(val binding: ItemSearchEnd0Binding) : MultipleViewHolder<SearchEndWiki>(binding.root) {
    override fun setHolder(entity: SearchEndWiki) {
        binding.titleTv.text=entity.title
        binding.textTv.text=entity.text
        itemView.setOnClickListener(){
            context?.startActivity<WikiActivity>()

        }

    }


}
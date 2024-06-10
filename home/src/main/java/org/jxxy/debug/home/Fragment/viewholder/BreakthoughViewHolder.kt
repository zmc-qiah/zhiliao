package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.home.Fragment.adapter.BreakthoughAdapter
import org.jxxy.debug.home.Fragment.decoration.GridSpacingItemDecoration
import org.jxxy.debug.home.Fragment.decoration.SlideBarItemDecoration
import org.jxxy.debug.home.Fragment.item.Breakthough
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.StudyComponentBreakthoughBinding

class BreakthoughViewHolder(val binding: StudyComponentBreakthoughBinding) : MultipleViewHolder<Breakthough>(binding.root) {
    private val breakthoughAdapter = BreakthoughAdapter()

    init {
        val layoutManager = object : LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false) {
            override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
                super.onLayoutChildren(recycler, state)
                calculateItemSize()
            }

            override fun onItemsChanged(recyclerView: RecyclerView) {
                super.onItemsChanged(recyclerView)
                calculateItemSize()
            }

            private fun calculateItemSize() {
                val itemWidth = binding.recyclerview.width / 2
                for (i in 0 until childCount) {
                    getChildAt(i)?.layoutParams?.width = itemWidth
                }
            }
        }
        binding.recyclerview.layoutManager = layoutManager
        layoutManager.orientation = RecyclerView.HORIZONTAL
        val spacing = binding.root.context.resources.getDimensionPixelOffset(R.dimen.d_1)
        binding.recyclerview.addItemDecoration(GridSpacingItemDecoration(spacing))
        binding.recyclerview.adapter = breakthoughAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerview)
        val slideBarItemDecoration = SlideBarItemDecoration(binding.root.context)
        binding.recyclerview.addItemDecoration(slideBarItemDecoration)
    }

    override fun setHolder(entity: Breakthough) {
        binding.breakthough.text = entity.typeName
        entity.thoughInfos?.let { List ->
            breakthoughAdapter.clear()
            val tools = Breakthough(entity.type,entity.typeName,entity.thoughInfos)
            for (i in 1..80) {
                breakthoughAdapter.add(tools)
            }
        }
    }
}
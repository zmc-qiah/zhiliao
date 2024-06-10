package org.jxxy.debug.search.holder

import android.util.Log
import androidx.fragment.app.FragmentManager
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick

import org.jxxy.debug.search.bean.SearchEndNotebook
import org.jxxy.debug.search.databinding.DialogImageBinding

import org.jxxy.debug.search.databinding.ItemSearchEnd3Binding
import org.jxxy.debug.search.dialog.SearchImageDialog
import kotlin.math.log

class SearchEndNotebookHolder (val binding: ItemSearchEnd3Binding, private val parentFragmentManager : FragmentManager) : MultipleViewHolder<SearchEndNotebook>(binding.root) {
    var fragment: FragmentManager? = null
    override fun setHolder(entity: SearchEndNotebook) {
        binding.notebookImg1.load(entity.imageId1,20)
        binding.notebookImg2.load(entity.imageId2,20)
        binding.notebookImg3.load(entity.imageId3,20)
        binding.notebookImg4.load(entity.imageId4,20)
        binding.notebookImg5.load(entity.imageId5,20)

        binding.notebookImg1.singleClick {
             image ->
            Log.i("233","点击")
                fragment?.let {
                    Log.i("233","点击2")
                    ImageClickDialog(image).show(parentFragmentManager)
                }

        }

    }

}
package org.jxxy.debug.classification.dialog

import android.app.Dialog
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jxxy.debug.category.adapter.ClassificationDialogAdapter
import org.jxxy.debug.classification.R
import org.jxxy.debug.classification.databinding.DialogDisplayAllBinding
import org.jxxy.debug.classification.fragment.ClassificationFragment
import org.jxxy.debug.common.http.Response.FirstCategory
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.singleClick

class DisplayAllDialog(var marginTopCount : Int = 0) : BaseDialog<DialogDisplayAllBinding>() {

    companion object {
        private const val TAG = "DisplayAllDialog"
    }

    private var firstList: List<FirstCategory>? = null
    var categoryDialogAdapter = ClassificationDialogAdapter()
    var categoryFragment: ClassificationFragment? = null

    private val firstPosition: Int = 0

    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogDisplayAllBinding {
        return DialogDisplayAllBinding.inflate(inflater,parent,false)
    }


    override fun initView(view: DialogDisplayAllBinding){
//        view.rv.addItemDecoration(CommonItemDecoration(5f, RecyclerView.VERTICAL))
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        view.rv.layoutManager = staggeredGridLayoutManager
        view.rv.addItemDecoration(SpanItemDecoration(0f, 0f, 3))
        view.rv.adapter = categoryDialogAdapter
        view.close.singleClick {
            dismiss()
        }
        firstList?.let {
            categoryDialogAdapter.clear()
            categoryDialogAdapter.add(it)
        }
        categoryDialogAdapter.setOnItemClickListener(object :
            ClassificationDialogAdapter.OnItemClickListener {
            override fun onItemClick(entity: FirstCategory, position: Int) {
                categoryFragment?.refreshFirstCategory(position)
//                view.rv[position].setBackgroundResource(R.color.light_blue苍苍)
                /*val newSecondCategoryData = entity.secondCategory
                categoryFragment?.secondCategoryAdapter?.clearData()
                newSecondCategoryData?.let {
                    categoryFragment?.secondCategoryAdapter?.submitData(it)
                }*/
                    dismiss()
                }
            })
//        view.rv[firstPosition].setBackgroundResource(R.color.search_blue)
    }

    fun show(
        fragmentManager: FragmentManager,
        list: List<FirstCategory>?,
        firstPosition: Int,
        categoryFragment: ClassificationFragment
    ) {
        super.show(fragmentManager, TAG)
        firstList = list
        this.categoryFragment = categoryFragment
        categoryFragment.firstPosition
    }

    override fun setDialogStyle(dialog: Dialog) {
        val params : WindowManager.LayoutParams = dialog.window?.attributes!!
        Log.d("marginTopCount","${marginTopCount}")
        params.y = marginTopCount
        dialog.window?.attributes = params
        dialog.window?.setWindowAnimations(R.style.dialogAnim)
        super.setDialogStyle(dialog)
    }

    init {
//        alpha = 0F
        ifCancelOnTouch = true
        enableBack = true
        gravity = Gravity.TOP
//        height = ViewGroup.LayoutParams.MATCH_PARENT
    }


}

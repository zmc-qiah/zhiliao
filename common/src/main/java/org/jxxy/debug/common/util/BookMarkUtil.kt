package org.jxxy.debug.common.util

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.*
import org.jxxy.debug.common.BookMarkView
import org.jxxy.debug.common.BookViewModel
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.show


object BookMarkUtil {
    lateinit var bookMark : BookMarkView
    lateinit var viewModel : BookViewModel
    fun addView(activity : Activity,marginTop : Int = -1,content : String = "",photoUrl:String = ""){
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(BookViewModel::class.java)
        val root = activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)
        viewModel.getLiveData.observe(activity as LifecycleOwner){
            it.onSuccess {
                Log.d("Scheme","${it?.scheme}")
                bookMark.setData(it?.scheme!!,it?.photo!!,it?.name!!)
                root.addView(bookMark)
            }
        }
        viewModel.getAiLiveData.observe(activity as LifecycleOwner){
            it.onSuccess {
                bookMark.setData(it?.scheme!!,it?.photo!!,it?.name!!)
                root.addView(bookMark)
            }
        }
        bookMark = BookMarkView(activity,marginTop,content,photoUrl)

//        activity.addContentView(bookMark,params)

//        activity.window.decorView.
    }

    fun removeView(){
        bookMark.parent?.let {
            (bookMark.parent as ViewGroup)?.removeView(bookMark)
        }
    }

    fun get(id : Int,isAi : Boolean = false){
        if(isAi){
            viewModel.getAiBookMarkScheme(id)
        }else {
            viewModel.getBookMarkScheme(id)
        }
    }

    fun hide(){
        bookMark.gone()
    }

    fun show(){
        bookMark.show()
    }
}
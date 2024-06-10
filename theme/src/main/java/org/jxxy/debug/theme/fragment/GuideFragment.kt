package org.jxxy.debug.theme.fragment

import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.databinding.FragmentGuideDrawBinding

class GuideFragment(val url: String,val content : String,val type : Int = 0) : BaseFragment<FragmentGuideDrawBinding>() {
    lateinit var way : () -> Unit
    lateinit var waySecond : () -> Unit
    override fun bindLayout(): FragmentGuideDrawBinding {
        return FragmentGuideDrawBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Glide.with(find.guideIv).load(url).into(find.guideIv)
//        find.guideIv.load(url,2)
//        find.guideTv.text = content9
//        when(content){
//            "第一页" -> {
//                find.firstView.setBackgroundResource(R.drawable.item_choose)
//                find.secondView.setBackgroundResource(R.drawable.item_not_choose)
//                find.thirdView.setBackgroundResource(R.drawable.item_not_choose)
//            }
//            "第二页" ->{
//                find.firstView.setBackgroundResource(R.drawable.item_not_choose)
//                find.secondView.setBackgroundResource(R.drawable.item_choose)
//                find.thirdView.setBackgroundResource(R.drawable.item_not_choose)
//            }
//            "第三页" ->{
//                find.firstView.setBackgroundResource(R.drawable.item_not_choose)
//                find.secondView.setBackgroundResource(R.drawable.item_not_choose)
//                find.thirdView.setBackgroundResource(R.drawable.item_choose)
//            }
//        }
        if(type == 1) {
            find.startTv.show()
            find.startTv.singleClick {
                way()
                waySecond()
            }
        }
    }

    override fun subscribeUi() {

    }
}
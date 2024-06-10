package org.jxxy.debug.resources.fragment

import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.resources.databinding.FragmentGuideBinding

class GuideFragment(val url: String,val content : String = "",val type : Int = 0) : BaseFragment<FragmentGuideBinding>() {
    lateinit var way : () -> Unit
    lateinit var waySecond : () -> Unit
    override fun bindLayout(): FragmentGuideBinding {
        return FragmentGuideBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Glide.with(find.guideIv).load(url).into(find.guideIv)
//        find.guideIv.load(url,2)
//        find.guideTv.text = content
        if(type == 1) {
//            find.startTv.show()
//            find.startTv.singleClick {
//                way()
//            }
            way()
        }
    }

    override fun subscribeUi() {

    }
}
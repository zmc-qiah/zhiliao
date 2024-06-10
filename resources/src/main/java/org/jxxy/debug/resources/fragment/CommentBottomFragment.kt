package org.jxxy.debug.resources.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.databinding.FragmentDialogCommentBinding
class CommentBottomFragment(val resourceId:Int):BottomSheetDialogFragment() {
    lateinit var binding: FragmentDialogCommentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogCommentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,org.jxxy.debug.common.R.style.AppBottomSheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params = binding.root.layoutParams
        params.height = getHeight()/5*4
        binding.root.layoutParams =params
        binding.commentBackIcon.singleClick {
            dismiss()
        }
        binding.commentViewPage.setOnTouchListener{ _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.commentViewPage.parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    binding.commentViewPage.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
        val viewPage = binding.commentViewPage
        val tabLayout = binding.commentTabLayout
        val list = listOf<Fragment>(
            RecommendFragment(),
            VideoCommentFragment(resourceId)
        )
        viewPage.adapter = VideoFragmentStateAdapter(list, requireActivity())
        val tabList = mutableListOf<String>("推荐资讯", "评论")
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList.get(position)
        }.attach()
    }

}
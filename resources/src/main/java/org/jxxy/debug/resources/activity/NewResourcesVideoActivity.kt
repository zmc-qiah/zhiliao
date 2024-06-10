package org.jxxy.debug.resources.activity

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.MyVideoPlayer
import org.jxxy.debug.resources.NoteDialog
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.databinding.ActivityNewVideoBinding
import org.jxxy.debug.resources.databinding.Tab1Binding
import org.jxxy.debug.resources.fragment.VideoBriefFragment
import org.jxxy.debug.resources.fragment.VideoCommentFragment
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.util.Mytype
import org.jxxy.debug.resources.util.isLogin
import org.jxxy.debug.resources.videoHelper.MyVideoPlayHelperAInt
import org.jxxy.debug.resources.videoHelper.MyVideoPlayerHelperRotate
import java.lang.ref.WeakReference

class NewResourcesVideoActivity : BaseActivity<ActivityNewVideoBinding>() {
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }

    override fun bindLayout(): ActivityNewVideoBinding = ActivityNewVideoBinding.inflate(layoutInflater)
    val orientationUtils: OrientationUtils by lazy { OrientationUtils(this, view.playView2) }
    // 是否横屏
    var state = false
    fun OrientationUtils.myResolveByClick(){
        resolveByClick()
        state = !state
    }

    override fun initView() {
//        view.recycleView.adapter = adapter
    }

    override fun subscribeUi() {
        viewModel.resourceLiveData.observe(this) {
            it.onSuccess {
                loadView(it)
            }
        }
        viewModel.pointLiveData.observe(this){
            it.onSuccess {
                Log.d("PointChange", "用户${PersistenceUtil.getValue<String>("userName")?:""}通过观看视频获得2积分\n现在积分${it}")
            }
        }
        val intent = intent
        if (intent != null) {
            val extra = intent.getSerializableExtra("resource_info")
            extra.nullOrNot({
                var id = intent.getIntExtra("resourceId", -1)
                if (id != -1) {
                    viewModel.getResourceById(id)
                } else {
                    "资源不存在".toast()
                    finish()
                }
            }, {
                it as ResourceInfoResponse
                loadView(it)
            })
        } else {
            "资源不存在".toast()
            finish()
        }
    }
    fun loadView(res: ResourceInfoResponse?) {
//        view.appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
//            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
//                // verticalOffset的值表示AppBarLayout的垂直偏移量，向上滚动为负，向下滚动为正
//                val totalScrollRange = appBarLayout!!.totalScrollRange
//                if (verticalOffset == 0) {
//                    // 完全展开状态
//                    view.toolbarCL.gone()
//                } else if (Math.abs(verticalOffset) >= totalScrollRange) {
//                    // 完全折叠状态
//                    view.toolbarCL.show()
//                }
//            }
//
//        })
        res?.let { resources ->
            if(isLogin())  {
                viewModel.addVideo()
            }
            val tabList = mutableListOf("简介")
            var temp = "评论"
            if (resources.resourceInfo?.resourceComments != 0) {
                temp += resources.resourceInfo?.resourceComments.toString()
            }
            tabList.add(temp)
            val videoBriefFragment = VideoBriefFragment(resources)
            videoBriefFragment.flag = true
            val commentFragment = VideoCommentFragment(resources.resourceInfo?.resourceId ?: -1)
            commentFragment.flag = true
            val list = listOf<Fragment>(
                videoBriefFragment,
                commentFragment
            )
            val adapter = VideoFragmentStateAdapter(list, this@NewResourcesVideoActivity)
            val viewPage = view.viewPage
            viewPage.adapter = adapter
            val tabLayout = view.tabLayout
            TabLayoutMediator(tabLayout, viewPage) { tab, position ->
                val binding = Tab1Binding.inflate(layoutInflater, tabLayout,false)
                tab.customView = binding.root
                tab.tag = binding
                binding.tabTextView.text = tabList[position]
                when(position){
                    1->{
                        binding.tabIcon.text = ResourceUtil.getString(R.string.icon_berief)
                        binding.tabIcon.textSize = 25f
                        binding.tabTextView.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                        binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                    }
                    0->{
                        binding.tabIcon.text = ResourceUtil.getString(R.string.icon_comment)
                        binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                        binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                    }
                }
            }.attach()
            tabLayout.selectTab(tabLayout.getTabAt(0))
            tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val binding = tab?.tag as Tab1Binding
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                    binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val binding = tab?.tag as Tab1Binding
                    binding.tabTextView.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            resources.resourceInfo?.let { info ->
                if (info.resourceType != Mytype.RESOURCE_VIDEO_INFO) {
                    "该视频资源不存在".toast()
                    finish()
                }
                info.videoUrl.nullOrNot(
                    { "视频加载异常".toast() },
                    {
                        resources.resourceInfo?.let { info ->
                            if (info.resourceType != Mytype.RESOURCE_VIDEO_INFO) {
                                "该视频资源不存在".toast()
                                finish()
                            }
                            info.videoUrl.nullOrNot(
                                { "视频加载异常".toast() },
                                {
                                    view.playView2.apply {
                                        this.activity = WeakReference(this@NewResourcesVideoActivity)
                                        this.infor = resources
                                        setUp(it, true, info.resourceTitle)
                                        if (resources.photosUrl?.size?:0 > 0) {
                                            val image = ImageView(this@NewResourcesVideoActivity)
                                            image.scaleType = ImageView.ScaleType.CENTER_CROP
                                            image.load(resources.photosUrl!![0])
                                            thumbImageView = image
                                        }
                                        titleTextView.hide()
                                        backButton.show()
                                        fullscreenButton.singleClick {
                                            startWindowFullscreen(context, false, true)
                                        }
                                        setGSYStateUiListener {
                                            when (it) {
                                                StandardGSYVideoPlayer.CURRENT_STATE_PLAYING -> {
                                                    (view.collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams).scrollFlags = 0
                                                }
                                                StandardGSYVideoPlayer.CURRENT_STATE_PAUSE -> {
                                                    (view.collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams).scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                                                }
                                            }
                                        }
                                        isAutoFullWithSize = false
                                        isReleaseWhenLossAudio = false
                                        isShowFullAnimation = true
                                        setIsTouchWiget(false)
                                        clickMap.put(Mytype.ROTATE,object : MyVideoPlayerHelperRotate {
                                            override fun rotate() {
                                                orientationUtils.myResolveByClick()
                                            }
                                        })
                                        clickMap.put(R.id.commentIcon,object : MyVideoPlayerHelperRotate {
                                            override fun rotate() {
                                                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                                            }
                                        })
                                        clickMap.put(R.id.videoNoteIcon,object : MyVideoPlayHelperAInt{
                                            override fun help(id:Int) {
                                                val bottomSheetView =NoteDialog(id)
                                                bottomSheetView.show(supportFragmentManager,bottomSheetView.tag)
                                            }
                                        })
//                                                val bottomSheetDialog =
//                                                    BottomSheetDialog(this@NewResourcesVideoActivity)
//                                                bottomSheetDialog.setContentView(bottomSheetView.root)
//                                                bottomSheetView.submitButton.singleClick {
//                                                    loginCheck(this@NewResourcesVideoActivity) {
//                                                        id?.let { it2 ->
//                                                            viewModel.addNote(
//                                                                it2,
//                                                                bottomSheetView.editText.text.toString()
//                                                            )
//                                                            bottomSheetView.editText.setText("")
//                                                        }
//                                                    }
//                                                }
//                                                bottomSheetDialog.show()

//                                        lifecycleScope.launch(Dispatchers.Default) {
//                                            while (true){
//                                                delay(1000.toLong())
//                                                getListItemSize()?.let {
//                                                    Log.d("TAG", "loadViewgetListItemSize: ${it[0]}")
//                                                    Log.d("TAG", "loadViewgetListItemSize: ${it[1]}")
//                                                }
//                                                getListItemRect()?.let {
//                                                    Log.d("TAG", "loadViewgetListItemRect: ${it[0]}")
//                                                    Log.d("TAG", "loadViewgetListItemRect: ${it[1]}")
//                                                }
//                                            }
//                                        }
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }
    override fun onPause() {
        super.onPause()
        view.playView2.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        view.playView2.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils.releaseListener()
    }

    override fun onBackPressed() {
        val player = (view.playView2.currentPlayer) as MyVideoPlayer
        if(view.playView2.currentPlayer.isIfCurrentIsFullscreen){
            if ( player.bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                player.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            }else{
                if (state){
                    orientationUtils.resolveByClick()
                }
                view.playView2.onBackFullscreen()
            }
        }else{
            view.playView2.setVideoAllCallBack(null)
            super.onBackPressed()
        }

    }
}

package org.jxxy.debug.resources

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.widget.CommonToolbar
import org.jxxy.debug.corekit.widget.IconFont
import org.jxxy.debug.corekit.widget.RoundConstraintLayout
import org.jxxy.debug.resources.activity.NewResourcesVideoActivity
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.databinding.VideoLayoutBinding
import org.jxxy.debug.resources.fragment.VideoCommentFragment
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.util.MyUtil
import org.jxxy.debug.resources.util.Mytype
import org.jxxy.debug.resources.videoHelper.MyVideoPlayHelperAInt
import org.jxxy.debug.resources.videoHelper.MyVideoPlayerHelper
import org.jxxy.debug.resources.videoHelper.MyVideoPlayerHelperRotate
import java.lang.ref.WeakReference

class MyVideoPlayer : StandardGSYVideoPlayer {
    constructor(context: Context):super(context)
    constructor(context: Context,attrs: AttributeSet):super(context,attrs)
    constructor(context: Context,fullFlag: Boolean):super(context,fullFlag)
    lateinit var shareIcon:IconFont
    lateinit var rotateIcon:IconFont
    lateinit var unLikeIcon:IconFont
    lateinit var markIcon:IconFont
    lateinit var commentIcon:IconFont
    lateinit var likeIcon:IconFont
    lateinit var authorImageView: ImageView
    lateinit var authorTextView: TextView
    lateinit var labelTextView: TextView
    lateinit var labelRCL:RoundConstraintLayout
    lateinit var layoutRight:ConstraintLayout
    lateinit var outMyVideoPlayer:MyVideoPlayer
    lateinit var bottomSheetBehavior:BottomSheetBehavior<RoundConstraintLayout>
    // 0竖屏填充，1横屏默认
    private var showState:Int = 0;
    private val TAG:Int = R.id.video_full_screen_icon_click
    var infor : ResourceInfoResponse ?=null
    lateinit var binding: VideoLayoutBinding
    var activity: WeakReference<NewResourcesVideoActivity>? =null

    val clickMap: HashMap<Int, MyVideoPlayerHelper> = HashMap()
    override fun getLayoutId(): Int = R.layout.video_layout
    override fun initInflate(context: Context?) {
        binding = VideoLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }
    override fun init(context: Context?) {
        super.init(context)
        outMyVideoPlayer = this
        bottomSheetBehavior= BottomSheetBehavior.from(binding.videoBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.isHideable = true
        shareIcon =binding.videoShareIcon
        unLikeIcon =binding.videoUnLikeIcon
        markIcon =binding.videoMarkIcon
        commentIcon =binding.videoCommentIcon
        likeIcon =binding.videoLikeIcon
        rotateIcon =binding.videoRotateIcon
        authorImageView = binding.videoAuthorImageView
        authorTextView = binding.videoAuthorTextView
        labelTextView = binding.videoTableTextView
        labelRCL = binding.videoLabelRCL
        layoutRight = binding.layoutRight
        layoutRight.gone()
        authorImageView.gone()
        authorTextView.gone()
        labelRCL.gone()
        titleTextView.hide()
        rotateIcon.gone()
        binding.videoNoteIcon.gone()

    }
    override fun addTextureView() {
        super.addTextureView()
    }

    override fun showSmallVideo(
        size: Point?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        return super.showSmallVideo(size, actionBar, statusBar) as MyVideoPlayer
    }

    override fun startWindowFullscreen(
        context: Context?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        var myVideoPlayer =
            super.startWindowFullscreen(context, actionBar, statusBar) as MyVideoPlayer
        myVideoPlayer.apply {
            binding.videoBottomSheet.setTag(TAG, true)
            binding.videoCommentIcon.setTag(TAG, false)
            if (currentVideoHeight/currentVideoWidth.toFloat()>
                MyUtil.getHeight()-binding.videoBottomSheet.height/MyUtil.getWidth()){
                binding.root.setTag(TAG,true)
            }else{
                binding.root.setTag(TAG,false)
            }
            this@MyVideoPlayer.infor.nullOrNot(
                {
                    markIcon.setTag(TAG, false)
                    unLikeIcon.setTag(TAG, false)
                    likeIcon.setTag(TAG, false)
                }, {
                    infor->
                    infor.resourceInfo?.let {
                        authorTextView.text = it.resourceAuthorName
                        authorImageView.load(it.resourceAuthorHead, true)
                        labelTextView.text = it.resourceLabel
                        if (it.resourceLabel?.length?:0>0){
                            labelRCL.show()
                        }
                        binding.videoLikeTV.text = it.resourceLikes.toString()
                        binding.videoCommentTV.text = it.resourceComments.toString()
                        binding.videoMarkTV.text = "收藏"
                    }
                    infor.myState?.let {
                        if (it.likeState == 1) {
                            likeIcon.setTag(TAG, true)
                        } else {
                            likeIcon.setTag(TAG, false)
                        }
                        if (it.collectState == 1) {
                            markIcon.setTag(TAG, true)
                        } else {
                            markIcon.setTag(TAG, false)
                        }
                    }
                    unLikeIcon.setTag(TAG, false)
                    binding.videoCommentNumCommonToolbar.setTitleText("评论${infor.resourceInfo?.resourceComments}")
                    this@MyVideoPlayer.activity?.let {
                        val  adapter = VideoFragmentStateAdapter( listOf<Fragment>(VideoCommentFragment(infor.resourceInfo!!.resourceId)),
                            it.get()!!
                            )
                        binding.videoCommentViewPager2.adapter = adapter
                    }

                })
            rotateIcon.show()
            layoutRight.show()
            authorImageView.show()
            authorTextView.show()
            titleTextView.show()
            binding.videoNoteIcon.show()
            markIcon.singleClick { icon ->
                Log.d("TAG", "startWindowFullscreen: ")
                val tag = icon.getTag(TAG)
                tag?.let {
                    val b = it as Boolean
                    when (b) {
                        false -> {
                            myVideoPlayer.markIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                            icon.setTag(TAG, true)
                        }
                        true -> {
                            myVideoPlayer.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
                            icon.setTag(TAG, false)
                        }
                    }
                }
            }
            unLikeIcon.singleClick { icon ->
                Log.d("TAG", "startWindowFullscreen: ")
                val tag = icon.getTag(TAG)
                tag?.let {
                    val b = it as Boolean
                    when (b) {
                        false -> {
                            myVideoPlayer.unLikeIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                            icon.setTag(TAG, true)
                        }

                        true -> {
                            myVideoPlayer.unLikeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
                            icon.setTag(TAG, false)
                        }
                    }
                }
            }
            likeIcon.singleClick { icon ->
                Log.d("TAG", "startWindowFullscreen: ")

                val tag = icon.getTag(TAG)
                tag?.let {
                    val b = it as Boolean
                    when (b) {
                        false -> {
                            myVideoPlayer.likeIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                            icon.setTag(TAG, true)
                        }

                        true -> {
                            myVideoPlayer.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
                            icon.setTag(TAG, false)
                        }
                    }
                }
            }
            rotateIcon.singleClick {
                Log.d("TAG", "startWindowFullscreen: ")
                when (this.showState) {
                    0 -> {
                        this@MyVideoPlayer.clickMap.get(Mytype.ROTATE)?.let {
                            (it as MyVideoPlayerHelperRotate).rotate()
                        }
                        layoutRight.gone()
                        authorImageView.gone()
                        authorTextView.gone()
                        labelRCL.gone()
                        titleTextView.gone()
//                        val params =
//                            binding.videoNoteIcon.layoutParams as RelativeLayout.LayoutParams
//                        params.removeRule(RelativeLayout.ALIGN_PARENT_START)
//                        params.addRule(RelativeLayout.ALIGN_PARENT_END)
//                        params.addRule(RelativeLayout.CENTER_VERTICAL)
//                        binding.videoNoteIcon.layoutParams = params
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        this.showState = 1

                    }

                    1 -> {
                        this@MyVideoPlayer.clickMap.get(Mytype.ROTATE)?.let {
                            (it as MyVideoPlayerHelperRotate).rotate()
                        }
                        layoutRight.show()
                        authorImageView.show()
                        authorTextView.show()
                        labelRCL.show()
                        titleTextView.show()
//                        val params =
//                            binding.videoNoteIcon.layoutParams as RelativeLayout.LayoutParams
//                        params.removeRule(RelativeLayout.ALIGN_PARENT_END)
//                        params.addRule(RelativeLayout.ALIGN_PARENT_START)
//                        params.addRule(RelativeLayout.CENTER_VERTICAL)
//                        binding.videoNoteIcon.layoutParams = params
//                        this.showState = 0
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                    }
                    else -> {}
                }
            }
            commentIcon.singleClick { icon ->
                Log.d("TAG", "startWindowFullscreen: ")
                val tag = icon.getTag(TAG)
                tag?.let {
                    val b = it as Boolean
                    when (b) {
                        false -> {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            icon.setTag(TAG, true)
                        }
                        true -> {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                            icon.setTag(TAG, false)
                        }
                    }
                }
            }
            bottomSheetBehavior.addBottomSheetCallback(object :BottomSheetCallback(){
                val h = MyUtil.getHeight()
                val 宽长比 = MyUtil.getWidth().toFloat() / h.toFloat()
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when(newState) {
                        BottomSheetBehavior.STATE_EXPANDED ->{
                            val height = h - binding.videoBottomSheet.height
                            binding.surfaceContainer.gravity =Gravity.TOP
                            binding.surfaceContainer.layoutParams.height = height
                           if( binding.root.getTag(TAG) as Boolean){
                               binding.surfaceContainer.layoutParams.width = (height*宽长比).toInt()
                           }
                        }
                        BottomSheetBehavior.STATE_HIDDEN ->{
                            binding.surfaceContainer.gravity =Gravity.CENTER
                            binding.surfaceContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                            binding.surfaceContainer.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        }
                        BottomSheetBehavior.STATE_COLLAPSED ->{
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val height = h - binding.videoBottomSheet.height*slideOffset
                    binding.surfaceContainer.gravity =Gravity.TOP
                    binding.surfaceContainer.layoutParams.height = height.toInt()
                    if( binding.root.getTag(TAG) as Boolean){
                        binding.surfaceContainer.layoutParams.width = (height*宽长比).toInt()
                    }
                }

            })
            binding.videoCommentNumCommonToolbar.toolbarBackCallback = object :
                CommonToolbar.ToolbarBackCallback {
                override fun backActivity(context: Context?) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
            binding.videoNoteIcon.singleClick {
                this@MyVideoPlayer.clickMap.get(it.id)?.let {
                    (it as MyVideoPlayHelperAInt).help(this@MyVideoPlayer.infor!!.resourceInfo!!.resourceId)
                }
            }
        }
        return myVideoPlayer
    }
    fun getListItemRect(): IntArray? = mListItemRect
    fun getListItemSize():IntArray?  = mListItemSize
}
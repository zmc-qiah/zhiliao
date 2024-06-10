package org.jxxy.debug.resources.activity

import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.debug.myapplication.Selectbale.SelectableTextHelper
import com.debug.myapplication.Selectbale.SelectionInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import navigation
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.service.drawThinkMapNoAI
import org.jxxy.debug.common.service.goThinkMap
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.common.util.start
import org.jxxy.debug.common.widget.read.IsLastItem
import org.jxxy.debug.common.widget.read.view.BookLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.*
import org.jxxy.debug.corekit.widget.IconFont
import org.jxxy.debug.resources.NoteDialog
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.adapter.GuidePageAdapter
import org.jxxy.debug.resources.adapter.TextAdapter
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.bean.ItemCommentedChapter
import org.jxxy.debug.resources.bean.TagInfo
import org.jxxy.debug.resources.databinding.ActivityNewNewTextBinding
import org.jxxy.debug.resources.dialog.ThinkMapDialog
import org.jxxy.debug.resources.fragment.*
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.myListener.ClickThinkMapDialogSubBtnListener
import org.jxxy.debug.resources.myListener.SubmitListener
import org.jxxy.debug.resources.util.Mytype
import org.jxxy.debug.resources.util.isLogin


class NewNewResourcesTextActivity : BaseActivity<ActivityNewNewTextBinding>() {
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    val adapter: TextAdapter by lazy {
        TextAdapter(viewModel, textSelectListen, this, pageSum)
    }
    private val pageSum = MutableLiveData<String>()
    private var myResourceId: Int? = null
    private var res: ResourceInfoResponse? = null
    private var makeText: ResourceInfoResponse? = null
    private var selectText: TextView? = null
    private var selectChapter: ItemCommentedChapter? = null
    private val textSelectListen: SelectableTextHelper.OnSelectListener =
        object : SelectableTextHelper.OnSelectListener {
            override fun onTextSelected(content: CharSequence?) {
            }

            override fun takeNote(selectionInfo: SelectionInfo?) {
                val bottomSheetView = NoteDialog(myResourceId)
                bottomSheetView.show(supportFragmentManager, bottomSheetView.tag)
            }

            override fun copyText(content: String?) {
                val thinkMapDialog = ThinkMapDialog(object : ClickThinkMapDialogSubBtnListener {
                    override fun click(root: Int, isAi: Boolean) {
                        res?.let { res ->
                            when (root) {
                                0 -> {
                                    if(isAi&&myResourceId == 25){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,44)
                                            }
                                        }
                                    }else if(isAi&&myResourceId == 14){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,62)
                                            }
                                        }
                                    }else if (isAi) {
                                        res.resourceInfo?.resourceContent?.let {
                                            viewModel.aiAnalysisTabulation(
                                                it
                                            )
                                        }
                                        view.lvCircularJump.start()
                                    } else {
                                        var node: Node =
                                            Node(res.resourceInfo?.resourceTitle.toString())
                                        res.photosUrl?.let {
                                            node.pictureList = it
                                        }
                                        drawThinkMapNoAI(this@NewNewResourcesTextActivity, node)
                                    }
                                }
                                1 -> {
                                    if(isAi&&myResourceId == 25){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,44)
                                            }
                                        }
                                    }else if(isAi&&myResourceId == 14){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,62)
                                            }
                                        }
                                    }else if (isAi) {
                                        viewModel.aiAnalysisTabulation(content.toString())
                                        view.lvCircularJump.start()
                                    } else {
                                        drawThinkMapNoAI(
                                            this@NewNewResourcesTextActivity,
                                            Node(content.toString())
                                        )
                                    }
                                }
                                2 -> {
                                    if(isAi&&myResourceId == 25){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,44)
                                            }
                                        }
                                    }else  if(isAi&&myResourceId == 14){
                                        view.lvCircularJump.start()
                                        lifecycleScope.launch{
                                            delay(3000)
                                            launch(Dispatchers.Main) {
                                                goThinkMap(this@NewNewResourcesTextActivity,62)
                                            }
                                        }
                                    }else if (isAi) {
                                        viewModel.aiAnalysisTabulation(content.toString())
                                        view.lvCircularJump.start()
                                    } else {
                                        drawThinkMapNoAI(
                                            this@NewNewResourcesTextActivity,
                                            Node(content.toString())
                                        )
                                    }
                                }
                                else -> {}
                            }
                            if (isAi) {
                                view.lvCircularJump.setViewColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                                view.lvCircularJump.start()
                            }
                        }
                    }
                })
                thinkMapDialog.show(supportFragmentManager)
            }

            override fun textSearch(key: String?) {
                key?.let {
                    val scheme = Scheme(Scheme.SEARCH)
                    scheme.keyword = it
                    scheme.navigation(this@NewNewResourcesTextActivity)
                }
            }

            override fun markText(view: TextView, mSelectionInfo: SelectionInfo) {
                selectText = view
                selectChapter = ItemCommentedChapter()
                addFragment = ChapterReviewFragment2(
                    selectChapter!!.apply {
                        id == myResourceId!!
                        start = mSelectionInfo.start
                        end = mSelectionInfo.end
                        content = mSelectionInfo.selectionContent ?: ""
                    },
                    object : SubmitListener {
                        override fun onSubmit(string: String) {
                            val baseOffset = (view.tag as? Int) ?: 0
                            viewModel.addChapter(
                                TagInfo(
                                    myResourceId!!,
                                    baseOffset + mSelectionInfo.start,
                                    baseOffset + mSelectionInfo.end,
                                    0,
                                    string
                                )
                            )
                        }
                    },
                    true
                )
                addFragment!!.show(supportFragmentManager, addFragment!!.tag)
            }

            override fun singleClick(baseOffset: Int, startOffset: Int, endOffset: Int) {
                res?.tagInfo?.forEach {
                    if (it.start <= baseOffset + startOffset && it.end >= baseOffset + endOffset) {
                        if (it.id != 0) {
                            val itemCommentedChapter = ItemCommentedChapter(
                                res!!.resourceInfo!!.resourceContent!!.substring(
                                    it.start,
                                    it.end
                                ), it.id
                            )
                            val chapterReviewFragment = ChapterReviewFragment2(itemCommentedChapter)
                            chapterReviewFragment.show(
                                supportFragmentManager,
                                chapterReviewFragment.tag
                            )
                        } else {
                            "网络波动，加载中".toast()
                        }
                    }
                }
            }
        }
    private var addFragment: ChapterReviewFragment2? = null
    override fun bindLayout(): ActivityNewNewTextBinding =
        ActivityNewNewTextBinding.inflate(layoutInflater)

    val photoBottomSheetBehavior by lazy { BottomSheetBehavior.from(view.bottomSheet) }
    val commentBottomSheetBehavior by lazy { BottomSheetBehavior.from(view.commentBottomSheet) }
    override fun initView() {
        if (PersistenceUtil.getValue<Int>("isLoad") == null) {
            val adapterGuide = GuidePageAdapter(this)
            adapterGuide.list.add(
                GuideFragment(
                    "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/2b048514-c82f-4f75-8f4c-309e3e87b50e.png",
                    "第一页"
                )
            )
            adapterGuide.list.add(
                GuideFragment(
                    "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/f5ee90f4-320f-4e47-8983-bd38a4033af4.png",
                    "第二页"
                )
            )
            adapterGuide.list.add(
                GuideFragment(
                    "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/d964f2f7-47c7-436e-b0ff-4bd470fd74da.png",
                    "第三页"
                )
            )
            adapterGuide.list.add(
                GuideFragment(
                    "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/3b320e0c-2aab-4ee4-bbdf-836a36b34b54.png",
                    "第四页"
                )
            )
            adapterGuide.list.add(
                GuideFragment(
                    "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/82e517a5-789f-4f12-bb2b-abaf283503f4.png",
                    "第五页"
                )
            )
            val fragment = GuideFragment("", "", 1)
            fragment.way = {
                view.guideVp.hide()
                BookMarkUtil.bookMark.show()
                view.imgFloating.show()
                view.sumPageTextView.show()
                view.currentPageTextView.show()
            }
            view.imgFloating.hide()
            view.sumPageTextView.hide()
            view.currentPageTextView.hide()
            adapterGuide.list.add(fragment)
            view.guideVp.adapter = adapterGuide
            PersistenceUtil.putValue("isLoad",1)
            adapter.count = 1
        }else{
            view.guideVp.hide()
        }
        photoBottomSheetBehavior.peekHeight = 0
        photoBottomSheetBehavior.isHideable = false
        photoBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        view.commentBottomSheet.layoutParams.height = (getHeight() / 5 * 4)
        commentBottomSheetBehavior.peekHeight = 0
        commentBottomSheetBehavior.isHideable = false
        commentBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//        view.commentBackIcon.singleClick(increase = true) {
//            commentBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
        view.bookView.setAdapter(adapter)
        view.bookView.setFlipMode(BookLayoutManager.BookFlipMode.MODE_CURL)
        view.bookView.setOnPositionChangedListener { arriveNext, curPosition ->
            if (curPosition != 0) {
                BookMarkUtil.hide()
            }else{
                BookMarkUtil.show()
            }
            view.currentPageTextView.text = (curPosition + 1).toString()
        }
    }

    override fun subscribeUi() {
        viewModel.resourceLiveData.observe(this) {
            it.onSuccess {
                res = it
                loadView(it)
            }
        }
        pageSum.observe(this) {
            view.sumPageTextView.text = "/" + it
        }
        viewModel.pointLiveData.observe(this) {
            it.onSuccess {
                Log.d(
                    "PointChange",
                    "用户${PersistenceUtil.getValue<String>("userName") ?: ""}通过阅读文本获得1积分\n现在积分${it}"
                )
            }
        }
        viewModel.nodeLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    view.lvCircularJump.close()
                    drawThinkMapNoAI(this, it)
                }
            }
        }
        viewModel.addChapterLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    if (res!!.tagInfo == null) {
                        res!!.tagInfo = ArrayList()
                    }
                    var tempList: ArrayList<TagInfo> = ArrayList(res!!.tagInfo!!)
                    var tagInfo: TagInfo? = null
                    for (datum in it) {
                        if (datum.start == selectChapter!!.start && datum.end == selectChapter!!.end) {
                            selectChapter!!.id = datum.id
                            tagInfo = datum
                            break
                        }
                    }
                    tagInfo?.let { it1 ->
                        selectChapter?.let {
                            Log.d("TAG", "subscribaaaaeUi: ${selectChapter}")
                            selectChapter?.id = tagInfo?.id ?: 0
                            markText(selectText!!, it.start, it.end)
                            addFragment?.refresh(selectChapter!!.id)
                        }
                        res!!.tagInfo = tempList.apply {
                            add(it1)
                        }
                    }
                }
            }
        }
        if (intent != null) {
            val extra = intent.getSerializableExtra("resource_info")
            extra.nullOrNot({
                myResourceId = intent.getIntExtra("resourceId", 0)
                if (myResourceId != 0) {
                    viewModel.getResourceById(myResourceId!!)
                } else {
                    "资源不存在".toast()
                    finish()
                }
            }, {
                it as ResourceInfoResponse
                res = it
                myResourceId = it.resourceInfo!!.resourceId
                loadView(it)
            })
        } else {
            "资源不存在".toast()
            finish()
        }
    }

    private var textSize: Int = 0
    fun loadView(info: ResourceInfoResponse?) {
        info?.let { resources ->
            if (isLogin()) {
                viewModel.addText()
            }
            resources.type = Mytype.RESOURCE_TEXT_INFO
            textSize = resources.resourceInfo?.resourceContent?.length ?: 0
            adapter.add(resources)
            adapter.act = this
            view.currentPageTextView.text = 1.toString()
            resources.resourceInfo.nullOrNot(
                {
                    "资源不存在".toast()
                    finish()
                },
                { info ->
                    if (info.resourceType != Mytype.RESOURCE_TEXT_INFO) {
                        "资源不异常".toast()
                        finish()
                    }
                    if (resources.photosUrl?.size ?: 0 > 0 && resources.photosUrl!![0] != null) {
                        loadPhotos(resources.photosUrl)
                    } else {
                        view.imgFloating.singleClick {
                            "该文章下没有图片".toast()
                        }
                    }
                    loadComments(info.resourceId)
                }
            )
        }


    }

    fun loadComments(resourceId: Int) {
        val viewPage = view.commentViewPage
        val tabLayout = view.commentTabLayout
        val list = listOf<Fragment>(
            RecommendFragment(),
            VideoCommentFragment(resourceId)
        )
        viewPage.adapter = VideoFragmentStateAdapter(list, this)
        val tabList = mutableListOf<String>("推荐资讯", "评论")
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList.get(position)
        }.attach()
        view.bookView.setIsLastItem(object : IsLastItem {
            override fun isLastItem(): Boolean {
                return adapter.itemCount - 1 == view.bookView.bookRecyclerView.currentPosition
            }

            override fun ifLastItem() {
                commentBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }

    fun loadPhotos(photosUrl: List<String>?) {
        val photoFragmentList = mutableListOf<Fragment>()
        Log.d("TAG", "loadPhotos: " + photosUrl)
        photosUrl?.forEach {
            if (it != null) {
                photoFragmentList.add(PhotoFragment(it))
            }
        }
        val photoFragmentAdapter = VideoFragmentStateAdapter(photoFragmentList, this)
        view.imagesViewPage.adapter = photoFragmentAdapter
        view.imagesTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<IconFont>(R.id.pointPoint)?.setTextColor(
                    ResourceUtil.getColor(R.color.pink)
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<IconFont>(R.id.pointPoint)?.setTextColor(
                    ResourceUtil.getColor(
                        org.jxxy.debug.corekit.R.color.grey
                    )
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        TabLayoutMediator(view.imagesTabLayout, view.imagesViewPage) { tab, position ->
            tab.setCustomView(R.layout.tab_point)
        }.attach()
        view.imagesViewPage.setCurrentItem(0)
        photoBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        view.imgFloating.setImageResource(R.drawable.img2)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        view.imgFloating.setImageResource(R.drawable.img)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
        view.imgFloating.singleClick {
            when (photoBottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    photoBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    photoBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    override fun onBackPressed() {
        if (commentBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            commentBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else if (photoBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            photoBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BookMarkUtil.removeView()
    }
}

fun findExtraTagInfo(longList: List<TagInfo>, shortList: List<TagInfo>): List<TagInfo> {
    val longMap = longList.groupBy {
        it.id
    }
    val shortMap = shortList.groupBy { it.id }
    val extraTagInfoList = ArrayList<TagInfo>(shortList)
    longMap.forEach { (tagId, longTagInfos) ->
        val shortTagInfos = shortMap[tagId]
        if (shortTagInfos == null || longTagInfos.size > shortTagInfos.size) {
            extraTagInfoList.addAll(longTagInfos)
        }
    }
    return extraTagInfoList
}

fun markText(view: TextView, start: Int, end: Int) {
    if (view is MyTextView) {
        view.tagList = ArrayList(view.tagList).apply {
            add(TagInfo(0, start, end, 0))
        }
        view.invalidate()
    } else {
        val text = view.text.toString()
        val spannableString = if (view.text is SpannableString) {
            view.text as SpannableString
        } else {
            SpannableString(text)
        }
        spannableString.setSpan(
            UnderlineSpan(),
            start,
            end,
            0
        )
        spannableString.setSpan(
            ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100)),
            start,
            end,
            0
        )
        spannableString.setSpan(
            BackgroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_100)),
            start,
            end,
            0
        )
        view.text = spannableString
    }
}
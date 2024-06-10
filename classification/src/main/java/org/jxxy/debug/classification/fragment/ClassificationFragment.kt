package org.jxxy.debug.classification.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jxxy.debug.category.adapter.ClassificationAdapter
import com.jxxy.debug.category.adapter.ResourceAdapter
import com.jxxy.debug.category.adapter.SecondClassificationAdapter
import com.jxxy.debug.category.http.viewmodel.CategoryViewModel
import org.jxxy.debug.classification.databinding.FragmentCategoryBinding
import org.jxxy.debug.classification.dialog.DisplayAllDialog
import org.jxxy.debug.common.http.Response.*
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.corekit.recyclerview.CenterLayoutManager

class ClassificationFragment : BaseFragment<FragmentCategoryBinding>() {

//    val viewModel : TestViewModel by lazy {
//        ViewModelProvider(this).get(TestViewModel::class.java)
//    }
//    override fun bindLayout(): FragmentClassificationBinding = FragmentClassificationBinding.inflate(layoutInflater)
//
//    override fun initView() {
//        val adapter = ClassificationViewPageFirstAdapter(this)
//        val tabName : ArrayList<String> = ArrayList()
//        for (i in 0..10){
//            tabName.add("年龄段" + i)
//        }
//        find.classificationFirstVp.adapter = adapter
//        TabLayoutMediator(find.classificationFirstTl,find.classificationFirstVp){ tab,position ->
//            tab.setText(
//                tabName[position]
//            )
//        }.attach()
//    }
//
//    override fun subscribeUi() {
//    }

    private val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(CategoryViewModel::class.java)
    }
    var firstPosition: Int? = null

    //    private var firstCategoryAdapter: FirstCategoryAdapter? = null
    private var categoryAdapter: ClassificationAdapter? = null
    private var resourceAdapter: ResourceAdapter? = null
    var secondClassificationAdapter: SecondClassificationAdapter? = null
    private var firstCategoryList: List<FirstCategory>? = null
    var displayAllDialog : DisplayAllDialog? = null
//    private var searchList: List<SearchBean>? = null
//    private var searchAdapter: SearchAdapter = SearchAdapter()

//    val displayAllDialog = DisplayAllDialog()

    override fun bindLayout(): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
//        val secondCategorys = ArrayList<SecondCategory>()
//        repeat(10) {
//            secondCategorys.add(SecondCategory(1, "测试2"))
//        }
//        val FirstCategorys = ArrayList<FirstCategory>()
//        repeat(10) {
//            FirstCategorys.add(FirstCategory(1, "测试1", secondCategorys))
//        }
//        firstCategoryList = FirstCategorys
        // 先加载一级分类列表
        categoryAdapter = ClassificationAdapter()
        //测试
//        categoryAdapter?.add(FirstCategorys)
        find.rvLevel1.addItemDecoration(CommonItemDecoration(0f, RecyclerView.HORIZONTAL))
        val layoutManager = CenterLayoutManager(context,RecyclerView.HORIZONTAL,false)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        find.rvLevel1.layoutManager = layoutManager
        find.rvLevel1.adapter = categoryAdapter

//        find.displayAll.singleClick {
//            displayAllDialog.show(parentFragmentManager, firstCategoryList, firstPosition, this)
//        }

        find.displayAll.singleClick {
            displayAllDialog?.show(parentFragmentManager, firstCategoryList, firstPosition!!, this)
        }

        secondClassificationAdapter = SecondClassificationAdapter()
        find.rvLevel2.addItemDecoration(CommonItemDecoration(0f, RecyclerView.VERTICAL))
        val layoutManager2 = LinearLayoutManager(context)
        find.rvLevel2.layoutManager = layoutManager2
        find.rvLevel2.adapter = secondClassificationAdapter

        resourceAdapter = ResourceAdapter()
        // 添加Android自带的分割线
//        find.rvResource.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        secondClassificationAdapter?.resourceAdapter = resourceAdapter
        find.level3.addItemDecoration(CommonItemDecoration(5f, RecyclerView.VERTICAL))
        find.level3.layoutManager = LinearLayoutManager(context)
        find.level3.adapter = resourceAdapter

        // 新回调接口 点击一级分类item刷新二级分类数据,并默认显示第一个
        categoryAdapter?.refreshSecondCategory(object :
            ClassificationAdapter.RefreshSecondCategory {
            override fun refreshSecondCategory(entity: FirstCategory, position: Int) {
                if(firstPosition != null) {
                    layoutManager.smoothScrollToPosition(
                        find.rvLevel1,
                        RecyclerView.State(),
                        position
                    )
                }
                displayAllDialog?.categoryDialogAdapter?.nowChoose = position
                firstPosition = position
                val newSecondCategoryData = entity.list
                newSecondCategoryData?.let {
                    secondClassificationAdapter?.clear()
                    resourceAdapter?.clear()
                    secondClassificationAdapter?.add(it)
                    // 每点击一个一级分类，改一级分类下的二级分类第一个默认为选中状态，并在资源列表刷新该二级分类下的资源
                    secondClassificationAdapter?.refresh()
                    it.getOrNull(0)?.id?.let { it1 -> submitResourceData(it1) }
                }
            }
        })
        find.level1.post {
            val location = IntArray(2)
            find.level1.getLocationOnScreen(location)
            val absoluteY = location[1]
            displayAllDialog = DisplayAllDialog(absoluteY + find.level1.height)
            Log.d("absoluteY", "$absoluteY")
        }
        // 原回调接口
//        categoryAdapter?.setOnItemClickListener(object :
//            CategoryAdapter.OnItemClickListener {
//            override fun onItemClick(entity: FirstCategory, position: Int) {
//            }
//        })

        // 点击二级分类刷新资源列表
        refreshResource()
        secondClassificationAdapter?.setOnItemClickListener(object :
            SecondClassificationAdapter.OnItemClickListener {
            override fun onItemClick(entity: SecondCategory, position: Int) {
                val newResourceId = entity.id
                Log.d("Test", "我点击了二级分类")
                newResourceId?.let {
                    submitResourceData(it)
                }
            }
        })

//        refreshFirstCategory(0)
    }

    private fun refreshResource() {
    }

//    private fun searchCarousel() {
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.isSmoothScrollbarEnabled = false
//        find.rvSearch.layoutManager = layoutManager
//        find.rvSearch.adapter = searchAdapter
//        searchList?.let {
//            searchAdapter.clearData()
//            searchAdapter.submitData(it)
//        }
//        // 轮播
//        ScheduleTask(lifecycle, 5, TimeUnit.SECONDS) {
//            if (layoutManager.itemCount > 0) {
//                find.rvSearch.scrollToPosition((layoutManager.findFirstVisibleItemPosition() + 1) % layoutManager.itemCount)
//            }
//        }.startTask()
//    }

    override fun subscribeUi() {
        viewModel.liveData.observe(this) { res ->
            res.onSuccess { data: CategoryDetailResponse? ->
                data?.first?.let {
                    firstPosition = null
                    firstCategoryList = it
                    categoryAdapter?.clearAndAdd(it)
                    // 初始第一个一级分类为点击状态
                    refreshFirstCategory(0)

                    // 默认显示第一个一级分类下第一个二级分类中的资源数据
//                    viewModel.resourceLiveData.observe(this) { res: Resource<ResourceTypeList> ->
//                        res.onSuccess { data: ResourceTypeList? ->
//                            data?.titleContentList?.let {
//                                resourceAdapter?.submitData(it)
//                            }
//                        }.onError { error, response ->
//                            error?.message.toast()
//                        }
//                    }
                    if (data.first != null && data.first?.size!! > 0 &&
                        data.first!![0].list != null &&
                        data.first!![0].list?.size!! > 0
                    ) {
//                        data.firstCategoryList!![0].secondCategory?.get(0)?.id?.let { it1 ->
//                            viewModel.getResourceList(
//                                it1
//                            )
//                        }
                    }
                }
            }
            res.onError { error, response ->

            }
            Log.d("RRRRRR", "我进入接口了")
        }
        viewModel.getAll()


        viewModel.resourceListLiveData.observe(this) { res ->
            res.onSuccess {
                resourceAdapter?.clear()
                it?.let {
                    for (i in 0 until it.resourceInfos.size) {
                        val info = it.resourceInfos[i]
                        resourceAdapter?.add(
                            Resource(
                                info.createTime,
                                resourcePhoto = info.photo,
                                resourceAuthorHead = info.authorHead,
                                resourceAuthorName = info.author,
                                resourceDescribe = info.describe,
                                resourceLikes = info.likes.toLong(),
                                resourceReads = info.readHot.toLong(),
                                resourceType = info.type,
                                scheme = info.scheme,
                                resourceTitle = info.title
                            )
                        )
                    }
                    resourceAdapter?.add(
                        Resource(resourceType = 5)
                    )
                    resourceAdapter?.notifyItemRangeChanged(0,resourceAdapter?.itemCount!! - 1)
                }
            }.onError { error, _ ->
                error?.message.toast()
            }
        }
        IdentityUtil.instance.state.observe(this){
            viewModel.getAll()
        }
    }

    private fun submitResourceData(newResourceId: Int) {
        resourceAdapter?.clear()
//        resourceAdapter?.add(list)
        newResourceId.let { viewModel.getResourceList(it) }
    }


    fun refreshFirstCategory(position: Int) {
        categoryAdapter?.refreshFirstCategory(position)
    }
}
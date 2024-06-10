package org.jxxy.debug.home.Fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.http.bean.Resource
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.home.Fragment.Listener.aListener
import org.jxxy.debug.home.Fragment.adapter.CommonHomeAdapter
import org.jxxy.debug.home.Fragment.adapter.FallAdapter
import org.jxxy.debug.home.Fragment.http.viewmodel.HomeViewModel
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.Fragment.item.bean.FallBean
import org.jxxy.debug.home.databinding.FragmentPopularizeBinding

class CommonHomeFragment() : BaseFragment<FragmentPopularizeBinding>(){
   constructor(type:Int,aListener: aListener?=null):this(){
       this.type = type
       this.aListener = aListener
   }
    var type:Int = 1
    private var aListener:aListener ?=null
    private var homeAdapter: CommonHomeAdapter? = null
    private var fallAdapter: FallAdapter? = null
    private var nowPage: Int = 1
    private var currentCityId: String? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    override fun bindLayout(): FragmentPopularizeBinding {
        return FragmentPopularizeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        homeAdapter = CommonHomeAdapter(lifecycle, parentFragmentManager,lifecycleScope)
        find.rvHome.addItemDecoration(CommonItemDecoration(10f, RecyclerView.VERTICAL))
        find.rvHome.layoutManager = LinearLayoutManager(context)
        find.rvHome.adapter = homeAdapter
        fallAdapter = FallAdapter()
        // 修改阻尼效果（0 - 1），越小阻尼越大，默认0.5
        find.refresh.setDragRate(0.6f)
        // 设置主题颜色
        find.refresh.setPrimaryColorsId(org.jxxy.debug.common.R.color.white)
        find.refresh.setEnableRefresh(false)
        find.refresh.setEnableLoadMore(false)
        if (type == 1){
            find.refresh.setEnableLoadMore(true)
            find.refresh.setRefreshFooter(ClassicsFooter(requireContext()))
            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            find.rvFall.layoutManager = staggeredGridLayoutManager
            find.rvFall.addItemDecoration(SpanItemDecoration(3f, 5f, 2))
            find.rvFall.adapter = fallAdapter
            find.refresh.setOnLoadMoreListener { memberRefreshLayout: RefreshLayout ->
                viewModel.getFalls(nowPage++,6)
                memberRefreshLayout.finishLoadMore(200)
            }
            find.refresh.setOnRefreshListener {
//            refresh()
            }
        }
    }

    override fun subscribeUi() {
        viewModel.homeLiveData.observe(this) { res ->
            Log.d("HomeFragment", "LiveData received: $res")
            res.onSuccess { data->
                Log.d("HomeFragment", "Data received: $data")
                data?.list?.let {
                    homeAdapter?.clear()
                    Log.d("HomeFragment", "$it")
                    aListener?.b()
                    homeAdapter?.add(it)
                    find.rvHome.smoothScrollToPosition(0)
                    if (type == 1)viewModel.getFalls()
                    find.refresh.finishRefresh(true)
                    Log.d("HomeFragment", "Data added to HomeAdapter: $it")
                    return@onSuccess
                }
            }
            find.refresh.finishRefresh(false)
        }
        viewModel.FallLiveData.observe(this) { res: Resource<Fall> ->
            res.onSuccess { data: Fall? ->
                data?.fallInfos?.let {
                    fallAdapter?.add(it)
//                    fallAdapter?.add(FallBean(
//                            "漫画图解人工智能",
//                            4,
//                            "aa",
//                            "",
//                            "",
//                            "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/271b2557-a1fe-4374-8e39-1a072035476a.png",
//                            "2023-09-04",
//                            "浙江", Scheme(Scheme.DETAIL).apply {
//                                resourceId = 124
//                        }
//                            ))
                    if (it.size == 0) "已经没有更多了".toast(true)
                }
                find.refresh.finishLoadMore()
            }
        }
        viewModel.getHome(type)
    }
    override fun onResume() {
        super.onResume()
    }
}
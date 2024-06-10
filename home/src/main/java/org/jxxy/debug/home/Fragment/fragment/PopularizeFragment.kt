package org.jxxy.debug.home.Fragment.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.http.AddressManager
import org.jxxy.debug.corekit.http.bean.Resource
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.NoticeRefresh
import org.jxxy.debug.home.Fragment.adapter.FallAdapter
import org.jxxy.debug.home.Fragment.adapter.HomeAdapter
import org.jxxy.debug.home.Fragment.http.response.HomeFloor
import org.jxxy.debug.home.Fragment.http.viewmodel.HomeViewModel
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.databinding.FragmentPopularizeBinding

class PopularizeFragment : BaseFragment<FragmentPopularizeBinding>(), NoticeRefresh {
    private var homeAdapter: HomeAdapter? = null
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
        homeAdapter = HomeAdapter(lifecycle, parentFragmentManager)
        find.rvHome.addItemDecoration(CommonItemDecoration(10f, RecyclerView.VERTICAL))
        find.rvHome.layoutManager = LinearLayoutManager(context)
        find.rvHome.adapter = homeAdapter
        viewModel.getHomeFloor()
        fallAdapter = FallAdapter()
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        find.rvFall.layoutManager = staggeredGridLayoutManager
        find.rvFall.addItemDecoration(SpanItemDecoration(3f, 5f, 2))
        find.rvFall.adapter = fallAdapter

        // 修改阻尼效果（0 - 1），越小阻尼越大，默认0.5
        find.refresh.setDragRate(0.6f)
        // 设置主题颜色
        find.refresh.setPrimaryColorsId(org.jxxy.debug.common.R.color.white)
        find.refresh.setEnableRefresh(true)
        find.refresh.setEnableLoadMore(true)
        find.refresh.setOnLoadMoreListener { memberRefreshLayout: RefreshLayout ->
            viewModel.getFalls(++nowPage,10)
            memberRefreshLayout.finishLoadMore(200)
        }
        find.refresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun subscribeUi() {
        viewModel.liveData.observe(this) { res: Resource<HomeFloor> ->
            Log.d("HomeFragment", "LiveData received: $res")
            res.onSuccess { data: HomeFloor? ->
                Log.d("HomeFragment", "Data received: $data")
                data?.list?.let {
                    homeAdapter?.clear()
                    homeAdapter?.add(it)
                    viewModel.getFalls()
                    find.refresh.finishRefresh(true)
                    Log.d("HomeFragment", "Data added to HomeAdapter: $it")
                    return@onSuccess
                }
            }
            find.refresh.finishRefresh(false)
        }
        viewModel.FallLiveData.observe(this) { res: Resource<Fall> ->
            res.onSuccess { data: Fall? ->
                data?.fallInfos.let {
                    if (nowPage == 1) {
                        fallAdapter?.clear()
                    }
//                    fallAdapter?.add(Fall(13,"瀑布流",it))
                    if (it?.size!! < 6) {
                        find.refresh.finishLoadMoreWithNoMoreData()
                    } else {
                        find.refresh.finishLoadMore()
                    }
                    return@onSuccess
                }
                find.refresh.finishLoadMore(false)
            }
        }
    }

    override fun refresh() {
        currentCityId = AddressManager.getCityId()
        nowPage = 1
        viewModel.getHomeFloor()
    }
    override fun onResume() {
        super.onResume()
        refresh()
    }
}

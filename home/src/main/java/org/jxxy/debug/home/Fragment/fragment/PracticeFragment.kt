package org.jxxy.debug.home.Fragment.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.http.bean.Resource
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.home.Fragment.NoticeRefresh
import org.jxxy.debug.home.Fragment.adapter.PracticeAdapter
import org.jxxy.debug.home.Fragment.http.response.PracticeFloor
import org.jxxy.debug.home.Fragment.http.viewmodel.HomeViewModel
import org.jxxy.debug.home.Fragment.item.*
import org.jxxy.debug.home.Fragment.item.bean.*
import org.jxxy.debug.home.databinding.FragmentHomeBinding
import org.jxxy.debug.home.databinding.FragmentPracticeBinding

class PracticeFragment : BaseFragment<FragmentPracticeBinding>(), NoticeRefresh {
    private var practiceAdapter: PracticeAdapter?=null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun bindLayout(): FragmentPracticeBinding {
        return FragmentPracticeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        practiceAdapter = PracticeAdapter(lifecycle, parentFragmentManager)
        find.rvPractice.addItemDecoration(CommonItemDecoration(11f, RecyclerView.VERTICAL))
        find.rvPractice.layoutManager = LinearLayoutManager(context)
        find.rvPractice.adapter = practiceAdapter
        // 修改阻尼效果（0 - 1），越小阻尼越大，默认0.5
        find.refresh.setDragRate(0.6f)
        // 设置主题颜色
        find.refresh.setPrimaryColorsId(org.jxxy.debug.common.R.color.white)
        find.refresh.setEnableRefresh(true)
        find.refresh.setEnableLoadMore(true)
        find.refresh.setOnLoadMoreListener { memberRefreshLayout: RefreshLayout ->
            memberRefreshLayout.finishLoadMore(200)
        }
    }

    override fun subscribeUi() {
        viewModel.PracticeLiveData.observe(this) { res: Resource<PracticeFloor> ->
            Log.d("PracticeFragment", "LiveData received: $res")
            res.onSuccess { data: PracticeFloor? ->
                Log.d("PracticeFragment", "Data received: $data")
                data?.list?.let {
                    practiceAdapter?.clear()
                    practiceAdapter?.add(it)
                    find.refresh.finishRefresh(true)
                    Log.d("PracticeFragment", "Data added to PracticeAdapter: $it")
                    return@onSuccess
                }
            }
            find.refresh.finishRefresh(false)
        }
        viewModel.getPracticeFloor()
    }
    override fun refresh(){
        viewModel.getPracticeFloor()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
}
package org.jxxy.debug.home.Fragment.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.http.bean.Resource
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.home.Fragment.NoticeRefresh
import org.jxxy.debug.home.Fragment.adapter.StudyAdapter
import org.jxxy.debug.home.Fragment.http.viewmodel.HomeViewModel
import org.jxxy.debug.home.Fragment.item.*
import org.jxxy.debug.home.databinding.FragmentStudyBinding

class StudyFragment : BaseFragment<FragmentStudyBinding>() , NoticeRefresh {
    private var studyAdapter:StudyAdapter?=null
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun bindLayout(): FragmentStudyBinding {
        return FragmentStudyBinding.inflate(layoutInflater)
    }

    override fun initView() {
        studyAdapter = StudyAdapter(lifecycle, parentFragmentManager,lifecycleScope)
        find.rvEducate.addItemDecoration(CommonItemDecoration(11f, RecyclerView.VERTICAL))
        find.rvEducate.layoutManager = LinearLayoutManager(context)
        find.rvEducate.adapter = studyAdapter

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
        viewModel.StudyLiveData.observe(this) { res: Resource<StudyFloor> ->
            Log.d("HomeFragment", "LiveData received: $res")
            res.onSuccess { data: StudyFloor? ->
                Log.d("HomeFragment", "Data received: $data")
                data?.list?.let {
                    studyAdapter?.clear()
                    studyAdapter?.add(it)
                    find.refresh.finishRefresh(true)
                    Log.d("HomeFragment", "Data added to HomeAdapter: $it")
                    return@onSuccess
                }
            }
            find.refresh.finishRefresh(false)
        }
        viewModel.getStudyFloor()
    }
    override fun refresh(){
        viewModel.getStudyFloor()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
}
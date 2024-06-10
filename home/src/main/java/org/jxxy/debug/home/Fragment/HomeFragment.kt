package org.jxxy.debug.home.Fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import navigation
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.service.IdentityService
import org.jxxy.debug.common.service.gonSearchMiddle
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.home.Fragment.Listener.aListener
import org.jxxy.debug.home.Fragment.adapter.HomeAdapter
import org.jxxy.debug.home.Fragment.adapter.HomePagerAdapter
import org.jxxy.debug.home.Fragment.adapter.IdentityNameAdapter
import org.jxxy.debug.home.Fragment.http.viewmodel.HomeViewModel
import org.jxxy.debug.home.Fragment.item.bean.SearchHintBean
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.FragmentHomeBinding
import org.jxxy.debug.home.databinding.HomeTabBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var homePagerAdapter: HomePagerAdapter? = null
    private var homeAdapter: HomeAdapter? = null
    private var identityAdapter:IdentityNameAdapter?=null
    private val tabScaleFactor = 1.2f // 放大的比例
    private val tabDefaultScale = 1.0f // 默认比例
    private val searchItems = arrayOf("#网络数据人工智能安全治理分论坛在津召开", "#汉鑫科技：三大业务齐突破 加码人工智能", "#人工智能趋势不变，金山云抢布MaaS先发优势初现")
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 3000L
    private var currentIndex = 0
    private var nowPage: Int = 0
    private var temp :GifDrawable?=null
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private var currentCityId: String? = null
    private lateinit var runnable: Runnable

    override fun bindLayout(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.getIdentity(0)
        find.con3.singleClick {
            context?.let { it1 -> gonSearchMiddle(it1) }
        }
        find.tv2.singleClick {
            CommonServiceManager.service<IdentityService>()?.goSelectIdentity(context)
        }
        find.switchIdentityIcon.singleClick {
            CommonServiceManager.service<IdentityService>()?.goSelectIdentity(context)
        }
         //创建一个 Runnable 对象，用于定时切换搜索栏文字
        runnable = object : Runnable {
            override fun run() {
                // 切换搜索栏文字
                val nextIndex = (currentIndex + 1) % searchItems.size
               /* find.tv.text = searchItems[nextIndex]*/

                // 更新索引
                currentIndex = nextIndex

                // 延迟执行下一次任务
                handler.postDelayed(this, delayMillis)
            }
        }
        // 开始执行定时任务
        handler.postDelayed(runnable, delayMillis)
        val searchHintBeans: List<SearchHintBean> = searchItems.mapIndexed { index, item ->
            SearchHintBean(index + 1, item)
        }
        find.sousuokuang .initFactory(14f,false)
        find.sousuokuang.setData(searchHintBeans,lifecycleScope )
    }

    override fun onDestroyView() {
        // 移除定时任务
        handler.removeCallbacks(runnable)
        super.onDestroyView()
    }

    private fun animateTabView(tabItemView: TextView, isSelected: Boolean) {
        if (tabItemView is TextView) {
            if (isSelected) {
                tabItemView.animate()
                    .scaleX(tabScaleFactor)
                    .scaleY(tabScaleFactor)
                    .setDuration(200)
                    .start()
                tabItemView.gravity = Gravity.CENTER // 设置文本居中
                tabItemView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
            } else {
                tabItemView.animate()
                    .scaleX(tabDefaultScale)
                    .scaleY(tabDefaultScale)
                    .setDuration(200)
                    .start()
                tabItemView.setPadding(0, 0, 0, 0) // 清除 padding
                tabItemView.gravity = Gravity.CENTER_VERTICAL // 恢复文本的默认位置
                tabItemView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_200))
            }
        }
    }

    private fun refresh() {
        viewModel.getIdentity(IdentityUtil.instance.state.value ?: 0)
        viewModel.getTab()
    }
    override fun subscribeUi() {
        IdentityUtil.instance.state.observe(this){
            refresh()
        }
        viewModel.tabLiveData.observe(this){
            it.onSuccess {
                it?.tabInfos?.let {
                    val fragment = it.map {
                        CommonHomeFragment(it.type,aListener)
                    }
                    homePagerAdapter = HomePagerAdapter(requireActivity(),fragment)
//                    find.loadingView.start()
                    val viewpager = find.viewpager
                    viewpager.adapter = homePagerAdapter
                    val tabLayout = find.tab
                    TabLayoutMediator(tabLayout, viewpager) { tab, position ->
                        val binding = HomeTabBinding.inflate(layoutInflater,tabLayout,false)
                        tab.customView = binding.root
                        tab.tag = binding
                     binding.tabTV.text = it[position].name
//                        binding.iconIV.load(it[position].icon)
                    }.attach()
                    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val tag = tab?.tag
                            if(tag is HomeTabBinding){
                                animateTabView(tag.tabTV,true)
                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                            val tag = tab?.tag
                            if(tag is HomeTabBinding){
                                animateTabView(tag.tabTV,false)
                            }
                        }
                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }

                    })
                    val homeTabBinding = tabLayout.getTabAt(0)?.tag as HomeTabBinding
                    animateTabView(homeTabBinding.tabTV,true)
                }
            }
        }
        viewModel.IdentityNameLiveData.observe(this) {
            it.onSuccess {
                find.tv2.text = it
                Log.i("2333","2333")
            }
            it.onError { error, response ->
            }
            //確實是網絡請求出錯了，沒走進success這裡
        }
        loginCheck(requireContext()){
            refresh()
        }
    }
    val aListener by lazy {
        object : aListener {
            override fun b() {
//                "hhh".toast()
//                find.loadingView.close()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
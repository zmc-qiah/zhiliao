package org.jxxy.debug.activity

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.igexin.sdk.PushManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import navigation
import org.jxxy.debug.R
import org.jxxy.debug.bean.TabIconBean
import org.jxxy.debug.classification.fragment.ClassificationFragment
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.service.isLogin
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.databinding.ActivityMainBinding
import org.jxxy.debug.home.Fragment.HomeFragment
import org.jxxy.debug.login.http.repository.LoginRepository
import org.jxxy.debug.member.fragment.MemberRecycleViewFragment
import org.jxxy.debug.test.fragment.fragment.TestFragment
import org.jxxy.debug.theme.fragment.ThemeFragment


// 你要是自己写activity运行不了，记得去AndroidManifest.xml看看有没有添加进去
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var memberFragment: MemberRecycleViewFragment? = null
    private var classificationFragment: ClassificationFragment? = null
    private var homeFragment: HomeFragment? = null
    private var testFragment: TestFragment? = null
    private var themeFragment: ThemeFragment? = null
    private val tabList: List<TabIconBean> by lazy {
        listOf(TabIconBean(view.homeTab, view.homeTabIcon, view.homeTabTv), TabIconBean(view.categoryTab, view.categoryTabIcon, view.categoryTabTv), TabIconBean(view.topicTab, view.topicTabIcon, view.topicTabTv), TabIconBean(view.memberTab, view.memberTabIcon, view.memberTabTv))
    }
    var selected: Int = 0
    override fun bindLayout(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        var height = getHeight()
        val node = Node("记录灵光瞬间")
        Log.d("aaaaaaaaaaaaaaaaaa", "initView:aaxacsa${GsonManager.instance.gson.toJson(node)} ")
        height -= view.bar.layoutParams.height
        view.tabFragment.layoutParams.height = height + 5
        tabList.forEach {
            it.tab.singleClick {
                onclick(it)
            }
        }
        view.themeTab.singleClick { onclick(it) }
        PushManager.getInstance().initialize(this)
        view.themeTab.singleClick { onclick(it) }
    }

    override fun subscribeUi() {
        when(intent.getIntExtra("selected",0)){
            -1->onclick(view.themeTab)
            0->onclick(view.homeTab)
            1->onclick(view.categoryTab)
            2->onclick(view.topicTab)
            3->onclick(view.memberTab)
        }
        if (isLogin()){
            lifecycleScope.launch(Dispatchers.IO){
                LoginRepository().init(TokenManager.getToken()!!)
            }
        }
    }

    private fun hideFragment(fragmentTransaction: FragmentTransaction) {
        when (selected) {
            3 -> {
                memberFragment?.let { fragmentTransaction.hide(it) }
            }
            1 -> {
                classificationFragment?.let { fragmentTransaction.hide(it) }
            }
            2 -> {
                testFragment?.let { fragmentTransaction.hide(it) }
            }
            0 -> {
                homeFragment?.let { fragmentTransaction.hide(it) }
            }
            -1 ->{
                themeFragment?.let {
                    fragmentTransaction.hide(it)
                }
            }
        }
        tabList.forEach {
            it.icon.setTextColor(ResourceUtil.getColor(R.color.black))
            it.text.setTextColor(ResourceUtil.getColor(R.color.black))
        }
    }

    private fun onclick(v: View) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideFragment(fragmentTransaction)
        when (v.id) {
            R.id.homeTab -> {
                homeFragment.nullOrNot({
                    homeFragment = HomeFragment()
                    fragmentTransaction.add(R.id.tabFragment, homeFragment!!)
                }, {
                    fragmentTransaction.show(it)
                })
                selected = 0
            }
            R.id.categoryTab -> {
                classificationFragment.nullOrNot({
                    classificationFragment = ClassificationFragment()
                    fragmentTransaction.add(R.id.tabFragment, classificationFragment!!)
                }, {
                    fragmentTransaction.show(it)
                })
                selected = 1
            }
            R.id.topicTab -> {
                testFragment.nullOrNot({
                    testFragment = TestFragment()
                    fragmentTransaction.add(R.id.tabFragment, testFragment!!)
                }, {
                    fragmentTransaction.show(it)
                })
                selected = 2
            }
            R.id.memberTab -> {
                memberFragment.nullOrNot({
                    memberFragment = MemberRecycleViewFragment()
                    fragmentTransaction.add(R.id.tabFragment, memberFragment!!)
                }, {
                    fragmentTransaction.show(it)
                })
                selected = 3
            }
            R.id.themeTab ->{
                themeFragment.nullOrNot({
                    themeFragment = ThemeFragment()
                    fragmentTransaction.add(R.id.tabFragment, themeFragment!!)
                }, {
                    fragmentTransaction.show(it)
                })
                selected = -1
            }
        }
        tabList.getOrNull(selected)?.let {
            it.icon.setTextColor(ResourceUtil.getColor(R.color.icon_select))
            it.text.setTextColor(ResourceUtil.getColor(R.color.icon_select))
        }
        fragmentTransaction.commit()
    }

    var flag = false
    override fun onBackPressed() {
        if(selected != 0){
            onclick(view.homeTab)
        }else if (flag){
            super.onBackPressed()
        }else{
            "再次滑动退出app".toast()
            flag = true
            lifecycleScope.launch {
                delay(2000)
                flag = false
            }
        }
    }
}

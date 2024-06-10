package org.jxxy.debug.test.fragment.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.test.databinding.ActivityAchievementBinding
import org.jxxy.debug.test.fragment.adapter.AchievementAdapter
import org.jxxy.debug.test.fragment.bean.Achievement
import org.jxxy.debug.test.fragment.viewModel.AchievementViewModel

class AchievementActivity : BaseActivity<ActivityAchievementBinding>() {

    val viewModel : AchievementViewModel by lazy {
        ViewModelProvider(this).get(AchievementViewModel::class.java)
    }
    lateinit var adapter : AchievementAdapter
    override fun bindLayout(): ActivityAchievementBinding {
        return ActivityAchievementBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val url = "https://tse4-mm.cn.bing.net/th/id/OIP-C.p5crZub0E5fUyHyZ0tfjBAHaHa?w=210&h=210&c=7&r=0&o=5&dpr=1.3&pid=1.7"
        adapter = AchievementAdapter()
        adapter.way = {
            viewModel.useAchievement(it)
        }
        view.achievementRv.adapter = adapter
        adapter.fragmentManager = supportFragmentManager
        view.achievementRv.layoutManager = GridLayoutManager(this,2)
        view.achievementRv.addItemDecoration(SpanItemDecoration(10f,15f,2))
    }

    override fun subscribeUi() {
        viewModel.getAchievementLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    adapter.nowChoose = it.achievementShowInfo.id
                    adapter.add(it.achievementInfoList)
                    adapter.add(Achievement(-1,"","","",-1))
                    adapter.add(Achievement(-1,"","","",-1))
                }
            }
        }
        viewModel.getAchievement()
    }

}
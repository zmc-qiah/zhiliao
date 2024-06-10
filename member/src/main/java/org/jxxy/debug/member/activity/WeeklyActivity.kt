package org.jxxy.debug.member.activity


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.member.ViewPageHelp
import org.jxxy.debug.member.adapter.WeeklyAdapter
import org.jxxy.debug.member.databinding.ActivityWeeklyBinding
import org.jxxy.debug.member.fragment.Week1Fragment
import org.jxxy.debug.member.fragment.Week2Fragment
import org.jxxy.debug.member.fragment.Weekly3Fragment
import org.jxxy.debug.member.fragment.Weekly4Fragment
import org.jxxy.debug.member.fragment.Weekly5Fragment
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class WeeklyActivity() : BaseActivity<ActivityWeeklyBinding>(), ViewPageHelp {
    override fun bindLayout(): ActivityWeeklyBinding = ActivityWeeklyBinding.inflate(layoutInflater)
    val viewModel : MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    override fun initView() {
    viewModel.getReport()
    }

    override fun subscribeUi() {
        viewModel.reportLiveData.observe(this){
            it.onSuccess {
               it?.let {
                   val list = listOf<Fragment>(
                       Week1Fragment(),
                       Weekly4Fragment(it),
                       Week2Fragment(it),
                       Weekly3Fragment(it),
                       Weekly5Fragment(it)
                   )
                   view.weekly.adapter = WeeklyAdapter(list,this)
                   isSliding(false)
               }

            }
        }
    }

    override fun goPage(page: Int) {
        view.weekly.setCurrentItem(page)
    }

    override fun isSliding(flag: Boolean) {
        view.weekly.isUserInputEnabled = flag
    }
}

package org.jxxy.debug.member.fragment

import android.os.Build
import androidx.annotation.RequiresApi
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.AppUtils
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.ViewPageHelp
import org.jxxy.debug.member.databinding.FragmentWeek1Binding
import org.jxxy.debug.member.http.respone.WeekReportResponce
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class Week1Fragment() : BaseFragment<FragmentWeek1Binding>() {
    override fun bindLayout(): FragmentWeek1Binding = FragmentWeek1Binding.inflate(layoutInflater)
    val help: ViewPageHelp by lazy {
        activity as ViewPageHelp
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        val today = LocalDate.now()
        val startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
        val endOfWeek = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))
        find.userNameTextView.text = "@${PersistenceUtil.getValue("userName")?:"亲爱的用户"}"
        find.appPhoto.load(org.jxxy.debug.common.R.drawable.logo_1,true)
        find.appNameTextView.text = AppUtils.getAppName()
        find.weekDate1.text = startOfWeek.toString().replace("-", ".")
        find.weekDate2.text = endOfWeek.toString().replace("-", ".")
        find.weeklyButton.singleClick {
            help.goPage(1)
            help.isSliding(true)
        }
    }

    override fun subscribeUi() {
    }
}

package org.jxxy.debug.member.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goLogin
import org.jxxy.debug.common.service.isLogin
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.GroupBankActivity
import org.jxxy.debug.member.activity.PlanActivity
import org.jxxy.debug.member.activity.PointsTaskActivity
import org.jxxy.debug.member.activity.SettingActivity
import org.jxxy.debug.member.activity.UserDetailActivity
import org.jxxy.debug.member.activity.WeeklyActivity
import org.jxxy.debug.member.adapter.MemberAdapter
import org.jxxy.debug.member.bean.MemberData
import org.jxxy.debug.member.bean.MemberPoint
import org.jxxy.debug.member.databinding.FragmentMemberRvBinding
import org.jxxy.debug.member.http.respone.GroupForumResponse
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class MemberRecycleViewFragment : BaseFragment<FragmentMemberRvBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val loginLiveData: MutableLiveData<String?> = MutableLiveData()
    val adapter: MemberAdapter by lazy { MemberAdapter(requireActivity(), ::onclick) }
    private val TAG = "MemberFragment"
    override fun bindLayout(): FragmentMemberRvBinding = FragmentMemberRvBinding.inflate(layoutInflater)

    override fun initView() {
        find.point.gone()
        find.pointIcon1.gone()
        find.pointIcon2.gone()
        find.pointIcon3.gone()
        find.pointText1.gone()
        find.pointText2.gone()
        find.pointText3.gone()
        find.userTodayPointTV.gone()
        find.userSumPointTV.gone()
        find.userDaysTV.gone()
        find.settingIcon.gone()
        find.pointLeftView.gone()
        find.pointRightView.gone()
        find.memberRecycleView.adapter = adapter
        find.pointView.singleClick {
            context?.let { it1 ->
                loginCheck(it1){
                    it1.startActivity(PointsTaskActivity::class.java)
                }
            }
        }
        context?.let {it1->
            find.userPhotoIV.singleClick {
                loginCheck(it1){
                    it1.startActivity(UserDetailActivity::class.java)
                }
            }
            find.userNameTV.singleClick {
                loginCheck(it1){
                    it1.startActivity(UserDetailActivity::class.java)
                }
            }
            find.userDescribeTV.singleClick {
                loginCheck(it1){
                    it1.startActivity(UserDetailActivity::class.java)
                }
            }
            find.settingIcon.singleClick {
                loginCheck(it1){
                    it1.startActivity(SettingActivity::class.java)
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        // ea8dad00-1d37-4738-b661-4e49b5e8c3a3
        val token = TokenManager.getToken()
        if (!token.equals(loginLiveData.value)){
            loginLiveData.postValue(token)
        }
    }

    override fun subscribeUi() {
        loginLiveData.observe(this){
            viewModel.getMember()
        }
        viewModel.memberDataLiveRespone.observe(this) {
            it.onSuccess {
                it?.let {
                    adapter.clear()
                    it.memberBrief?.let {
                            it1 ->
                        find.userPhotoIV.load(it.memberBrief.headPhoto,true)
                        find.userNameTV.text = it.memberBrief.userName
                       if("".equals(it.memberBrief.userInfo)) find.userDescribeTV.text = "个性签名"
                        else find.userDescribeTV.text = it.memberBrief.userInfo
                        find.point.show()
                        find.pointIcon1.show()
                        find.pointIcon2.show()
                        find.pointIcon3.show()
                        find.pointText1.show()
                        find.pointText2.show()
                        find.pointText3.show()
                        find.userTodayPointTV.show()
                        find.userSumPointTV.show()
                        find.userDaysTV.show()
                        find.settingIcon.show()
                        find.pointLeftView.show()
                        find.pointRightView.show()
                        PersistenceUtil.putValue("userName",it.memberBrief.userName?:"游客")
//                        adapter.add(it1)
                        it.memberScore?.let {
                            find.userSumPointTV.text  =it.sumsScore.toString()
                            find.atv.text  =it.sumsScore.toString()+"P"
                            find.userDaysTV.text  =it1.togetherDay.toString()
                            find.userTodayPointTV.text  =it.todayScore.toString()
                            adapter.add(MemberPoint(it, it1.togetherDay))
                        }
                    }
                    if (it.member4Part != null && it.memberGroup != null) {
                        adapter.add(MemberData(it.memberGroup, it.member4Part))
                    }
                    it.memberStudyPlan?.let {
                        adapter.add(it)
                    }
                    adapter.add(GroupForumResponse())
                }
            }
        }
        loginLiveData.postValue(TokenManager.getToken())
    }

    fun onclick(view: View) {
        if (!isLogin()) {
            "当前未登录用户".toast()
            context?.let { goLogin(it) }
            return
        }
        if(view is ImageView){
            ImageClickDialog(view).show(parentFragmentManager)
        }
        when (view.id) {
            R.id.barView -> {
                context?.startActivity(GroupBankActivity::class.java)
            }
            R.id.groupBar -> {
                context?.startActivity(GroupBankActivity::class.java)
            }
            R.id.pointView -> {
                startActivity(Intent(activity, PointsTaskActivity::class.java))
            }
            R.id.userWeeklyButton -> {
                startActivity(Intent(activity, WeeklyActivity::class.java))
            }
            R.id.settingIcon -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
            R.id.userPlanNameTV -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
            R.id.usePlanBtn -> {
                startActivity(Intent(activity, PlanActivity::class.java))
            }
            R.id.userDetailBtn -> {
                context?.startActivity(UserDetailActivity::class.java)
            }
        }
    }
}
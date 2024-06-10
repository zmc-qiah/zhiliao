package org.jxxy.debug.member.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.member.bean.UserDetail
import org.jxxy.debug.member.databinding.ActivityDetailBinding
import org.jxxy.debug.member.databinding.ItemDetailImgBinding
import org.jxxy.debug.member.databinding.ItemDetailTextBinding
import org.jxxy.debug.member.http.respone.InfoRespone
import org.jxxy.debug.member.http.viewModel.MemberViewModel
import org.jxxy.debug.member.viewHolder.UserDetailImgViewHolder
import org.jxxy.debug.member.viewHolder.UserDetailTextViewHolder
import org.jxxy.debug.member.viewHolder.UserDetaliHelper

class UserDetailActivity : BaseActivity<ActivityDetailBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter = object : MultipleTypeAdapter() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when (viewType) {
            0 -> UserDetailImgViewHolder(ItemDetailImgBinding.inflate(layoutInflater))
            1 -> UserDetailTextViewHolder(
                ItemDetailTextBinding.inflate(layoutInflater),
                object : UserDetaliHelper {
                    override fun updateInfo(entiy: UserDetail, new: String) {
                        val fields = UserDetail::class.java.declaredFields
                        for (field in fields) {
                            if (field.name.equals(entiy.field)) {
                                UserInfo?.let {
                                    field.set(UserInfo, new)
                                    viewModel.updateInfo(it)
                                }
                            }
                        }
                    }
                }
            )
            else -> null
        }
    }
    var UserInfo: InfoRespone? = null
    override fun bindLayout(): ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel.getInfo()
        view.listRecyclerView.adapter = adapter
    }

    override fun subscribeUi() {
        viewModel.userInfoDataLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    UserInfo = it
                    adapter.add(UserDetail("头像", "headPhoto", it.headPhoto, 0))
                    adapter.add(UserDetail("昵称", "userName", it.userName, 1))
                    adapter.add(UserDetail("手机号", "userPhone", it.userPhone, 1))
                    adapter.add(UserDetail("性别", "userSex", it.userSex, 1))
                    adapter.add(UserDetail("年龄", "userAge", it.userAge.toString(), 1))
                    adapter.add(UserDetail("常驻城市", "location", it.location, 1))
                    adapter.add(UserDetail("兴趣", "userLikes", it.userLikes, 1))
                    adapter.add(UserDetail("成就", "achievement", it.achievement, 1))
                    adapter.add(UserDetail("创建时间", "createTime", it.createTime, 1))
                    adapter.add(UserDetail("工作", "work", it.work, 1))
                    adapter.add(UserDetail("介绍", "userInfo", it.userInfo, 1))
                }
            }
        }
    }
}

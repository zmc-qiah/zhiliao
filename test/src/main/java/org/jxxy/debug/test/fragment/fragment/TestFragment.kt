package org.jxxy.debug.test.fragment.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.databinding.FragmentTestBinding
import org.jxxy.debug.test.fragment.adapter.TestAdapter
import org.jxxy.debug.test.fragment.viewModel.AchievementViewModel

class TestFragment : BaseFragment<FragmentTestBinding>() {
    val viewModel: AchievementViewModel by lazy {
        ViewModelProvider(this).get(AchievementViewModel::class.java)
    }
    var picLiveData: MutableLiveData<Int> = MutableLiveData()
    override fun bindLayout(): FragmentTestBinding = FragmentTestBinding.inflate(layoutInflater)

    override fun initView() {
        val adapter = TestAdapter(ArrayList())
        adapter.fragmentManager = parentFragmentManager
        Glide.with(find.backgroundIv)
            .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/0d46fd16-82b2-44f7-b7cd-c6302fae6523.png")
            .into(find.backgroundIv)
        Glide.with(find.backgroundShowIv)
            .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/f0db6b06-b2e5-4928-9e75-4f6b7dab69f9.png")
            .into(find.backgroundShowIv)
//        find.backgroundIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/13/35d2aa24-bf33-4609-82c7-e54a9284e5d1.png")
        find.testRv.adapter = adapter
        find.testRv.layoutManager = LinearLayoutManager(context)
        find.testRv.addItemDecoration(CommonItemDecoration(10f))
//        find.fragmentTestIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/7ccd96e2-7c90-4c26-9239-da07e46afbe2.jpg")
//        find.fragmentTestTv.text = "成就4"
    }

    override fun subscribeUi() {
        viewModel.getAchievementLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    Glide.with(find.fragmentTestIv)
                        .load(it.achievementShowInfo.photoUrl)
                        .into(find.fragmentTestIv)
//                    find.fragmentTestIv.load(it.achievementShowInfo.photoUrl)
//                    find.fragmentTestTv.text = it.achievementShowInfo.name
                }
            }
        }
        viewModel.getAchievement()
        picLiveData.observe(this) {

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAchievement()
    }
}

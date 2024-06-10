package org.jxxy.debug.test.fragment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityInterVideoListBinding
import org.jxxy.debug.test.fragment.adapter.InterVideoListAdapter
import org.jxxy.debug.test.fragment.bean.InterVideo
import org.jxxy.debug.test.fragment.bean.QuestionVideo
import org.jxxy.debug.test.fragment.viewModel.QuestionVideoViewModel

class InterVideoListActivity : BaseActivity<ActivityInterVideoListBinding>() {
    val viewModel : QuestionVideoViewModel by lazy {
        ViewModelProvider(this).get(QuestionVideoViewModel::class.java)
    }
    override fun bindLayout(): ActivityInterVideoListBinding {
        return ActivityInterVideoListBinding.inflate(layoutInflater)
    }
    lateinit var adapter : InterVideoListAdapter

    override fun initView() {
//        val url = "https://ts1.cn.mm.bing.net/th/id/R-C.ae9164183ecdb09c8e01ada7af40f15a?rik=K%2bv8Rw%2fp3%2foOWQ&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2019%2f08-24%2f69216%2fwater_69216_698_515.91_.png&ehk=vsV2PwFoQo01Wr1fT92jbnlkn1Ln4tpa2ZbWWwqV1wA%3d&risl=&pid=ImgRaw&r=0"
//        val list = ArrayList<InterVideo>()
//        list.add(InterVideo(url,1,"谜题1"))
//        list.add(InterVideo(url,2,"谜题2"))
//        list.add(InterVideo(url,3,"谜题3"))
//        list.add(InterVideo(url,4,"谜题4"))
//        list.add(InterVideo(url,5,"谜题5"))
//        list.add(InterVideo(url,6,"谜题6"))
//        list.add(InterVideo(url,7,"谜题7"))
        adapter = InterVideoListAdapter()
        view.interVideoRl.layoutManager = LinearLayoutManager(this)
        view.interVideoRl.addItemDecoration(CommonItemDecoration(10f))
        view.interVideoRl.adapter = adapter


    }

    override fun subscribeUi() {
        viewModel.questionvideoListLiveData.observe(this){
            it.onSuccess {
                it?.let { it1 ->
                    adapter.add(it1.questionVideoEasyInfos)
                    adapter.add(QuestionVideo(0,"",""))
                }
            }
        }
        viewModel.getQuestionVideoList()
    }
}
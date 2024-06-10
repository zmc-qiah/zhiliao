package org.jxxy.debug.society.activity

import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.adapter.AibreakAdapter
import org.jxxy.debug.society.bean.AibreakBean
import org.jxxy.debug.society.databinding.ActivityAibreakBinding
import org.jxxy.debug.society.databinding.ActivityDiscussBinding

class AibreaklowActivity : BaseActivity<ActivityAibreakBinding>() {
    private val list =ArrayList<AibreakBean>()
    override fun bindLayout(): ActivityAibreakBinding {
       return ActivityAibreakBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
       view.aibreakRv.layoutManager=layoutManager
        initlist()
        println(list)
        val adapter = AibreakAdapter(list)
        view.aibreakRv.adapter=adapter
    }

    override fun subscribeUi() {

    }
    fun initlist(){
        repeat(5){
        list.add(AibreakBean("标题","ai犯法案例"))
    }}
}
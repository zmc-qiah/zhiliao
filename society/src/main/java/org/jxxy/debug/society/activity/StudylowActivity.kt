package org.jxxy.debug.society.activity

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.adapter.StudylowAdapter
import org.jxxy.debug.society.bean.StudyBean
import org.jxxy.debug.society.databinding.ActivityAibreakBinding
import org.jxxy.debug.society.databinding.ActivityStudylowBinding

class StudylowActivity: BaseActivity<ActivityStudylowBinding>() {
    private val list=ArrayList<StudyBean>()
    override fun bindLayout(): ActivityStudylowBinding {
        return ActivityStudylowBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initlist()
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        view.lowRv.layoutManager=layoutManager

        view.lowRv.adapter=StudylowAdapter(list)
    }

    override fun subscribeUi() {

    }
    fun initlist(){
        repeat(5){
            list.add(StudyBean("《法律》"))
        }
    }
}
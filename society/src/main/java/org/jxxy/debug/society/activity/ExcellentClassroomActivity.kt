package org.jxxy.debug.society.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.adapter.ExcellentClassAdapter
import org.jxxy.debug.society.bean.ExcellebtClassBean
import org.jxxy.debug.society.bean.ExcellentIntroduceBean
import org.jxxy.debug.society.bean.MyclassBean
import org.jxxy.debug.society.bean.RecommendclassBean
import org.jxxy.debug.society.databinding.ActivityExcellentclassroomBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import org.jxxy.debug.society.util.SpaceItemDecoration

class ExcellentClassroomActivity  : BaseActivity<ActivityExcellentclassroomBinding>() {
    val adapter= ExcellentClassAdapter()
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    override fun bindLayout(): ActivityExcellentclassroomBinding {
        return ActivityExcellentclassroomBinding.inflate(layoutInflater)
    }
    override fun initView() {
        viewModel.classget(1)
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        view.classRv.layoutManager=layoutManager
        view.classRv.addItemDecoration(SpaceItemDecoration(20,0))
        view.classRv.adapter=adapter

    }
    override fun subscribeUi() {
        viewModel.classgetData.observe(this) { res ->
            res.onSuccess { r ->
                adapter.add( ExcellebtClassBean(0, "\"从0开始学\""+r!!.classInfos?.get(0)!!.title!!,r.classInfos?.get(0)!!.photo!!,r.classInfos?.get(0)!!.scheme!!))

                adapter.add(ExcellentIntroduceBean(3,"推荐课"))

                adapter.add(RecommendclassBean(1,r.classInfos?.get(1)!!.describe!!,r.classInfos?.get(1)!!.photo!!,r.classInfos?.get(1)!!.title!!,r.classInfos?.get(1)!!.scheme!!))
                adapter.add(RecommendclassBean(1,r.classInfos?.get(2)!!.describe!!,r.classInfos?.get(2)!!.photo!!,r.classInfos?.get(2)!!.title!!,r.classInfos?.get(2)!!.scheme!!))
                adapter.add(RecommendclassBean(1,r.classInfos?.get(3)!!.describe!!,r.classInfos?.get(3)!!.photo!!,r.classInfos?.get(3)!!.title!!,r.classInfos?.get(3)!!.scheme!!))
                adapter.add(ExcellentIntroduceBean(3,"我的课程"))
                adapter.add(MyclassBean(2,r.classInfos?.get(4)!!.describe!!,r.classInfos?.get(4)!!.photo!!,r.classInfos?.get(4)!!.title!!,r.classInfos?.get(4)!!.scheme!!))
                adapter.add(MyclassBean(2,r.classInfos?.get(4)!!.describe!!,r.classInfos?.get(3)!!.photo!!,r.classInfos?.get(3)!!.title!!,r.classInfos?.get(3)!!.scheme!!))
                adapter.add(MyclassBean(2,r.classInfos?.get(4)!!.describe!!,r.classInfos?.get(2)!!.photo!!,r.classInfos?.get(2)!!.title!!,r.classInfos?.get(2)!!.scheme!!))

            }

            view.classRv .adapter= adapter
        }



    }
    }




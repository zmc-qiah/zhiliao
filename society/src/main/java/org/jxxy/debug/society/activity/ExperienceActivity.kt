package org.jxxy.debug.society.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.R
import org.jxxy.debug.society.adapter.ExperienceAdapter
import org.jxxy.debug.society.bean.ExpfinshBean
import org.jxxy.debug.society.bean.ExptodayBean
import org.jxxy.debug.society.databinding.ActivityExperienceBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import org.jxxy.debug.society.util.SpaceItemDecoration

class ExperienceActivity: BaseActivity<ActivityExperienceBinding>() {
    val adapter= ExperienceAdapter()
    val list=ArrayList<ExpfinshBean>()
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    override fun bindLayout(): ActivityExperienceBinding {
        return ActivityExperienceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.shareget()

        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        view.expRv.layoutManager=layoutManager
        view.expRv.addItemDecoration(SpaceItemDecoration(40,0))


    }

    override fun subscribeUi() {
        viewModel.sharegetData.observe(this){res ->
            res.onSuccess { r ->

                view.expImg.load(r!!.background)


                adapter.add(ExptodayBean(0,r.date!!))
                list.addAll(r.activityShareInfo!!.map {
                    ExpfinshBean(1,it!!.shareContent!!,it.sharePhoto!!,it.activityInfo!!.title!!,it.userInfo!!.headPhoto!!,it.userInfo!!.userName!!,it.year!!,it.mouth!!.toInt().toString()+"月",it.day!!.toInt().toString())
                })
                list.forEach { bean ->
                    adapter.add(bean)
                }

            }
        }
        view.expRv.adapter= adapter

        }


}

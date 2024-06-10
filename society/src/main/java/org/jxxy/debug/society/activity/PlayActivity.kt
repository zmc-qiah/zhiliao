package org.jxxy.debug.society.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.R
import org.jxxy.debug.society.adapter.LectureAdapter
import org.jxxy.debug.society.adapter.LectureTimeAdapter
import org.jxxy.debug.society.adapter.MovableImgAdapter
import org.jxxy.debug.society.adapter.PlayAdapter
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.bean.LectureTimeBean
import org.jxxy.debug.society.bean.MovableImgBean
import org.jxxy.debug.society.bean.PlayBean
import org.jxxy.debug.society.databinding.ActivityPlayBinding
import org.jxxy.debug.society.databinding.ActivitySocietyLectureBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import org.jxxy.debug.society.util.SpaceItemDecoration

class PlayActivity : BaseActivity<ActivityPlayBinding>() {
    private val list=ArrayList<PlayBean>()
    private  val list2 =ArrayList<LectureTimeBean>()
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    override fun bindLayout(): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.discussionget()
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        val timeRvLayoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.HORIZONTAL
        )
        view.seminarRv .layoutManager=layoutManager
        view.seminarRv .addItemDecoration(SpaceItemDecoration(20,0))
        view.seminarRv .adapter= PlayAdapter(list)

        //时间日期

        initlist()
        view.timeRv.adapter= LectureTimeAdapter(list2)
        view.timeRv.layoutManager=timeRvLayoutManager
        view.timeRv.addItemDecoration(SpaceItemDecoration(0,10))
    }

    override fun subscribeUi() {
        viewModel.discussiongetData .observe(this) { res ->
            res.onSuccess { r ->
                repeat(5){
                    list.addAll(r!!.lectureInfos !!.map { PlayBean(it!!.lecturePhoto!!,it.lectureTitle!!,it.lecturePlace !!,it.lectureNums !! ) })
                }
            }
            view.seminarRv.adapter= PlayAdapter(list)
        }
    }
    fun initlist(){
        repeat(5){
            list2.add(LectureTimeBean("周一","8月8日"))
            list2.add(LectureTimeBean("周二","8月9日"))
            list2.add(LectureTimeBean("周三","8月10日"))
        }}
}
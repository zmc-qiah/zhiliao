package org.jxxy.debug.society.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.society.adapter.MovableImgAdapter
import org.jxxy.debug.society.bean.MovableImgBean
import org.jxxy.debug.society.databinding.ActivityMovableBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import java.util.Timer
import java.util.TimerTask

class MovableActivity : BaseActivity<ActivityMovableBinding>() {
    private val list2 =ArrayList<MovableImgBean>()
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    override fun bindLayout(): ActivityMovableBinding {
        return ActivityMovableBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.activityget()
        val viewPager: ViewPager = view.viewpager
        val adapter = MovableImgAdapter (this, list2)
        viewPager.adapter = adapter
        viewPager.pageMargin = 20
        view.customConstraintLayout.singleClick {
            startActivity<ExperienceActivity>()
        }

        val timer = Timer()
        val autoScrollTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val currentItem = viewPager.currentItem
                    val totalItems = adapter.count
                    val nextItem = (currentItem + 1) % totalItems
                    viewPager.setCurrentItem(nextItem, true)
                }
            }
        }
        val delay = 2000L // 每隔2秒滚动一次
        val period = 2000L // 滚动任务之间的间隔时间
        timer.schedule(autoScrollTask, delay, period)

    }


    override fun subscribeUi() {

        viewModel.activitygetData.observe(this) { res ->
            res.onSuccess { r ->
                repeat(5){
                    list2.addAll(r!!.carousel!!.map { MovableImgBean(it!!.photo!!,it.scheme!!) })
                }
                view.adImg.load(r!!.posters,20)
                view.imageView7.load(r.photoUrl?.get(1))
                view.imageView.load(r.photoUrl?.get(0))
                view.customImageView.load(r.photoUrl?.get(2))
            }
            view.viewpager.adapter= MovableImgAdapter(this,list2)
        }
    }


}

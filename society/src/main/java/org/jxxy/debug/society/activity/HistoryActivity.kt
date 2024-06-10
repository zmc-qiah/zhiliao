package org.jxxy.debug.society.activity
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.util.SpaceItemDecoration
import org.jxxy.debug.society.R
import org.jxxy.debug.society.adapter.HistoryAtapter
import org.jxxy.debug.society.bean.HistoryBean
import org.jxxy.debug.society.databinding.ActivityHistoryBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {
    private var flag=0
    override fun bindLayout(): ActivityHistoryBinding {
       return ActivityHistoryBinding.inflate(layoutInflater)
    }
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    private lateinit var   adapter:HistoryAtapter

    override fun initView() {
         val list=ArrayList<HistoryBean>()
         val tv1:TextView = findViewById(R.id.tv1)
         val textTv:TextView = findViewById(R.id.textTv)
         val img:ImageView = findViewById(R.id.imageImg)
        viewModel.Historyget()

        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.HORIZONTAL
        )

        view.historyRv.layoutManager=layoutManager
        view.historyRv.addItemDecoration(SpaceItemDecoration(0,0))
        /*view.historyRv.adapter= HistoryAtapter(list, tv1,textTv,img)*/
        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = 1000 // 设置动画持续时间（单位：毫秒）
        fadeInAnimation.fillAfter = true
        view.textTv.startAnimation(fadeInAnimation)


    }



    override fun subscribeUi() {

        val list=ArrayList<HistoryBean>()
        val tv1:TextView = findViewById(R.id.tv1)
        val textTv:TextView = findViewById(R.id.textTv)
        val img:ImageView = findViewById(R.id.imageImg)

        viewModel.HistoryData .observe(this) { res ->
            res.onSuccess { r ->
                    list.addAll(r!!.aiHistory !!.map { HistoryBean(it!!.pointInTime!!,it.historyEvent!!,it.historyPhoto!! )})
                list.forEach {
                    println(it.imageImg)
                }
            }
            when(flag){
                0->{view.tv1.text=viewModel.HistoryData.data?.aiHistory?.get(0)?.historyEvent
                    view.imageImg.load(viewModel.HistoryData.data?.aiHistory?.get(0)?.historyPhoto)
                    flag=1}
            }
            adapter=HistoryAtapter(list,tv1,textTv,img)
            view.historyRv.adapter= adapter
        }

    }


}
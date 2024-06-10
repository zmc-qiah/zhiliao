package org.jxxy.debug.society.activity
import ScaleTransformer
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.adapter.PracticeAdapter
import org.jxxy.debug.society.bean.AiImgBean
import org.jxxy.debug.society.databinding.ActivityPracticeBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import org.jxxy.debug.society.util.AutoHeightViewPager


class PracticeActivity: BaseActivity<ActivityPracticeBinding>() {
private val list2 =ArrayList<AiImgBean>()
    val viewModel: SocialViewModel  by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    override fun bindLayout(): ActivityPracticeBinding {
       return ActivityPracticeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.AiImg()
        val viewPager: ViewPager  = view.viewpager
       /* initlist()*/
        val adapter = PracticeAdapter(this, list2)
        viewPager.adapter = adapter
        viewPager.pageMargin = 60
        viewPager.setOffscreenPageLimit(adapter.count)
        viewPager.setPageTransformer(false, ScaleTransformer())

    }

    override fun subscribeUi() {
        viewModel.AiImgData.observe(this) { res ->
            res.onSuccess { r ->
                repeat(4){
                    list2.addAll(r!!.imgUrl!!.map { AiImgBean(it!!) })

                    }

                }
                view.viewpager.adapter=PracticeAdapter(this,list2)
            }
        }
    }

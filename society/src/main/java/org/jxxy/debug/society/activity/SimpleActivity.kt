package org.jxxy.debug.society.activity
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.society.R
import org.jxxy.debug.society.databinding.ActivitySimpleBinding

class SimpleActivity: BaseActivity<ActivitySimpleBinding>() {
    override fun bindLayout(): ActivitySimpleBinding {
        return ActivitySimpleBinding.inflate(layoutInflater)
    }

    override fun initView() {

        view.button1.singleClick {
            startActivity<PracticeActivity>()
        }
        view.button2.singleClick {
            startActivity<DiscussActivity>()
        }
        view.button3.singleClick {
            startActivity<HistoryActivity>()
        }
        view.button4.singleClick {
            startActivity<LegalActivity>()
        }
        view.button5.singleClick {
            startActivity<RecommendActivity>()
        }
        view.button6.singleClick {
            startActivity<ExcellentClassroomActivity>()
        }
        view.button7.singleClick {
            startActivity<LectureActivity>()
        }
        view.button8.singleClick {
            startActivity<PlayActivity>()
        }
        view.button9.singleClick {
            startActivity<ExperienceActivity>()
        }
        view.button10.singleClick {
            startActivity<MovableActivity>()
        }
    }

    override fun subscribeUi() {

    }
}
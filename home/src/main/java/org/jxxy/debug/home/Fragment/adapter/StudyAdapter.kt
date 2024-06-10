package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Study
import org.jxxy.debug.home.Fragment.viewholder.*
import org.jxxy.debug.home.databinding.*

class StudyAdapter (private val lifecycle: Lifecycle, private val fragmentManager: FragmentManager,val scope: LifecycleCoroutineScope?=null) :
    MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType){
        Study.DAILY ->{
            DailyViewHolder(StudyComponentDailyBinding.inflate(inflater,parent,false),fragmentManager)
        }
        Study.TOOLS ->{
            ToolsViewHolder(StudyComponentToolsBinding.inflate(inflater,parent,false))
        }
        Study.COURSE ->{
            CourseViewHolder(ComponentNewsBinding.inflate(inflater,parent,false))
        }
        Study.BOOK ->{
            BookViewHolder(StudyComponentRecommendBookBinding.inflate(inflater,parent,false))
        }
        Study.INTERVIEW ->{
            InterviewViewHolder(StudyComponentInterviewBinding.inflate(inflater,parent,false),lifecycle)
        }
        Study.TECHNOLOGY ->{
            TechnologyViewHolder(StudyComponentHotTechnologyBinding.inflate(inflater,parent,false),scope)
        }
        Study.LEARN ->{
            LearnViewHolder(StudyComponentLearnBinding.inflate(inflater,parent,false))
        }
        Study.BREAKTHOUGH ->{
            BreakthoughViewHolder(StudyComponentBreakthoughBinding.inflate(inflater,parent,false))
        }
        Study.READ ->{
            ReadViewHolder(StudentComponentReadBinding.inflate(inflater,parent,false))
        }
        Study.TED ->{
            TedViewHolder(StudyComponentTedBinding.inflate(inflater,parent,false),scope)
        }
        Study.LEARN_2 ->{
            StudyViewHolder(StudyBinding.inflate(inflater,parent,false))
        }
        else -> {
            null
        }
    }

}
package org.jxxy.debug.home.Fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Component
import org.jxxy.debug.home.Fragment.http.response.Component.Companion.POPULARIZE
import org.jxxy.debug.home.Fragment.http.response.Practice
import org.jxxy.debug.home.Fragment.http.response.Study
import org.jxxy.debug.home.Fragment.http.response.Study.Companion.LEARN_2
import org.jxxy.debug.home.Fragment.viewholder.*
import org.jxxy.debug.home.databinding.*

class CommonHomeAdapter(private val lifecycle: Lifecycle, private val fragmentManager: FragmentManager,val scope: LifecycleCoroutineScope?=null):MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder?  =
        when (viewType){
            Practice.CAROUSEL ->{
                CarouseVH(ItemCarouselZmcBinding.inflate(inflater,parent,false),lifecycle)
            }
            Practice.NOTICE ->{
                NoticeViewHolder(PracticeComponentNoticeBinding.inflate(inflater,parent,false))
            }
            Practice.ACTIVITIES ->{
                ActivitiesViewHolder(PracticeComponentActivitiesBinding.inflate(inflater,parent,false))
            }
            Practice.ADVERTISE ->{
                AdvertiseViewHolder(PracticeComponentAdvertiseBinding.inflate(inflater,parent,false))
            }
            Practice.COMPETITION ->{
                CompetitionViewHolder(PracticeComponentCompetitionBinding.inflate(inflater,parent,false))
            }
            Practice.DISCOVERY ->{
                DiscoveryViewHolder(PracticeComponentDiscoveryBinding.inflate(inflater,parent,false))
            }
            Practice.RECORD ->{
                RecordViewHolder(PracticeComponentRecordBinding.inflate(inflater,parent,false))
            }
            Practice.SHARE ->{
                ShareViewHolder(PracticeComponentShareBinding.inflate(inflater,parent,false))
            }
            Practice.POSTURE ->{
                PostureViewHolder(ComponentNewsBinding.inflate(inflater,parent,false),lifecycle)
            }
            Practice.THEME ->{
                ThemeViewHolder(PracticeComponentThemeBinding.inflate(inflater,parent,false))
            }
            Practice.COME ->{
                ComeViewHolder(PracticeComponentComeBinding.inflate(inflater,parent,false))
            }
            Component.CAROUSEL -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---1")
                CarouseVH(ItemCarouselZmcBinding.inflate(inflater,parent,false),lifecycle)
            }
            Component.LIKE ->{
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---2")
                LikeViewHolder(ComponentLikeBinding.inflate(inflater,parent,false))
            }
            Component.HOTTOPIC -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---3")
                HotTopicViewHolder(ComponentHotTopicBinding.inflate(inflater,parent,false),lifecycle)
            }
            Component.VOTE -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---4")
                VoteViewHolder(ComponentVoteBinding.inflate(inflater,parent,false))
            }
            Component.History ->{
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---5")
                HistoryViewHolder(ComponentHistoryBinding.inflate(inflater,parent,false),scope)
            }
            Component.EXPERT ->{
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---6")
                ExpertViewHolder(ComponentExpertBinding.inflate(inflater,parent,false))
            }
            Component.NEWS -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---7")
                NewsViewHolder(ComponentNewsBinding.inflate(inflater,parent,false))
            }
            Component.LAST -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---8")
                LastViewHolder(ComponentLastViewBinding.inflate(inflater,parent,false))
            }
            Component.LAW -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---9")
                LawViewHolder(ComponentLawBinding.inflate(inflater,parent,false),fragmentManager)
            }
            Component.GRIDFOUR -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---10")
                GridFourViewHolder(ComponentNewsBinding.inflate(inflater,parent,false))
            }
            Component.FLASH -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---11")
                FlashViewHolder(ComponentFlashBinding.inflate(inflater,parent,false))
            }
            Component.GRID -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---12")
                GridViewHolder(ComponentGridBinding.inflate(inflater,parent,false))
            }
            Component.FALL -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---13")
                FallViewHolder(ItemFallZmcBinding.inflate(inflater,parent,false))
            }
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
            Practice.PRACTICE->{
                PracticeViewHolder(PracticeBinding.inflate(inflater,parent,false))
            }
            LEARN_2->{
                StudyViewHolder(StudyBinding.inflate(inflater,parent,false))
            }
            POPULARIZE->{
                PopularizeViewHolder(ComponentBinding.inflate(inflater,parent,false))
            }
            else -> {
                null
            }
        }
}
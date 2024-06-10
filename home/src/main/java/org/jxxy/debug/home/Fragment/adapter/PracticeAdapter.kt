package org.jxxy.debug.home.Fragment.adapter

import CarousellViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.ACTIVITIES
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.ADVERTISE
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.CAROUSEL_3
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.COME
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.COMPETITION
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.DISCOVERY

import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.NOTICE
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.POSTURE
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.PRACTICE
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.RECORD
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.SHARE
import org.jxxy.debug.home.Fragment.http.response.Practice.Companion.THEME
import org.jxxy.debug.home.Fragment.viewholder.*
import org.jxxy.debug.home.databinding.*

class PracticeAdapter (private val lifecycle: Lifecycle, private val fragmentManager: FragmentManager) :
    MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? =
        when (viewType){
        CAROUSEL_3 ->{
            CarousellViewHolder(ComponentCarouselBinding.inflate(inflater,parent,false),lifecycle)
        }
        NOTICE ->{
            NoticeViewHolder(PracticeComponentNoticeBinding.inflate(inflater,parent,false))
        }
        ACTIVITIES ->{
            ActivitiesViewHolder(PracticeComponentActivitiesBinding.inflate(inflater,parent,false))
        }
        ADVERTISE ->{
            AdvertiseViewHolder(PracticeComponentAdvertiseBinding.inflate(inflater,parent,false))
        }
        COMPETITION ->{
            CompetitionViewHolder(PracticeComponentCompetitionBinding.inflate(inflater,parent,false))
        }
        DISCOVERY ->{
            DiscoveryViewHolder(PracticeComponentDiscoveryBinding.inflate(inflater,parent,false))
        }
        RECORD ->{
            RecordViewHolder(PracticeComponentRecordBinding.inflate(inflater,parent,false))
        }
        SHARE ->{
            ShareViewHolder(PracticeComponentShareBinding.inflate(inflater,parent,false))
        }
        POSTURE ->{
            PostureViewHolder(ComponentNewsBinding.inflate(inflater,parent,false),lifecycle)
        }
        THEME ->{
            ThemeViewHolder(PracticeComponentThemeBinding.inflate(inflater,parent,false))
        }
        COME ->{
            ComeViewHolder(PracticeComponentComeBinding.inflate(inflater,parent,false))
        }
            PRACTICE->{
                PracticeViewHolder(PracticeBinding.inflate(inflater,parent,false))
            }
        else -> {
            null
        }
    }

}
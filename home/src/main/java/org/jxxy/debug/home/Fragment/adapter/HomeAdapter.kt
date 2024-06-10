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
import org.jxxy.debug.home.Fragment.http.viewmodel.CarouselViewHolder
import org.jxxy.debug.home.Fragment.viewholder.*
import org.jxxy.debug.home.databinding.*


class HomeAdapter(private val lifecycle: Lifecycle, private val fragmentManager: FragmentManager,val scope: LifecycleCoroutineScope?=null) :
    MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ):  RecyclerView.ViewHolder? = when (viewType){
        Component.CAROUSEL -> {
                val TAG = "confine"
                Log.d(TAG, "Homeadapter---1")
                CarouselViewHolder(ComponentCarouselBinding.inflate(inflater,parent,false),lifecycle)
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
//        Component.FALL -> {
//            val TAG = "confine"
//            Log.d(TAG, "Homeadapter---13")
//            FallViewHolder(ComponentFallBinding.inflate(inflater,parent,false))
//        }
        Component.POPULARIZE ->{
            PopularizeViewHolder(ComponentBinding.inflate(inflater,parent,false))
        }
            else -> {
                null
            }
        }
}

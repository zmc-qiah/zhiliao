package org.jxxy.debug.home.Fragment.viewholder

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.fragment.app.FragmentManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.dialog.DailyDialog
import org.jxxy.debug.home.Fragment.dialog.LawDialog
import org.jxxy.debug.home.Fragment.item.Daily
import org.jxxy.debug.home.Fragment.item.Expert
import org.jxxy.debug.home.databinding.ComponentExpertBinding
import org.jxxy.debug.home.databinding.StudyComponentDailyBinding

class DailyViewHolder(
    val binding: StudyComponentDailyBinding,
    private val fragmentManager: FragmentManager
) :
    MultipleViewHolder<Daily>(binding.root) {
    override fun setHolder(entity: Daily) {
        val dailyDialog = DailyDialog()
        binding.dailyKnowledgeContext.text = entity.knowledge
        binding.dailyKnowledgeContext.singleClick {
            dailyDialog.show(fragmentManager)
        }
        //TODO 二级首页路由跳转
        /* binding.con7.singleClick {
             val intent = Intent(binding.root.context, ExcellentClassroomActivity::class.java)
             binding.root.context.startActivity(intent)
         }*/
    }
}
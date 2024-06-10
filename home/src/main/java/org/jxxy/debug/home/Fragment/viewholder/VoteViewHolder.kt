package org.jxxy.debug.home.Fragment.viewholder

import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.*
import org.jxxy.debug.home.Fragment.item.HotTopic
import org.jxxy.debug.home.Fragment.item.Vote
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.ComponentHotTopicBinding
import org.jxxy.debug.home.databinding.ComponentVoteBinding
import java.math.BigInteger

class VoteViewHolder(val binding: ComponentVoteBinding) :
    MultipleViewHolder<Vote>(binding.root) {

    override fun setHolder(entity: Vote) {
        binding.voteQuestion.text = entity.voteInfos?.get(0)?.point
        binding.voteNum1.text = entity.voteInfos?.get(0)?.positiveSide.toString()
        binding.voteNum2.text = entity.voteInfos?.get(0)?.negativeSide.toString()
        entity.voteInfos?.get(0)?.positiveSide?.let {
            val weight1: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, it.toFloat())
            binding.voteLeft.layoutParams = weight1
        }
        entity.voteInfos?.get(0)?.negativeSide?.let {
            val weight2: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, it.toFloat())
            binding.voteRight.layoutParams = weight2
        }
        binding.voteNum1.hide()
        binding.voteNum2.hide()
        binding.voteYes.singleClick {
            val newYesNum = binding.voteNum1.text.toString().toInt() + 1
            binding.voteNum1.text = newYesNum.toString()
            val weight: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                newYesNum.toFloat()
            )
            binding.voteLeft.layoutParams = weight
            completeVoted()
        }

        binding.voteNo.singleClick {
            val newNoNum = binding.voteNum2.text.toString().toInt() + 1
            binding.voteNum2.text = newNoNum.toString()
            val weight: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                newNoNum.toFloat()
            )
            binding.voteRight.layoutParams = weight
            completeVoted()
        }
    }

    private fun completeVoted() {
        binding.voteYes.isClickable = false
        binding.voteNo.isClickable = false
        binding.voteIcon.gone()
        binding.voteYes.gone()
        binding.voteNo.hide()
        binding.voteRight.show()
        binding.voteLeft.show()

    }
}

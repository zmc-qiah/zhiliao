package org.jxxy.debug.test.fragment.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.FragmentDialogDoneAchievementBinding
import org.jxxy.debug.test.fragment.bean.Achievement

class DoneAchievementDialog(val achievement : Achievement) : BaseDialog<FragmentDialogDoneAchievementBinding>() {

    init {
        ifCancelOnTouch = true
        enableBack = true
        width = BaseApplication.context().resources.displayMetrics.widthPixels * 3 / 5
        height = BaseApplication.context().resources.displayMetrics.heightPixels / 2
    }

    override fun getView(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentDialogDoneAchievementBinding {
        return FragmentDialogDoneAchievementBinding.inflate(inflater,parent,false)
    }

    override fun initView(view: FragmentDialogDoneAchievementBinding) {
        view.achievementItemCheckTv.singleClick {
            dismiss()
        }
        view.achievementItemContentTv.text = achievement.describe
        Glide.with(view.achievementSmallIv).load(achievement.photoUrl).into(view.achievementSmallIv)
//        view.achievementSmallIv.load(achievement.photoUrl,4)
        view.achievementSmallTv.text = achievement.name
    }
}
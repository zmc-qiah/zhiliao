package org.jxxy.debug.test.fragment.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.DialogStatisticSmallBinding
import org.jxxy.debug.test.fragment.bean.Achievement
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class StatisticFragment(val achievement: Achievement) : BaseDialog<DialogStatisticSmallBinding>(){
    val viewModel : QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    lateinit var way : (Int) -> Unit
    init {
        ifCancelOnTouch = true
        enableBack = true
        width = BaseApplication.context().resources.displayMetrics.widthPixels * 3 / 5
        height = BaseApplication.context().resources.displayMetrics.heightPixels * 9 / 20
    }
    override fun getView(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): DialogStatisticSmallBinding {
        return DialogStatisticSmallBinding.inflate(inflater,parent,false)
    }

    override fun initView(view: DialogStatisticSmallBinding) {
        view.achievementItemCheckTv.singleClick {
            dismiss()
        }
        view.achievementItemChangeTv.singleClick {
            if(achievement.isDo == 0){
                Toast.makeText(context,"您还未获得此成就",Toast.LENGTH_SHORT).show()
                dismiss()
            }else{
                //切换主界面并且显示已佩戴
                way(achievement.id)
                Toast.makeText(context,"切换成功",Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
        Glide.with(view.achievementSmallIv).load(achievement.photoUrl).into(view.achievementSmallIv)
        view.achievementSmallTv.text = achievement.name
        view.achievementItemContentTv.text = "获取条件:" + achievement.describe
    }
}
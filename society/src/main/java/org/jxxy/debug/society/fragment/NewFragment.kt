package org.jxxy.debug.society.fragment

import org.jxxy.debug.society.adapter.DiscussAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.society.bean.Select
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.society.databinding.FragementSelectBinding

class NewFragment: BaseFragment<FragementSelectBinding>() {
    private  val list=ArrayList<Select>()
    override fun bindLayout(): FragementSelectBinding {
        return FragementSelectBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initlist()
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        find.recyclerRy.layoutManager=layoutManager
        find.recyclerRy.adapter= DiscussAdapter(list)
    }

    override fun subscribeUi() {

    }
    fun initlist (){
        list.add(
            org.jxxy.debug.society.bean.Select(
                "大一新生应该如何学习C语言,书上代码看不懂理解不了怎么办？",
                "讲真，大一新生，一般都是零基础的纯小白，看不懂书上的代码很正常，除非是小学、初中、高中就开始卷计算机的硬核少年；或者是因为教材选的有问题。\n" +
                        "\n" +
                        "那刚好二哥之前整理过一些学习 C语言的资料和学习方法，今天趁这个机会就再做个汇总和梳理。\n" +
                        "\n" +
                        "推荐一本书，两门视频课，若干学习建议，看完后如果还看不懂、理解不了C语言，过来骂我、捶我，只要不要打脸就行。",
                "#c语言#",
1
            )
        )
    }
}

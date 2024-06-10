package org.jxxy.debug.society.activity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.util.SpaceItemDecoration
import org.jxxy.debug.society.adapter.AnswerAdapter
import org.jxxy.debug.society.bean.AnswerBean
import org.jxxy.debug.society.bean.QuestionBean
import org.jxxy.debug.society.databinding.ActivityAnswerBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel

class DiscussAnswerActivity  : BaseActivity<ActivityAnswerBinding>() {
    override fun bindLayout(): ActivityAnswerBinding {
        return ActivityAnswerBinding.inflate(layoutInflater)
    }

    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    private val adapter= AnswerAdapter()

    override fun initView() {
        val bundle = intent.extras
        viewModel.getDetail(bundle!!.getInt("answerid"))
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        view.answerRv.layoutManager=layoutManager
       /* val adapter= AnswerAdapter()
        adapter.add(
            QuestionBean(
                1,
                R.drawable.book1,
                "朱元璋",
                bundle!!.getString("title")!!,
                bundle.getString("tag")!!,
                bundle.getString("text")!!
            )
        )

       repeat(3){
           adapter.add(AnswerBean(0, "从我们人类大脑的智能来看，可微优化过程 和 不可微优化过程 都很广泛[6].  人类大脑对不可微优化问题的求解策略是 复杂的、多样的、混合-集成式的，这种特征跟我们人类方便设计的算法的特征几乎完全相反。同样由于大脑应对不可微优化问题的这些求解策略，我们人类的行为表现出很多的非理性，但同时具有高效性。这一点上，我们所设计的算法还需要一些根本性的变革。"
               ,R.drawable.book1))
       }*/
        view.answerRv.addItemDecoration(SpaceItemDecoration(20,0))
        view.answerRv.adapter=adapter

    }

    override fun subscribeUi() {
        val list=ArrayList<QuestionBean>()
        val list2=ArrayList<AnswerBean>()
        viewModel.DiscussDetailData .observe(this) { res ->
            res.onSuccess { r ->
                list.addAll(listOf(QuestionBean(1,r!!.authorHead!!,r.authorName!!,r.questionTitle!!,r.questionLabel!!,r.questionContent!! )))
                list2.addAll(listOf(AnswerBean(0,r.answerContent!!,r.answerHead!!,r.answerName!!)))
            }

            adapter.add(list)
            adapter.add(list2)
            view.answerRv.adapter=adapter
        }
    }
}
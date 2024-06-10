package org.jxxy.debug.theme.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.WaveView
import org.jxxy.debug.theme.adapter.AiAdvGameAdapter
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.databinding.ActivityAiKnowledgeBinding
import org.jxxy.debug.theme.dialog.ChatGameDialog
import org.jxxy.debug.theme.http.viewModel.AiKnowledgeViewModel

class AiKnowledgeActivity : BaseActivity<ActivityAiKnowledgeBinding>() {
    val viewModel: AiKnowledgeViewModel by lazy {
        ViewModelProvider(this).get(AiKnowledgeViewModel::class.java)
    }
    var type = 1
    var nowString = ""
    val list = ArrayList<ChatContent>()
    lateinit var adapter: AiAdvGameAdapter
    lateinit var embeddings: List<Double>
    lateinit var searchId: String
    lateinit var ruleDialog: ChatGameDialog
    val title: String = "AI知识库"
    val rule: String =
        "介绍:AI知识库采用了向量数据库技术，向量数据库是一种用于存储和检索高维度向量数据的数据库系统。与传统的关系型数据库不同，向量数据库专注于处理具有大量特征的数据，如图像、音频、文本、时间序列等。这些特征通常表示为高维度向量，可高效解决用户对于长文章的难以一下子记住的问题"

    override fun bindLayout(): ActivityAiKnowledgeBinding {
        return ActivityAiKnowledgeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Glide.with(view.noneIv)
            .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/10/a795b4b1-a1e4-4484-a3be-23375c991f13.png")
            .into(view.noneIv)
        Glide.with(view.aiKnowledgeIv)
            .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/10/a795b4b1-a1e4-4484-a3be-23375c991f13.png")
            .into(view.aiKnowledgeIv)
        view.aiKnowledgeIv.setOnTouchListener { v, event ->
            false
        }
        view.aiKnowledgeToolbar.post {
            val positions = IntArray(2)
            view.aiKnowledgeToolbar.getLocationOnScreen(positions)
            BookMarkUtil.addView(this, positions[1] + view.aiKnowledgeToolbar.height)
            BookMarkUtil.get(4, true)
        }
        adapter = AiAdvGameAdapter()
        adapter.type = 0
        adapter.way = {
            goneNone()
        }
//        view.aiKnowledgeBackgroundIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/07/4c429904-0bc7-41d8-8d81-6fed7d1cc3ae.jpg")
        view.aiKnowledgeRv.adapter = adapter
        view.aiKnowledgeRv.layoutManager = LinearLayoutManager(this)
        view.aiKnowledgeRv.addItemDecoration(CommonItemDecoration(10f))
        view.testWaveView.post {
            view.testWaveView.percent = 60
            view.testWaveView.lifeDelegate = WaveView.RESUME
        }
        view.typeTurn.singleClick {
            if (type == 1) {
                type = 2
                view.aiKnowledgeEt.setText("")
                view.aiKnowledgeEt.setHint(R.string.ai_knowledge_type2)
                view.aiKnowledgeBtn.text = "提问"
                adapter.add(ChatContent(ChatContent.assistant, "切换至\"提问功能\""))
                view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
            } else {
                type = 1
                view.aiKnowledgeEt.setText("")
                view.aiKnowledgeEt.setHint(R.string.ai_knowledge_type1)
                view.aiKnowledgeBtn.text = "存入"
                adapter.add(ChatContent(ChatContent.assistant, "切换至\"存入功能\""))
                view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
            }
        }
        view.typeTurnTv.singleClick {
            if (type == 1) {
                type = 2
                view.aiKnowledgeEt.setText("")
                view.aiKnowledgeEt.setHint(R.string.ai_knowledge_type2)
                view.aiKnowledgeBtn.text = "提问"
                adapter.add(ChatContent(ChatContent.assistant, "切换至\"提问功能\""))
                view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
                view.chooseIcon.hide()
                view.chooseTv.hide()
            } else {
                type = 1
                view.aiKnowledgeEt.setText("")
                view.aiKnowledgeEt.setHint(R.string.ai_knowledge_type1)
                view.aiKnowledgeBtn.text = "存入"
                adapter.add(ChatContent(ChatContent.assistant, "切换至\"存入功能\""))
                view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
                view.chooseIcon.show()
                view.chooseTv.show()
            }
        }
        view.chooseIcon.singleClick {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("*/*") //无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivity(intent)
            startActivityForResult(intent, 1)
        }
        view.chooseTv.singleClick {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("*/*") //无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1)
        }
        view.aiKnowledgeRl.singleClick {
            if (view.aiKnowledgeEt.text.isNotEmpty()) {
                when (type) {
                    1 -> {
                        applyFadeInAnimation(view.aiKnowledgeAddTv, 2)
                        view.testWaveView.percent += 2
                        nowString = view.aiKnowledgeEt.text.toString()
                        view.aiKnowledgeEt.setText("")
                        adapter.add(ChatContent(ChatContent.assistant, "写入成功"))
//                        viewModel.getAiKnowledgeEmbeddings(nowString)
                        view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)

                    }
                    2 -> {
                        nowString = view.aiKnowledgeEt.text.toString()
                        view.aiKnowledgeEt.setText("")
                        viewModel.getAiKnowledgeEmbeddings(nowString)
                        adapter.add(ChatContent(ChatContent.user, nowString))
                        view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
                    }
                }
            } else {
                Toast.makeText(this, "您还未输入任何内容", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun subscribeUi() {
        viewModel.aiKnowlegdeEmbeddingsLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    Log.d("AiKnowLedge的数据", "${it}")
                    embeddings = it.embedding
//                    viewModel.writeEmbeddings(nowString, it.embedding)
                    viewModel.searchEmbeddings(
                        "213310-65ddf4f5-77f2-4d28-a0b5-20b300154b21",
                        embeddings
                    )
                }
            }
        }
        viewModel.writeEmbeddingsLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    Log.d("AiWrite的数据", "$it")
                    searchId = it.searchable_id
//                    viewModel.searchEmbeddings(searchId, embeddings)
                }
            }
        }
        viewModel.searchEmbeddingLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    val text = it.data.Get.Text[0].text.replace("\n","")
                    adapter.add(ChatContent(ChatContent.assistant,text))
                    view.aiKnowledgeRv.smoothScrollToPosition(adapter.itemCount - 1)
                    Log.d("AiSearch的数据", "$it")
                }
            }
        }
//        viewModel.getAiKnowledgeEmbeddings("测试测试")
        ruleDialog = ChatGameDialog(title, rule)
        ruleDialog.show(supportFragmentManager)
    }

    fun applyFadeInAnimation(addTextView: TextView, point: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            addTextView.text = "+$point"
            val fadeInAnimator = ObjectAnimator.ofFloat(addTextView, "alpha", 0f, 1f)
            fadeInAnimator.duration = 1000
            val fadeOutAnimator = ObjectAnimator.ofFloat(addTextView, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 1000
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(fadeInAnimator, fadeOutAnimator)
            animatorSet.start()
        }
    }

    override fun onDestroy() {
        BookMarkUtil.removeView()
        super.onDestroy()
    }

    fun goneNone() {
        view.noneIv.gone()
        view.noneTv.gone()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        adapter.add(ChatContent(ChatContent.assistant, "写入成功"))
        super.onActivityResult(requestCode, resultCode, data)
    }
}
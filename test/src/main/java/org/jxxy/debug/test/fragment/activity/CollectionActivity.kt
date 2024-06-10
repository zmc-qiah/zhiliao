package org.jxxy.debug.test.fragment.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityCollectionBinding
import org.jxxy.debug.test.fragment.adapter.AnswerRvAdapter
import org.jxxy.debug.test.fragment.adapter.CollectionRvAdapter
import org.jxxy.debug.test.fragment.bean.*
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class CollectionActivity : BaseActivity<ActivityCollectionBinding>() {
    val viewModel : QuestionViewModel by lazy{
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    val list : ArrayList<CollectionOrMistakeQuestion> by lazy{
        ArrayList()
    }

    lateinit var adapter : CollectionRvAdapter
    override fun bindLayout(): ActivityCollectionBinding {
        return ActivityCollectionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        adapter = CollectionRvAdapter(AnswerRvAdapter.COLLECTION,viewModel)
        view.collectionRv.adapter = adapter
        view.collectionRv.addItemDecoration(CommonItemDecoration(10f))
        view.collectionRv.layoutManager = LinearLayoutManager(this)

    }

    override fun subscribeUi() {
        viewModel.getCollectionLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    adapter.clearAndAdd(it.collections)
                }
            }
        }
        viewModel.getCollection()
    }

    fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.questionFl,fragment)
        transaction.commit()
    }

    fun init(){

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCollection()
    }
}
package org.jxxy.debug.search.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.common.widget.FlexLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.adapter.SeachMiddleAdapter2
import org.jxxy.debug.search.adapter.SearchHistoryAdapter
import org.jxxy.debug.search.adapter.SearchMiddleAdapter
import org.jxxy.debug.search.adapter.SearchRvAdapter
import org.jxxy.debug.search.bean.SearchHistory
import org.jxxy.debug.search.bean.SearchMiddleSubject
import org.jxxy.debug.search.bean.SearchMiddleTopic
import org.jxxy.debug.search.bean.SearchRv
import org.jxxy.debug.search.databinding.ActivitySearchMiddleBinding
import org.jxxy.debug.search.http.viewModel.SearchViewModel
import org.jxxy.debug.search.util.Search_SpaceItemDecoration

class SearchMiddleActivity:BaseActivity<ActivitySearchMiddleBinding>() {
    private val list1 =ArrayList<SearchMiddleSubject>()
    private val list2 =ArrayList<SearchMiddleTopic>()
    private val list3 =ArrayList<SearchHistory>()
    private val bundle = Bundle()
    val viewModel: SearchViewModel  by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }
    override fun bindLayout(): ActivitySearchMiddleBinding {
      return ActivitySearchMiddleBinding.inflate(layoutInflater)
    }
    override fun initView() {


        viewModel.Search()
        //搜索文本监听跳转，携带搜索框内的信息
        view.button.singleClick {
                if(view.editText.text.toString() != ""){
            bundle.putString("keyword",view.editText.text.toString())

            startActivity<SearchEndActivity>(bundle)

        }}
        //对应搜索界面的三个recyclerview
        val layoutManager1 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val layoutManager2 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        view.popular.layoutManager=layoutManager1
        view.popular.addItemDecoration(Search_SpaceItemDecoration(20,0))
        view.talk.layoutManager=layoutManager2
        view.talk.addItemDecoration(Search_SpaceItemDecoration(40,0))


        val layoutManager3 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        view.searchRv.layoutManager=layoutManager3
        val layoutManager =FlexLayoutManager()
        layoutManager.maxLines=2
        view.historyRv.layoutManager=layoutManager
        //搜索历史
        view.historyRv.addItemDecoration(Search_SpaceItemDecoration(30,40))

        //删除功能
        view.deleteIcon.singleClick {
            view.editText.text=null
        }
        view.editText.setOnEditorActionListener{
                v, actionId, event ->
            //EditorInfo.IME_NULL才能实现回车键跳转，为什么？
            //因为edittext内没有加上限制不能换行，回车键会被认为是换行操作而不是完成操作
            // v.text.toString() != ""
            if (actionId == EditorInfo.IME_ACTION_DONE && v.text.toString() != "") {
                // 执行跳转界面的操作
                Log.i("回车成功",v.text.toString())
                bundle.putString("keyword",view.editText.text.toString())
                startActivity<SearchEndActivity>(bundle)
                true
            } else {
                false
            }
        }
        view.deltehistoryIcon.singleClick {
            view.historyRv.visibility=View.GONE
        }



    }
    override fun onResume() {
        super.onResume()
        val searchEditText = view.editText
        searchEditText.setText("")
    }

    override fun subscribeUi() {
        //为了让搜索历史更新

        //搜索推荐内容更新
        viewModel.SearchData.observe(this) { res ->
            res.onSuccess { r ->
                repeat(4){
                    list2.addAll(r!!.hotTopicInfos!!.map { SearchMiddleTopic(it!!.resourceTitle!!,it.scheme!!) })
                list1.addAll(r.hotResourceInfos!!.resourceInfos!!.map {
                    SearchMiddleSubject(it!!.photo.toString(), it.title!!, it.author!!, "人工智能",
                        it.scheme!!
                    )
                })

                }
                val newItems = r?.historyInfos?.map { SearchHistory(it?.history!!) } ?: emptyList()
                for (item in newItems) {
                    val isDuplicate = list3.any { it.text == item.text }
                    if (!isDuplicate) {
                        list3.add(item)
                    }
                }

                view.popular.adapter=SearchMiddleAdapter(list1)
                view.talk.adapter=SeachMiddleAdapter2( list2)
                view.historyRv.adapter=SearchHistoryAdapter(list3)
            }
        }
        //搜索框内容更新
        viewModel.SearchcontentData.observe(this){ res ->
            // 第一个it是Resource对象
            res.onSuccess { content ->
                // 第二个是Resource里面的里面的response
                    if(content!!.associationalWord.isEmpty()){
                        view.searchRv.visibility=View.GONE
                    }
                else{
                        view.searchRv.visibility=View.VISIBLE
                    }
                    view.searchRv.adapter=SearchRvAdapter(content.associationalWord.map { SearchRv(it) })
            }
        }


        //监听搜索框的变化
        view.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 不需要实现
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // EditText文本变化时的回调
            }
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    viewModel.getSearch(view.editText.text.toString())
                    view.SearchCl.visibility=View.GONE
                    view.deleteIcon.visibility=View.VISIBLE
                    view.searchIcom.visibility=View.GONE
                    view.SearchBackground.visibility=View.VISIBLE

                } else {
                    view.deleteIcon.visibility=View.GONE
                    view.searchRv.visibility = View.GONE
                    view.SearchCl.visibility=View.VISIBLE
                    view.searchIcom.visibility=View.VISIBLE
                    view.SearchBackground.visibility=View.GONE
                }
            }
        })
    }
}

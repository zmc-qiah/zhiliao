package org.jxxy.debug.search.activity

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.search.adapter.SearchEndAdapter2
import org.jxxy.debug.search.adapter.SearchRvAdapter
import org.jxxy.debug.search.bean.SearchEndWiki
import org.jxxy.debug.search.bean.SearchEndBook
import org.jxxy.debug.search.bean.SearchEndProblem
import org.jxxy.debug.search.bean.SearchEndNotebook
import org.jxxy.debug.search.bean.SearchRv
import org.jxxy.debug.search.databinding.ActivitySearchEndBinding
import org.jxxy.debug.search.http.viewModel.SearchViewModel
import org.jxxy.debug.search.util.Search_SpaceItemDecoration

class SearchEndActivity : BaseActivity<ActivitySearchEndBinding>() {
    val adapter = SearchEndAdapter2(supportFragmentManager)

    val list1=ArrayList<SearchEndBook>()
    var key : String= ""
    var a:Int = 2
    val list3=ArrayList<SearchEndNotebook>()
    val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }
    override fun bindLayout(): ActivitySearchEndBinding {
        return ActivitySearchEndBinding.inflate(layoutInflater)
    }

    override fun initView() {
        //搜索页编辑框携带的信息(完成)
        val bundle = intent.extras
        bundle?.getString("keyword")?.let { Log.i("keyword", it) }
        key = bundle!!.getString("keyword")!!
        viewModel.Searchget(key)
        view.editText.hint=bundle.getString("keyword")!!
        view.aSrl.setRefreshFooter(ClassicsFooter(this))
        view.aSrl.setEnableLoadMore(true)
        view.aSrl.setEnableRefresh(false)
        view.aSrl.setOnLoadMoreListener {
            viewModel.f(key,a++)
        }
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        val layoutManager3 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        view.searchRv.layoutManager=layoutManager3

        view.recyclerSearch.layoutManager = layoutManager
        view.recyclerSearch.addItemDecoration(Search_SpaceItemDecoration(20,0))
        view.recyclerSearch.adapter = adapter

        //删除功能
        view.deleteIcon.singleClick {
            view.editText.text=null
        }
        //回车跳转
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
        //点击跳转
        view.button.singleClick {
            if(view.editText.text.toString() != ""){
                bundle.putString("keyword",view.editText.text.toString())

                startActivity<SearchEndActivity>(bundle)

            }}
    }
    override fun onResume() {
        super.onResume()
        val searchEditText = view.editText
        searchEditText.setText("")
    }
    override fun subscribeUi() {
        //搜索结果页更新
        viewModel.SearchgetData.observe(this) { res ->
            res.onSuccess { r ->
                if (r?.wikiInfo?.title.isNullOrEmpty()){
                    Log.i("233","nowiki")
                }
                else{
                    adapter.add(SearchEndWiki(0,r!!.wikiInfo!!.title!!,r.wikiInfo!!.shortText!!))
                }
                if (r?.gridInfos?.get(0)?.describe.isNullOrEmpty() == false){

                    list1.addAll(r!!.gridInfos!!.map{
                        SearchEndBook(1,it!!.describe!!,it.photo!!,it.title!!,it.scheme!!)
                    })
                    list1.forEach { bean ->
                        adapter.add(bean)
                    }
                }
                else{
                    view.notfoundTv.visibility = View.VISIBLE
                }
                if (r?.questionInfo?.title.isNullOrEmpty()){
                    Log.i("233","noquestion")
                }
                else{
                    adapter.add(SearchEndProblem(2,r!!.questionInfo!!.title!!,r.questionInfo!!.shortText!!))
                }
                if (r?.noteInfos.isNullOrEmpty()){
                    Log.i("233","nonote")
                }
                else{
                    list3.addAll(r!!.noteInfos!!.map{
                        SearchEndNotebook(3,it!!.photoUrl!!,it.photoUrl!!,it.photoUrl!!,it.photoUrl!!,it.photoUrl!!,)
                    })
                    list3.forEach { bean ->
                        adapter.add(bean)
                    }
                }
                view.recyclerSearch.adapter = adapter
                if(r?.wikiInfo?.title.isNullOrEmpty() == false &&r?.gridInfos?.get(0)?.describe.isNullOrEmpty() == false&&r?.questionInfo?.title.isNullOrEmpty() == false&&r?.noteInfos.isNullOrEmpty()== false){
                    view.notfoundTv.visibility = View.VISIBLE
                }
            }
            res.onError { error, response ->
                view.notfoundTv.visibility = View.VISIBLE
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
                view.searchRv.adapter= SearchRvAdapter(content.associationalWord.map { SearchRv(it) })
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
                    view.deleteIcon.visibility=View.VISIBLE
                    view.searchIcon.visibility=View.GONE
                    Log.i("22300","可见")
                } else {
                    Log.i("22300","不可见")
                    view.searchRv.visibility = View.GONE
                    view.deleteIcon.visibility=View.GONE
                    view.searchIcon.visibility=View.VISIBLE
                }
            }
        })
        viewModel.f.observe(this){
            it.onSuccess {
                it?.gridInfos?.let {
                    val list = it.map {
                        it?.let {
                            SearchEndBook(1,it.describe?:"",it.photo?:"",it.title?:"",it.scheme!!)
                        }
                    }.filterNotNull()
                    adapter.add(
                        list
                    )
                    if (list.size == 0){
                        "没有更多了".toast()
                    }
                    view.aSrl.finishLoadMore()

                }
//                if (r?.gridInfos?.get(0)?.describe.isNullOrEmpty() == false){
//                    list1.addAll(r!!.gridInfos!!.map{
//                        SearchEndBook(1,it!!.describe!!,it.photo!!,it.title!!,it.scheme!!)
//
//                    list1.forEach { bean ->
//                        adapter.add(bean)
//                    }
//                    view.aSrl.finishLoadMore()
//                }else{
//                    "没有更多了".toast()
//                }
            }
        }
    }
}
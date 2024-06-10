package org.jxxy.debug.resources.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.common.widget.read.IsLastItem
import org.jxxy.debug.common.widget.read.view.BookLayoutManager
import org.jxxy.debug.common.widget.read.view.BookView
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.databinding.ActivityComicBinding
import org.jxxy.debug.resources.databinding.ItemComicBinding
import org.jxxy.debug.resources.fragment.CommentBottomFragment
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel

class ResourceComicActivity: BaseActivity<ActivityComicBinding>() {
   private val adapter = object :SingleTypeAdapter<String>(){
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = object :SingleViewHolder<ItemComicBinding,String>(
            ItemComicBinding.inflate(inflater,parent,false)){
            override fun setHolder(entity: String) {
                view.comicImageView.loads(entity, getWidth())
            }
        }
    }
    private val pageLiveData:MutableLiveData<String> by lazy {MutableLiveData<String>()}
    private val viewModel:ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    private var myResourceId: Int? = null
    override fun bindLayout(): ActivityComicBinding= ActivityComicBinding.inflate(layoutInflater)

    override fun initView() {
        view.pageText.text = "0/0"
        view.BookView.setAdapter(adapter)
        view.BookView.setFlipMode(BookLayoutManager.BookFlipMode.MODE_CURL)
        view.BookView.setOnPositionChangedListener(object : BookView.OnPositionChangedListener {
            override fun onChanged(arriveNext: Boolean, curPosition: Int) {
                pageLiveData.postValue(curPosition.toString())
                showPage()
            }
        })
        view.markText.text = "收藏"
        view.commentTextView.text = "评论"
        view.shareText.text = "设置"
        view.BookView.setIsLastItem(object : IsLastItem {
            override fun isLastItem(): Boolean{
                return adapter.itemCount-1==view.BookView.bookRecyclerView.currentPosition
            }
            override fun ifLastItem() {
                myResourceId?.let {
                    val bottomFragment = CommentBottomFragment(it)
                    bottomFragment.show(supportFragmentManager,bottomFragment.tag)
                }
            }
        })
    }
    override fun subscribeUi() {
        viewModel.resourceLiveData.observe(this) {
            it.onSuccess {
                loadView(it)
            }
        }
        pageLiveData.observe(this){
           val split = view.pageText.text.split("/")
            view.pageText.text = it+"/"+split[1]
        }
        if (intent != null)  {
            val extra = intent.getSerializableExtra("resource_info")
            extra.nullOrNot({
                myResourceId = intent.getIntExtra("resourceId", 0)
                if (myResourceId != 0) {
                    viewModel.getResourceById(myResourceId!!)
                } else {
                    "资源不存在".toast()
                    finish()
                }
            }, {
                it as ResourceInfoResponse
                myResourceId = it.resourceInfo!!.resourceId
                loadView(it)
            })
        } else {
            "资源不存在".toast()
            finish()
        }
        showPage()
    }

    private fun loadView(res: ResourceInfoResponse?) {
        res?.let {
            pageLiveData.postValue(if (it.photosUrl?.size?:0>0)"1" else "0")
            val split = view.pageText.text.split("/")
            view.pageText.text = "${split[0]}/${it.photosUrl?.size?:0}"
            adapter.add(it.photosUrl)
            it.resourceInfo?.let {it1->
                val title = it1.resourceTitle!!
                view.tileCommonToolbar.setTitleText(if(title.length<8) title else title.substring(0,8))
                view.authorImageView.load(it1.resourceAuthorHead,true)
                view.authorTextView.text = it1.resourceAuthorName
                if (it.myState?.collectState == 1){
                    view.markIcon.tag = true
                    view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                }else{
                    view.markIcon.tag = false
                    view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.dark_grey))
                }
                view.markIcon.singleClick {
                    if(it.tag as Boolean){
                        myResourceId?.let { it2 -> viewModel.cancelCollection(it2) }
                        view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.dark_grey))
                        view.markIcon.tag = false
                    }else{
                        myResourceId?.let { it2 -> viewModel.addMark(it2) }
                        view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                        view.markIcon.tag = true
                    }
                }
                view.settingIocn.singleClick {
                    "该功能未开放".toast()
                }
                view.commentIcon.singleClick {
                    myResourceId?.let {
                        val bottomFragment = CommentBottomFragment(it)
                        bottomFragment.show(supportFragmentManager,bottomFragment.tag)
                    }
                }
            }
        }
    }
    private fun showPage(){
        view.pageText.show()
        lifecycleScope.launch(Dispatchers.IO){
            delay(3000)
            launch(Dispatchers.Main)
            {
                view.pageText.hide()
            }
        }
    }
}
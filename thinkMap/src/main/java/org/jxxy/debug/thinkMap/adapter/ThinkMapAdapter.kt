package org.jxxy.debug.thinkMap.adapter

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import navigation
import org.jxxy.debug.thinkMap.MyListener.ClickListener
import org.jxxy.debug.thinkMap.MyListener.TreeNodeClickListener
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.common.databinding.ItemResourcesVideoBinding
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.thinkMap.MyType
import org.jxxy.debug.thinkMap.R
import org.jxxy.debug.thinkMap.activity.ThinkMapActivity
import org.jxxy.debug.thinkMap.activity.ThinkMapActivity.Companion.nodeNotSelectB
import org.jxxy.debug.thinkMap.bean.MofidyBean
import org.jxxy.debug.thinkMap.bean.UserInfo
import org.jxxy.debug.thinkMap.databinding.ItemThinkMapNodeBinding
import org.jxxy.debug.thinkMap.treeview.BaseTreeViewHolder
import org.jxxy.debug.thinkMap.treeview.adapter.DrawInfo
import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewAdapter
import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewHolder
import org.jxxy.debug.thinkMap.treeview.line.BaseLine
import org.jxxy.debug.thinkMap.treeview.line.DashLine
import org.jxxy.debug.thinkMap.treeview.model.NodeModel

class ThinkMapAdapter(val context: Context) : TreeViewAdapter<Node>() {
    private val dashLine = DashLine(Color.parseColor("#F06292"), 6)
    var clickListener: TreeNodeClickListener<Node>? = null
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        model: NodeModel<Node>?
    ): TreeViewHolder<Node> = when(model?.value?.type?:0){
        MyType.TEXT ->{
            TextViewHolder(
                context,
                ItemResourceTextBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
                model!!
                )
        }
        MyType.ViDEO ->{
            VideoViewHolder(
                context,
                ItemResourcesVideoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
                model!!
            )
        }
        else ->{
            NodeViewHolder(
                ItemThinkMapNodeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
                model!!,
                clickListener
            )
        }
    }
    override fun onBindViewHolder(holder: TreeViewHolder<Node>) {
        val entry = holder.node.value
        when(entry.type){
            MyType.TEXT ->{
                holder as TextViewHolder
                holder.setHolder(entry)
            }
            MyType.ViDEO ->{
                holder as VideoViewHolder
                holder.setHolder(entry)
            }
            else ->{
                holder as NodeViewHolder
                holder.setHolder(entry)
            }
        }
    }

    override fun onDrawLine(drawInfo: DrawInfo?): BaseLine? = null

    class NodeViewHolder(binding: ItemThinkMapNodeBinding, node: NodeModel<Node>,val  clickListener:TreeNodeClickListener<Node>? = null) : BaseTreeViewHolder<Node, ItemThinkMapNodeBinding>(binding, node) {
        companion object{
            const val TAG = "@!#$%^&*()*"
        }
        override fun setHolder(entry: Node) {
            binding.tileET.setText(entry.title)
            binding.tileET.setOnTouchListener { v, event ->
                when(event.action){
                    MotionEvent.ACTION_DOWN ->{
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP ->{
                        v.parent.requestDisallowInterceptTouchEvent(false)

                    }
                }
                false
            }
            binding.tileET.addTextChangedListener(object : TextWatcher {
                var oledText:String = ""
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    oledText = s.toString()
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 在文本变化时执行的操作
                    val data = s.toString()
                    if (!oledText.equals(data)){
                        clickListener?.titleChange(
                            MofidyBean(
                                "1","1","1",1,
                                node.value.id?:0,
                                MofidyBean.ACTION_Title_CHANGE,
                                data,
                                2
                            )
                        )
                    }
                    entry.title = data
                }
                override fun afterTextChanged(s: Editable?) {
                    // 在文本变化之后执行的操作
                }
            })
            binding.thinkMapTexTView.text = entry.text
            binding.pictureRecyclerView.adapter = ThinkMapPicturesAdapter(0).apply {
                if (entry.pictureList?.size?:0>0){
                    add(entry.pictureList)
                    binding.pictureRecyclerView.show()
                }
            }
            binding.root.setOnTouchListener(
                ClickListener(
                    {
                        clickListener?.select(node)
                    },
                    {
                        clickListener?.select(node)
                        clickListener?.clickDouble(node)
                    },{
                        clickListener?.longClick(node)
                    }
                )
            )
            binding.pictureRecyclerView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.pictureRecyclerView.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        binding.pictureRecyclerView.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                false
            }
        }
        override fun update(entry: MofidyBean) {
            when(entry.action){
                MofidyBean.ACTION_CONTENT_ADD ->{
                    binding.thinkMapTexTView.append(entry.data)
                }
                MofidyBean.ACTION_PICTURE_ADD ->{
                    (binding.pictureRecyclerView.adapter as ThinkMapPicturesAdapter).add(entry.data)
                }
                MofidyBean.ACTION_Title_ADD ->{
                    binding.tileET.append(entry.data)
                }
                MofidyBean.ACTION_CONTENT_CHANGE ->{
                    node.value.text = entry.data
                    binding.thinkMapTexTView.text = entry.data
                }
                MofidyBean.ACTION_PICTURE_CHANGE ->{
                    val typeToken = object :TypeToken<List<String>>(){}.type
                    val list :List<String> = GsonManager.instance.gson.fromJson(entry.data,typeToken)
                    if (node.value.pictureList==null)node.value.pictureList = ArrayList<String>()
                    node.value.pictureList!!.addAll(list)
                    binding.pictureRecyclerView.show()
                    (binding.pictureRecyclerView.adapter as ThinkMapPicturesAdapter).clearAndAdd(list)
                }
                MofidyBean.ACTION_Title_CHANGE ->{
                    binding.tileET.setText(entry.data)
                    val position = entry.position
                    binding.root.background = ThinkMapActivity.seletctColorList[position% ThinkMapActivity.seletctColorList.size]
                }
                MofidyBean.ACTION_ADD_NODE ->{
                    clickListener?.addNode(entry)
                }
                MofidyBean.ACTION_DELETE_NODE ->{
                    clickListener?.deleteNode(entry)
                }
                MofidyBean.ACTION_SELECT->{
                    val position = entry.position
                    binding.root.background = ThinkMapActivity.seletctColorList[position% ThinkMapActivity.seletctColorList.size]
                }
                MofidyBean.ACTION_SELECT_NOT ->{
                    binding.root.background = ResourceUtil.getDrawable(R.drawable.node_not_selected)
                }
            }
        }
        fun cancelSelect(){
            binding.root.background = nodeNotSelectB
        }
    }
    class TextViewHolder(val context: Context,binding: ItemResourceTextBinding, node: NodeModel<Node>,val clickListener:TreeNodeClickListener<Node>? = null) : BaseTreeViewHolder<Node, ItemResourceTextBinding>(binding, node) {
        override fun setHolder(entry: Node) {
            val entity = entry.resource
            entity?.let {
                binding.root.background = ResourceUtil.getDrawable(R.drawable.node_not_selected)
                binding.authorNameTV.text = entity.resourceAuthorName
                binding.tileTV.text = entity.resourceTitle
                binding.likeTV.text = entity.resourceLikes.toString()
                binding.readTV.text = entity.resourceReads.toString()
                entity.createTime?.let {
                    binding.createDateTV.text = it.split(" ")[0]
                }
                binding.auImageView.loads(entity.resourceAuthorHead, true,context)
                binding.thumbnailIV.loads(entity.resourcePhoto,false,context)
                binding.likeIcon.tag =false
                binding.likeIcon.singleClick {
                    if ( it.tag as Boolean){
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                entity.resourceId?.let { it1 -> ResourceRepository.cancelLike(it1.toInt()) }
                            }?.let {
                                withContext(Dispatchers.Main){
                                    if (it.code==0){
                                        binding.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                                        binding.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_300))
                                        binding.likeTV.text = it.data.toString()
                                        binding.likeIcon.tag = false
                                    }
                                }
                            }
                        }
                    }
                    else {
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                entity.resourceId?.let { it1 -> ResourceRepository.addLike(it1.toInt()) }
                            }?.let {
                                withContext(Dispatchers.Main){
                                    if (it.code==0){
                                        binding.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                                        binding.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                                        binding.likeTV.text = it.data.toString()
                                        binding.likeIcon.tag = false
                                    }
                                }
                            }
                        }
                    }
                }
                binding.root.setOnTouchListener(ClickListener(
                    {
                        entity.scheme?.navigation(context)
                    },
                    {
                        clickListener?.select(node)
                    },
                    {
                        clickListener?.select(node)
                    }
                ))
            }
        }
    }
    class VideoViewHolder(val context: Context,binding: ItemResourcesVideoBinding, node: NodeModel<Node>,val clickListener:TreeNodeClickListener<Node>? = null) : BaseTreeViewHolder<Node, ItemResourcesVideoBinding>(binding, node) {
        override fun setHolder(entry: Node) {
            val entity = entry.resource
            entity?.let {
                binding.root.background = ResourceUtil.getDrawable(R.drawable.node_not_selected)
                binding.videoPhotoIV.loads(entity.resourcePhoto?:"https://i0.hdslb.com/bfs/archive/0e291149963b333021360535295f1e51db41c425.jpg@672w_378h_1c_!web-search-common-cover.webp",false,context)
                binding.authorImageView.loads(entity.resourceAuthorHead, true,context)
                binding.authorNameTV.text = entity.resourceAuthorName
                binding.videoTileTV.text = entity.resourceTitle
                binding.likeTV.text = entity.resourceLikes.toString()
                binding.readTV.text = entity.resourceReads.toString()
                entity.createTime?.let {
                    binding.createDateTV.text = it.split(" ")[0]
                }
                binding.root.setOnTouchListener(ClickListener(
                    {
                        entity.scheme?.navigation(context)
                    },
                    {
                        clickListener?.select(node)
                    },
                    {
                        clickListener?.select(node)
                    }
                ))
                binding.likeIcon.tag = false
                binding.likeIcon.singleClick {
                    if ( it.tag as Boolean){
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                entity.resourceId?.let { it1 -> ResourceRepository.cancelLike(it1.toInt()) }
                            }?.let {
                                withContext(Dispatchers.Main){
                                    if (it.code==0){
                                        binding.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                                        binding.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_300))
                                        binding.likeTV.text = it.data.toString()
                                    }
                                    binding.likeIcon.tag = false
                                }
                            }
                        }
                    }
                    else {
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                entity.resourceId?.let { it1 -> ResourceRepository.addLike(it1.toInt()) }
                            }?.let {
                                withContext(Dispatchers.Main){
                                    if (it.code==0){
                                        binding.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                                        binding.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                                        binding.likeTV.text = it.data.toString()
                                    }
                                    binding.likeIcon.tag = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

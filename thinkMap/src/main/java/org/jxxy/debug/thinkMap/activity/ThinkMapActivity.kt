package org.jxxy.debug.thinkMap.activity

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.think.bean.FirstMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.http.BaseClientThread
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.common.util.getJsonContent
import org.jxxy.debug.common.util.load
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.*
import org.jxxy.debug.thinkMap.MyListener.DragListener
import org.jxxy.debug.thinkMap.MyListener.ModifyDialogListener
import org.jxxy.debug.thinkMap.MyListener.MyTreeViewListener
import org.jxxy.debug.thinkMap.MyListener.PreviewListener
import org.jxxy.debug.thinkMap.MyListener.TreeNodeClickListener
import org.jxxy.debug.thinkMap.R
import org.jxxy.debug.thinkMap.adapter.ThinkMapAdapter
import org.jxxy.debug.thinkMap.adapter.ThinkMapPicturesAdapter
import org.jxxy.debug.thinkMap.adapter.UserAdapter
import org.jxxy.debug.thinkMap.bean.MofidyBean
import org.jxxy.debug.thinkMap.bean.ThinkMapRespone
import org.jxxy.debug.thinkMap.bean.UserInfo
import org.jxxy.debug.thinkMap.databinding.ActivityThinkMapBinding
import org.jxxy.debug.thinkMap.databinding.ItemThinkMapNodeBinding
import org.jxxy.debug.thinkMap.fragmemt.ModifyDialog
import org.jxxy.debug.thinkMap.fragmemt.QuoteDialog
import org.jxxy.debug.thinkMap.getTreeViewHolder
import org.jxxy.debug.thinkMap.http.ViewModel
import org.jxxy.debug.thinkMap.treeview.BaseTreeViewHolder
import org.jxxy.debug.thinkMap.treeview.layout.BoxRightTreeLayoutManager
import org.jxxy.debug.thinkMap.treeview.layout.TreeLayoutManager
import org.jxxy.debug.thinkMap.treeview.line.BaseLine
import org.jxxy.debug.thinkMap.treeview.line.SmoothLine
import org.jxxy.debug.thinkMap.treeview.listener.TreeViewControlListener
import org.jxxy.debug.thinkMap.treeview.model.NodeModel
import org.jxxy.debug.thinkMap.treeview.model.TreeModel


// 一坨，不要看
class ThinkMapActivity : BaseActivity<ActivityThinkMapBinding>() {
    private val TAG = "ThinkMapActivity"
    private val nodeMap = HashMap<Int,NodeModel<Node>>()
    private var root: NodeModel<Node>? = null
    private var selectedNote: NodeModel<Node>? = null
    private lateinit var tree:TreeModel<Node>
    private lateinit var userInfo: UserInfo
    private var selectHolder: BaseTreeViewHolder<Node, ItemThinkMapNodeBinding> ? = null
    private var lastNote: NodeModel<Node>? = null
    private var lastHolder: BaseTreeViewHolder<Node, ItemThinkMapNodeBinding> ? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isShared = 0
    private val viewModel by lazy { ViewModelProvider(this).get(ViewModel::class.java)}
    private var nodeId = 0
    private var thinkMapId = 0
    private var isFirst = true
    private val gson:Gson by lazy { GsonManager.instance.gson}
    private val dataType = object : TypeToken<BaseResp<String>>() {}.type
    private val userName :String
        get() {
            return PersistenceUtil.getValue<String>("userName").toString()
        }
    private lateinit var mHandler: Handler
    private lateinit var client: BaseClientThread
    override fun bindLayout(): ActivityThinkMapBinding = ActivityThinkMapBinding.inflate(layoutInflater)
    override fun initView() {
        loadTree()
        val root = intent.getSerializableExtra("root") as Node?
        root.nullOrNot(
            {
                val id = intent.getIntExtra("rootId",0)
                Log.d(TAG, "initView: $id")
                if (id != 0){
                    Log.d(TAG, "initView: $id")
                    thinkMapId = id
                    viewModel.getById(id)
                }else{
                    setRoot(Node("记录灵光瞬间"))
                }
            },
            {
                setRoot(it)
            }
        )
        loadButton()
    }
    private fun loadLink() {
        if (isShared == 0){
            return
        }else {
            mHandler = object : Handler(Looper.getMainLooper()) {
                var flag = true
                override fun handleMessage(msg: Message) {
                    var content: String = msg.obj as String
                    Log.d("BaseClientThread", "主线程接收到Socket线程发送的${msg.obj}")
                    lifecycleScope.launch {
                        if (content.startsWith("[@[*")){
                            view.userRV.adapter?.let {
                                it as UserAdapter
                                content = content.substring(4,content.length)
                                it.remove(content)
                            }
                        }else if (content.startsWith("{\"mapId\":")){
                            withContext(Dispatchers.Default){
                                gson.fromJson(content,UserInfo::class.java)
                            }?.let {userInfo ->
                                view.userRV.adapter?.let {
                                    it as UserAdapter
                                    it.add(userInfo)
                                }
                            }
                        }else{
                            withContext(Dispatchers.Default){
                                content = content.substring(1,content.length-1)
                                content = content.replace("\\\"","\"")
                                content = content.replace("\\\\\"","\\\"")
                                gson.fromJson(
                                    content,MofidyBean::class.java
                                )
                            }?.let{
                                Log.d("exception", "handleMessage: " + it)
                                launch(Dispatchers.Main){
                                    if (!userInfo.userName.equals(it.userName)) {
                                        if (it.id != MofidyBean.ID_ACTIVITY){
                                            val p = userAdapter.getPosition(it.userToken)
                                            if (p!=-1){
                                                val i = it.data.toIntOrNull()
                                                i?.let {id->
                                                    nodeMap[id]?.let {node ->
                                                        val preHolder = adapter.getTreeViewHolder(node)
                                                        if (preHolder is ThinkMapAdapter.NodeViewHolder) {
                                                            preHolder.cancelSelect()
                                                        }
                                                    }
                                                }
                                                it.position = p
                                                val holder = adapter.getTreeViewHolder(nodeMap.get(it.id)!!)

                                                if (holder is ThinkMapAdapter.NodeViewHolder) {
                                                    holder.update(it)
                                                }
                                            }
                                        }else{
                                            when(it.action){
                                                MofidyBean.ACTION_DRAW_NODE->{
                                                    val list = it.data.split(" ")
                                                    val p = list[0].toInt()
                                                    val c = list[1].toInt()
                                                    val np = list[2].toInt()
                                                    val pn= nodeMap.get(p)
                                                    val cn= nodeMap.get(c)
                                                    val npn= nodeMap.get(np)
                                                    if (pn!=null&&cn!=null&&npn!=null){
                                                        val n = pn.value
                                                        val c = cn.value
                                                        val np = npn.value
                                                        Log.d(TAG, "handleMessage: $n")
                                                        Log.d(TAG, "handleMessage: $c")
                                                        Log.d(TAG, "handleMessage: $np")
                                                        n.childNode!!.remove(c)
                                                        if (np.childNode == null)np.childNode = ArrayList()
                                                        np.childNode!!.add(c)
                                                        val edit =view.thinkMapTreeView.editor
                                                        tree.removeNode(pn,cn)
                                                        edit.removeNode(cn)
                                                        Log.d(TAG, "handleMessage: qwwwwwww")
                                                        Log.d(TAG, "handleMessage: qwwwwwww")
                                                        Log.d(TAG, "handleMessage: qwwwwwww${cn.parentNode}")
//                                                                    npn.addChildNodes(mutableListOf(cn))
                                                        edit.addChildNodes(npn,cn)
                                                        Log.d(TAG, "handleMessage: qwwwwwww")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }


                }
            }
            viewModel.useLiveData.observe(this){
                it.onSuccess {
                    it?.let {
                        userInfo = UserInfo(
                            userName = it.userName,
                            headPicture =  it.headPhoto,
                            token = token,
                            mapId = thinkMapId
                        )
                        viewModel.isJoinLiveData.observe(this){
                            it.onSuccess {
                                it?.let { it1 -> loadUser(it1) }
                                client= BaseClientThread(mHandler,ip = "47.99.43.189", port = 4000)
                                client.firstMessage = gson.toJson(FirstMessage(userToken = TokenManager.getToken()!!,mapId =  thinkMapId)).replace("\n", "").replace("\r", "")
                                Thread(client).start()
                            }
                            it.onError { error, response ->  }
                        }
                        viewModel.join(
                            userInfo
                        )
                    }
                }
            }
            viewModel.getUser()

        }
    }
    val token :String by lazy{ TokenManager.getToken()?:"" }

    override fun subscribeUi() {
        viewModel.thinkMapLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    val temp = isShared
                    isShared = it.isShared
                    if (temp == 0 && isShared == 1){
                        loadLink()
                    }
                    if (it.isShared == 0)        view.lastButton.load(ResourceUtil.getString(R.string.url_lock))
                    else         view.lastButton.load(ResourceUtil.getString(R.string.url_unlock))
                    val json = getJsonContent(it.jsonText)
                    if (thinkMapId == 0) "保存成功"
                    thinkMapId = it.id
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            GsonManager.instance.gson.fromJson(json, Node::class.java)
                        }.nullOrNot(
                            {
                                setRoot(Node("记录灵光瞬间"))
                            },
                            {
                                if (it.title?.length?:0 == 0){
                                    setRoot(Node("记录灵光瞬间"))
                                }else{
                                    setRoot(it)
                                }
                            }
                        )
                    }
                }
            }
        }

        viewModel.resourceLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    val node = Node()
                    node.type = it.resourceType
                    node.resource = it
                    node.id = nodeId++
                    val edit = view.thinkMapTreeView.editor
                    val value = NodeModel((node))
                    selectedNote?.value?.let {
                        if (it.childNode == null) it.childNode = ArrayList<Node>()
                        it.childNode!!.add(node)
                    }
                    nodeMap.put(nodeId-1, value)
                    edit.addChildNodes(selectedNote, value)
                    if (isShared == 1){
                        sendAndUpdate(modifyBean(
                            userInfo,
                            selectedNote?.value?.id?:0,
                            MofidyBean.ACTION_ADD_NODE,
                            gson.toJson(node),
                            thinkMapId))
                    }else{
                    }
                }
            }
            it.onError{e,r->
                "目标资源不存在".toast()
            }
        }
    }
    private val adapter by lazy {
        val mapAdapter = ThinkMapAdapter(this)
        mapAdapter.clickListener = adapterListener
        mapAdapter
    }
    private val adapterListener: TreeNodeClickListener<Node> by lazy {
        object : TreeNodeClickListener<Node> {
            override fun select(node: NodeModel<Node>) {
                lastNote = selectedNote
                lastHolder = selectHolder
                selectedNote = node
                selectHolder = adapter.getTreeViewHolder(node) as BaseTreeViewHolder<Node, ItemThinkMapNodeBinding>
                lastHolder?.binding?.root?.background = ResourceUtil.getDrawable(R.drawable.node_not_selected)
                selectHolder?.binding?.root?.background = ResourceUtil.getDrawable(R.drawable.node_selected)
                view.removeButton.show()
                view.addButton.show()
                view.modifyButton.show()
                view.quoteButton.show()
                if (isShared == 1){
                    send( modifyBean(
                        userInfo,
                        selectedNote!!.value.id,
                        MofidyBean.ACTION_SELECT,
                        "${lastNote?.value?.id ?: ""}",
                        thinkMapId))

                }
            }
            override fun clickDouble(node: NodeModel<Node>) {
                showModifyDialog(node.value)
            }

            override fun longClick(node: NodeModel<Node>) {
                loadDrawer(node)
                view.root.openDrawer(GravityCompat.START)
            }
            override fun deleteNode(bean: MofidyBean) {
                if (isShared == 0)return
                nodeMap.get(bean.id)?.let {
                    val edit = view.thinkMapTreeView.editor
                    edit.removeNode(it)
                }
            }
            override fun addNode(bean: MofidyBean) {
                if (isShared == 0)return
                val edit = view.thinkMapTreeView.editor
                nodeMap.get(bean.id)?.let {
                    val fromJson = gson.fromJson(bean.data, Node::class.java)
                    if (it.value!!.childNode== null ) {
                        it.value!!.childNode = ArrayList<Node>()
                    }
                    it.value!!.childNode!!.add(fromJson)
                    val nodeModel = NodeModel(fromJson)
                    nodeMap.put(fromJson.id,nodeModel)
                    edit.addChildNodes(it, nodeModel)
                }
            }
            override fun titleChange(bean: MofidyBean) {
                if (isShared == 0)return
                bean.userName = userInfo.userName ?: ""
                bean.userToken = userInfo.token ?: ""
                bean.userPhoto = userInfo.headPicture ?: ""
                bean.position = userInfo.position
                bean.thinkMapId = thinkMapId
                sendAndUpdate(bean)

            }
        }
    }
    private val treeListener by lazy {
        object : MyTreeViewListener {
            override fun onClick() {
                view.removeButton.hide()
                view.addButton.hide()
                view.modifyButton.hide()
                view.quoteButton.hide()
                selectHolder?.binding?.root?.background = ResourceUtil.getDrawable(R.drawable.node_not_selected)
                if (isShared == 1 && selectedNote!=null){
                    sendAndUpdate(modifyBean(userInfo,
                        selectedNote!!.value.id,
                        MofidyBean.ACTION_SELECT_NOT,
                        "",
                        thinkMapId))

                }
            }
        }
    }
    private fun showModifyDialog(node: Node) {
        val dialog = ModifyDialog(node)
        dialog.listener = dialogListener
        dialog.show(supportFragmentManager, dialog.tag)
    }
    private val dialogListener by lazy {
        object : ModifyDialogListener {
            override fun submit(text: String, list: List<LocalMedia>) {
                selectHolder?.let { holder->
                    selectedNote?.let {
                        it.value.text = text
                        if (isShared == 1){

                            sendAndUpdate(modifyBean(
                                userInfo,
                                it.value.id,
                                MofidyBean.ACTION_CONTENT_CHANGE,
                                text,
                                thinkMapId,
                            ))

                        }
                        if (it.value.pictureList == null){
                            it.value.pictureList = ArrayList<String>()
                        }
                       lifecycleScope.launch{
                           if (list.size>0){
                               val map = list.map {
                                   async {
                                       ResourceRepository.upLoadIMage(it.realPath)
                                   }
                               }
                               val map1 = map.awaitAll().map {
                                   if (it.code == 0) {
                                       it.data
                                   } else null
                               }.filterNotNull()
                               (holder.binding.pictureRecyclerView.adapter as ThinkMapPicturesAdapter).add(map1)
                               it.value.pictureList!!.addAll(map1)
                               if (isShared == 1){
                                   sendAndUpdate(modifyBean(
                                       userInfo,
                                       it.value.id,
                                       MofidyBean.ACTION_PICTURE_CHANGE,
                                       gson.toJson(it.value.pictureList),
                                       thinkMapId
                                   ))

                               }
                               holder.binding.pictureRecyclerView.show()
                           }
                       }
                    }
                    holder.binding.thinkMapTexTView.text = text
                }
            }
        }
    }
    private fun showQuoteDialog() {
        val dialog = QuoteDialog()
        dialog.listener = quoteDialogListener
        dialog.show(supportFragmentManager, dialog.tag)
    }
    private val quoteDialogListener by lazy {
        object : ModifyDialogListener {
            override fun submit(text: String, list: List<LocalMedia>) {
                text.toIntOrNull().nullOrNot(
                    {"错误的索引或者索引不存在".toast()},
                    {
                        viewModel.getInfo(it)
                    }
                )
            }
        }
    }
    private fun setRoot(rootData: Node) {
        rootData.id = nodeId++
        root = NodeModel(rootData)
        tree = TreeModel(root)
        nodeMap.put(rootData.id,root!!)
        loadNode(root!!,tree)
        adapter.treeModel = tree
    }
    fun loadNode(node:NodeModel<Node>, treeModel:TreeModel<Node>){
        node.value.childNode?.forEach {
            it.id = nodeId++
            val nodeModel = NodeModel(it)
            treeModel.addNode(node,nodeModel)
            nodeMap.put(it.id,nodeModel)
            loadNode(nodeModel,treeModel)
        }
    }
    private fun loadTree() {
        view.thinkMapTreeView.adapter = adapter
        view.thinkMapTreeView.setDragListener(
            object :DragListener{
                override fun removeNode(
                    parent: NodeModel<*>?,
                    childNode: NodeModel<*>?,
                    newP: NodeModel<*>?
                ) {
                    (newP as? NodeModel<Node>)?.let {newP->
                        (parent as? NodeModel<Node>)?.let { parent->
                            (childNode as? NodeModel<Node>)?.let { child ->
                                parent.value.childNode!!.remove(child.value)
                                if (newP.value.childNode == null)newP.value.childNode = ArrayList<Node>()
                                newP.value.childNode!!.add(child.value)
                                if (isShared == 1){
                                    sendAndUpdate(modifyBean(
                                        userInfo,
                                        MofidyBean.ID_ACTIVITY,
                                        MofidyBean.ACTION_DRAW_NODE,
                                        "${parent.value.id} ${child.value.id} ${newP.value.id}",
                                        thinkMapId
                                    ))

                                }
                            }
                        }
                    }
                }
            }
        )
        view.thinkMapTreeView.listener = treeListener
        view.thinkMapTreeView.setTreeLayoutManager(treeLayoutManager)
        val token = Any()
        val dismissRun = Runnable {
            view.scalePercent.setVisibility(
                View.GONE
            )
        }
        view.thinkMapTreeView.setTreeViewControlListener(object : TreeViewControlListener {
            override fun onScaling(state: Int, percent: Int) {
                view.scalePercent.show()
                if (state == TreeViewControlListener.MAX_SCALE) {
                    view.scalePercent.setText("MAX")
                } else if (state == TreeViewControlListener.MIN_SCALE) {
                    view.scalePercent.setText("MIN")
                } else {
                    view.scalePercent.setText("$percent%")
                }
                handler.removeCallbacksAndMessages(token)
                handler.postAtTime(dismissRun, token, SystemClock.uptimeMillis() + 2000)
            }

            override fun onDragMoveNodesHit(
                draggingNode: NodeModel<*>?,
                hittingNode: NodeModel<*>?,
                draggingView: View?,
                hittingView: View?
            ) {}
        })
    }

    private fun loadDrawer(node: NodeModel<Node>) {
        val node =node.value
        if (node.pictureList?.size?:0 > 0){
            view.pictureRecyclerView.show()
            view.pictureRecyclerView.adapter = ThinkMapPicturesAdapter(1).apply {
                add(node.pictureList)
                listener = PreviewListener(this@ThinkMapActivity, node.pictureList!!.map{
                    LocalMedia.generateHttpAsLocalMedia(it)
                })
            }
            view.pictureRecyclerView.setOnTouchListener { v, event ->
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

        }else view.pictureRecyclerView.gone()
        view.titleTv.text = node.title
        view.contentTV.text = node.text
    }
    fun loadButton() {
        val edit = view.thinkMapTreeView.editor
        view.quoteButton.singleClick {
            selectedNote.nullOrNot(
                { return@singleClick },
                {
                  showQuoteDialog()
                }
            )
        }
        view.quoteButton.load(ResourceUtil.getString(R.string.url_quote))
        view.addButton.singleClick {
            selectedNote.nullOrNot(
                { return@singleClick },
                {
                    val node = Node("记录灵光瞬间", nodeId++)
                    val sNode = it.value
                    if (sNode.childNode == null) sNode.childNode = ArrayList<Node>()
                    sNode.childNode!!.add(node)
                    val model = NodeModel(node)
                    nodeMap.put(nodeId-1,model)
                    edit.addChildNodes(selectedNote, model)
                    if (isShared == 1){
                        sendAndUpdate(modifyBean(
                            userInfo,
                            selectedNote?.value?.id?:-1,
                            MofidyBean.ACTION_ADD_NODE,
                            gson.toJson(node),
                            thinkMapId))
                    }
                }
            )
        }
        view.removeButton.singleClick {
            selectedNote.nullOrNot(
                { return@singleClick },
                {it->
                    if (it == root) {
                        "根节点不允许删除，请直接删除导图".toast()
                        return@singleClick
                    }
                    view.removeButton.hide()
                    view.addButton.hide()
                    view.modifyButton.hide()
                    view.quoteButton.hide()
                    (it.parentNode as? NodeModel<Node>)?.let {it2->
                        it2.value.childNode!!.remove(it.value as Node)
                    }
                    edit.removeNode(selectedNote)
                    if (isShared == 1 ){
                        sendAndUpdate(modifyBean(
                            userInfo,selectedNote?.value?.id?:0,MofidyBean.ACTION_DELETE_NODE,"",thinkMapId
                        ))

                    }
                }
            )
        }
        view.modifyButton.singleClick {
            selectedNote.nullOrNot(
                { return@singleClick },
                {
                    showModifyDialog(it.value)
                }
            )
        }
        view.fitWindowButton.singleClick {
            edit.focusMidLocation()
        }
        view.editButton.apply {
            setTag(id, false)
            singleClick {
                val b = !(getTag(id) as Boolean)
                setTag(id, b)
                edit.requestMoveNodeByDragging(b)
                if (b){
                    view.editButton.backgroundTintList = AppCompatResources.getColorStateList(this@ThinkMapActivity,R.color.gradientMostlyCloudy_c3)
                }else{
                    view.editButton.backgroundTintList = AppCompatResources.getColorStateList(this@ThinkMapActivity,org.jxxy.debug.common.R.color.white)
                }
            }
        }
        view.clearButton.singleClick {
            "该功能未开放".toast()
        }
        if (isShared == 0)        view.lastButton.load(ResourceUtil.getString(R.string.url_lock))
        else         view.lastButton.load(ResourceUtil.getString(R.string.url_unlock))
        view.lastButton.singleClick {
            if (thinkMapId == 0) {
                "请先保存导图".toast()
                return@singleClick
            }
            root?.value?.let {
                viewModel.update(ThinkMapRespone(
                    thinkMapId,
                    it.title,
                    if(isShared == 1) 0 else 1,
                    gson.toJson(it),
                    it.text,
                    ""
                    ))
            }
        }
        view.nextButton.load(
            ResourceUtil.getString(R.string.url_save)
        )
        view.nextButton.singleClick {
            if (thinkMapId != 0) root?.value?.let {
                Log.d(TAG, "loadButton: ${gson.toJson(it)}")
                viewModel.update(ThinkMapRespone(
                    thinkMapId,
                    it.title,
                   isShared,
                    gson.toJson(it),
                    it.text,
                    ""
                ))
            }else root?.value?.let {
                Log.d(TAG, "loadButton: ${gson.toJson(it)}")
                viewModel.save(ThinkMapRespone(
                    thinkMapId,
                    it.title,
                    0,
                    gson.toJson(it),
                    it.text,
                    ""
                ))
            }
        }
    }
    val userAdapter by lazy { UserAdapter() }
    fun loadUser(list: List<UserInfo>){
        view.userRV.adapter = userAdapter.apply {
            clearAndAdd(list.mapNotNull { it })
        }
    }
    private val treeLayoutManager: TreeLayoutManager
        get() {
            val space_50dp = 30
            val space_20dp = 20
            val line: BaseLine = line
            return BoxRightTreeLayoutManager(this, space_50dp, space_20dp, line)
        }
    private val line: BaseLine
        get() = SmoothLine()

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShared == 1){
            client.stopThread(byteArrayOf(0))
        }
    }
    private fun sendAndUpdate(modifyBean: MofidyBean) {
        val message = Message.obtain()
        message.what = 0
        message.obj = modifyBean
        client.revHandler.sendMessage(message)
        root?.value?.let {
            lifecycleScope.launch {
                viewModel.re.apiService.updateMap(
                    ThinkMapRespone(
                        thinkMapId,
                        it.title,
                        isShared,
                        gson.toJson(it),
                        it.text,
                        it.pictureList?.get(0)?:""
                    )
                )
            }

        }
    }
    private fun send(modifyBean: MofidyBean){
        val message = Message.obtain()
        message.what = 0
        message.obj = modifyBean
        client.revHandler.sendMessage(message)
    }
    companion object{
        val seletctColorList = listOf<Drawable>(
            ResourceUtil.getDrawable(R.drawable.node_p_200_selected)!!,
            ResourceUtil.getDrawable(R.drawable.node_orange_selected)!!,
            ResourceUtil.getDrawable(R.drawable.node_purple_selected)!!,
            ResourceUtil.getDrawable(R.drawable.node_pink_selected)!!,
            ResourceUtil.getDrawable(R.drawable.node_yellow_selected)!!,
        )
        val nodeNotSelectB = ResourceUtil.getDrawable(R.drawable.node_not_selected)!!
    }
}
fun modifyBean(userInfo: UserInfo, id: Int, action: Int, data: String, thinkMapId: Int):MofidyBean = MofidyBean(
    userInfo.token ?: "",
    userInfo.userName ?: "",
    userInfo.headPicture ?: "" ,
    userInfo.position,
    id, action, data, thinkMapId
)
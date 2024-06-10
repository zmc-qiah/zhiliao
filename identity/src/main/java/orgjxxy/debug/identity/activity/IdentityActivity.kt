package orgjxxy.debug.identity.activity
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import okhttp3.internal.cache2.Relay.Companion.edit
import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.AddressManager
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.identity.R
import org.jxxy.debug.identity.databinding.ActivityIdentityBinding
import org.jxxy.debug.search.util.Identity_SpaceItemDecoration
import orgjxxy.debug.identity.adapter.IdentityAdapter
import orgjxxy.debug.identity.bean.IdentityContextBean
import orgjxxy.debug.identity.http.viewModel.IdentityViewModel

class IdentityActivity: BaseActivity<ActivityIdentityBinding>() {
    //存放所有问题选项的列表
    private val questionlist=ArrayList<ArrayList<IdentityContextBean>>()
    //存放题目的列表
    private val Titlelist = ArrayList<String?>()

    //存放选项转化为A.B.C.D的列表
    private val Optionlist =ArrayList<Char>()
    private var type:Int=1

    val viewModel: IdentityViewModel by lazy {
        ViewModelProvider(this).get(IdentityViewModel::class.java)
    }
    //num用作题号
    private var num=0
    //存放选项的列表，初始默认选第一项
    private val list = ArrayList(List(9) { 0 })
    private var adapter=IdentityAdapter(list,num)
    override fun bindLayout(): ActivityIdentityBinding {
        return  ActivityIdentityBinding.inflate(layoutInflater)
    }

    override fun initView() {

        viewModel.IdentityGet()
        val layoutManager1 = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        view.questionRv.layoutManager=layoutManager1
        view.questionRv.adapter=adapter
        view.questionRv.addItemDecoration(Identity_SpaceItemDecoration(30))
        view.lastproblemTv.singleClick {
            onClick(it)
        }
        view.nextproblemTv.singleClick {
            onClick(it)
        }
        view.finshTv.singleClick {
            onClick(it)
        }
     }

    override fun subscribeUi() {

       //将要使用的资源填入列表
        viewModel.IdentityGetData.observe(this){ res ->
            res.onSuccess { content ->
                content!!.first?.forEach {
                    Titlelist.add(it!!.tabName!!)
                    val Optionlist = ArrayList<IdentityContextBean>() // 创建一个新的Optionlist对象
                    it.list?.forEach{
                        val input=it.tabName
                        val (option, context) = edit(input!!)
                        Optionlist.add(IdentityContextBean(1,option,context))
                        Log.i("tag", it.tabName!!)
                    }
                    questionlist.add(Optionlist)
                    Log.i("tag", questionlist.size.toString())
                }
            }
            //对第一题的初始化
            view.tileTV.text=Titlelist[num]
            adapter.add(questionlist[num])


        }



    }

    //三个点击组件，上一题，下一题，完成
     fun onClick(view: View) {
        when (view.id) {
            R.id.lastproblemTv-> lastproblem()
            R.id.nextproblemTv-> nextproblem()
            R.id.finshTv-> finshId()
        }
        changeUi()
    }
    //跳转上一题
    fun lastproblem(){
        if (num >0) {
            num -= 1
        }
//        adapter=IdentityAdapter(list,num)
        Log.i("23333333","lastproblem")
        adapter.currentQuestion = num
//        adapter.isSwitch = true
        adapter.isOptionClick =false
        adapter.clearAndAdd(questionlist[num])
//        adapter.isSwitch = false
//        adapter.setOptionPosition(num)
//        adapter.
//        view.questionRv.adapter=adapter

    }
    //跳转下一题
    fun nextproblem(){
        if (num in 0 until questionlist.size-1) {
            num += 1
        }
//       adapter=IdentityAdapter(list,num)
        adapter.currentQuestion = num
//        adapter.isSwitch = true
        adapter.isOptionClick =false
        adapter.clearAndAdd(questionlist[num])
//        adapter.isSwitch = false
//        adapter.setOptionPosition(num)
//       view.questionRv.adapter=adapter

    }
    @SuppressLint("SetTextI18n")
        //修改右上角完成情况
        //修改题目文本
        //最后一题修改提交按钮显示情况
    fun changeUi(){
        if(num==Titlelist.size-1){
            view.finshTv.visibility=View.VISIBLE
            view.nextproblemTv.visibility=View.GONE
        }
        else{
            view.nextproblemTv.visibility=View.VISIBLE
            view.finshTv.visibility=View.GONE
        }
        val currentProgress = num
        if (currentProgress <=view. progressBar.max) {
            view.progressBar.progress = currentProgress
        }
        view.progressTv.text=(num+1).toString()+"/9"
        view.tileTV.text=Titlelist[num]
    }
    //点击完成按钮事件
    fun finshId(){

        //把选线序号转成A。B。C。D。
        list.forEach{
            val letter = 'A' + it
            Optionlist.add(letter)
        }
        if (Optionlist[0]== 'A'){
            type=2


        }
        else if (Optionlist[1]=='C'){

            type=3
        }
        AddressManager.updateCity(type.toString())
        IdentityUtil.instance.state.value=type

        //提交结果
        viewModel.identityanswerpost(type,
            Optionlist[0],
            Optionlist[1],
            Optionlist[2],
            Optionlist[3],
            Optionlist[4],
            Optionlist[5],
            Optionlist[6],
            Optionlist[7],
            Optionlist[8])
        println(Optionlist)

        finish()
    }

    //拆分接口返回的String
    fun edit(input: String): Pair<String, String> {
        val optionPattern = "[A-Z.]+".toRegex() // 匹配大写英文字母和符号的正则表达式
        val optionMatchResult = optionPattern.find(input)
        val option = optionMatchResult?.value ?: "" // 如果没有匹配到，则返回空字符串

        val context = input.substringAfterLast(".")

        return Pair(option, context)
    }




}
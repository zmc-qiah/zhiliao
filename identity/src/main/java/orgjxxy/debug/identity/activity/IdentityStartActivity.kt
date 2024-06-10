package orgjxxy.debug.identity.activity

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.identity.databinding.ActivityIdentitystartBinding
import org.jxxy.debug.search.util.Identity_SpaceItemDecoration
import org.jxxy.debug.search.util.Identity_Space_HorzItemDecoration
import orgjxxy.debug.identity.MyClickListener
import orgjxxy.debug.identity.adapter.IdentityStartAdapter
import orgjxxy.debug.identity.bean.IdentityChooseBean
import orgjxxy.debug.identity.http.viewModel.IdentityViewModel

//身份选择初始页，三个快速主要身份，以及身份问卷
class IdentityStartActivity : BaseActivity<ActivityIdentitystartBinding>() {
    private var flag: Boolean=true
    private val Identitylist = ArrayList<IdentityChooseBean>()
    val viewModel: IdentityViewModel by lazy {
        ViewModelProvider(this).get(IdentityViewModel::class.java)
    }
    // 移除类级别的adapter初始化
    private lateinit var adapter: IdentityStartAdapter
    override fun bindLayout(): ActivityIdentitystartBinding {
        return ActivityIdentitystartBinding.inflate(layoutInflater)
    }
    val listener = object : MyClickListener {
        override fun onClick(identity: Int, list: List<Char>) {
            IdentityUtil.instance.state.value=identity
            viewModel.identityanswerpost(identity,
                list[0],
                list[1],
                list[2],
                list[3],
                list[4],
                list[5],
                list[6],
                list[7],
                list[8])
        }
    }
    override fun initView() {
        viewModel.IdentityAllGet()
        // 在此初始化adapter
        adapter = IdentityStartAdapter(viewModel, ::finish, listener)
        view.finshTv.singleClick {
            startActivity<IdentityActivity>()
        }
        val layoutManager1 = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        view.identityRv.layoutManager = layoutManager1
        view.identityRv.addItemDecoration(Identity_Space_HorzItemDecoration(10,10))
        view.identityRv.adapter = adapter
        view.identityRv.addItemDecoration(Identity_SpaceItemDecoration(30))
    }

    override fun subscribeUi() {
        viewModel.identityAllGetData.observe(this) { res ->
            res.onSuccess { r ->
                Identitylist.addAll(r!!.identityInfos!!.map { IdentityChooseBean(0, it!!.identityUrl!!, it.identityName!!, it.identityId!!) })
                adapter.add(Identitylist)
                Log.i("23333",flag.toString())
                view.identityRv.adapter = adapter
            }
        }
        viewModel.identityanswerpostData.observe(this){
            it.onSuccess {
                finish()
            }
        }
    }
}



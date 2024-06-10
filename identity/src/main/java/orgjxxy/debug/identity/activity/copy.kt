/*
package orgjxxy.debug.identity.activity;

import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.AddressManager
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.identity.databinding.ActivityIdentitystartBinding
import orgjxxy.debug.identity.http.viewModel.IdentityViewModel

//身份选择初始页，三个快速主要身份，以及身份问卷
class IdentityStartActivity : BaseActivity<ActivityIdentitystartBinding>(){
    private var type :Int =1
    val viewModel: IdentityViewModel by lazy {
        ViewModelProvider(this).get(IdentityViewModel::class.java)
    }
    private val Optionlist = ArrayList<Char>().apply {
        repeat(9) {
            add('A')
        }
    }
    override fun bindLayout(): ActivityIdentitystartBinding {
        return  ActivityIdentitystartBinding.inflate(layoutInflater)
    }
    override fun initView() {
        viewModel.IdentityAllGet()
        viewModel.getById(1)
        view.DeafaultCL.singleClick {
            type=1
            ChooseIdentity()

        }
        view.studentCL.singleClick {
            type=2
            ChooseIdentity()
        }
        view.exportCL.singleClick {
            type=3
            ChooseIdentity()
        }
        view.identitychooseTv.singleClick {
            startActivity<IdentityActivity>()
        }
    }

    override fun subscribeUi() {
viewModel.getByIdData.observe(this){ res ->
            res.onSuccess {
                println(it!!.message)


        }
    }





        viewModel.identityAllGetData.observe(this){ res ->
            res.onSuccess { content ->
                if (content != null) {
                    view.DeafaultImg.load(content.identityInfos?.get(0)?.identityUrl)
                    view.StudentImg.load(content.identityInfos?.get(1)?.identityUrl)
                    view.ExportImg.load(content.identityInfos?.get(2)?.identityUrl)

                }
            }


        }
    }
    fun ChooseIdentity(){
        AddressManager.updateCity(type.toString())
        IdentityUtil.instance.state.value=type
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


}
*/

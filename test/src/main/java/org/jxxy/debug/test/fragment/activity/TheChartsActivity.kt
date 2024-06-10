package org.jxxy.debug.test.fragment.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.test.databinding.ActivityTheChartsBinding
import org.jxxy.debug.test.fragment.adapter.TheChartsAdapter
import org.jxxy.debug.test.fragment.bean.TheChartsMember
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class TheChartsActivity : BaseActivity<ActivityTheChartsBinding>() {
    var type = 0
    lateinit var adapter : TheChartsAdapter
    val viewModel : QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    override fun bindLayout(): ActivityTheChartsBinding {
        return ActivityTheChartsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val url = "https://ts1.cn.mm.bing.net/th/id/R-C.ae9164183ecdb09c8e01ada7af40f15a?rik=K%2bv8Rw%2fp3%2foOWQ&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2019%2f08-24%2f69216%2fwater_69216_698_515.91_.png&ehk=vsV2PwFoQo01Wr1fT92jbnlkn1Ln4tpa2ZbWWwqV1wA%3d&risl=&pid=ImgRaw&r=0"
        type = intent.getIntExtra("type",0)
        adapter = TheChartsAdapter()
        view.theChartsRv.adapter = adapter
        view.theChartsRv.layoutManager = LinearLayoutManager(this)
    }

    override fun subscribeUi() {
        viewModel.getSpecialRankLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    it.specialRankInfos.run {
                        view.theChartsRv.addItemDecoration(CommonItemDecoration(5f))
                        if(size >= 1) {
                            view.theFirstIv.load(this[0].headPhoto, true)
                            view.theFirstNameTv.text = this[0].peopleName
                            view.theFirstDamageTv.text = "造成${this[0].damage}伤害"
                        }
                        if(size >= 2) {
                            view.theSecondIv.load(this[1].headPhoto, true)
                            view.theSecondNameTv.text = this[1].peopleName
                            view.theSecondDamageTv.text = "造成${this[1].damage}伤害"
                        }
                        if(size >= 3) {
                            view.theThirdIv.load(this[2].headPhoto, true)
                            view.theThirdNameTv.text = this[2].peopleName
                            view.theThirdDamageTv.text = "造成${this[2].damage}伤害"
                        }
                        adapter.run {
                            var index = 1
                            for (member in it.specialRankInfos){
                                add(TheChartsMember(index,member.headPhoto,member.peopleName,member.damage))
                                index ++
                            }
                        }
                    }
                }
            }
        }
        viewModel.getSpecialRank(type)
    }
}
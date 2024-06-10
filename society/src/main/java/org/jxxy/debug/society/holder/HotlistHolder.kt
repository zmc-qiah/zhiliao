package org.jxxy.debug.society.holder
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.HotlistBean
import org.jxxy.debug.society.databinding.ItemHotlistBinding

class HotlistHolder (view : ItemHotlistBinding):
    SingleViewHolder<ItemHotlistBinding, HotlistBean >(view){
    override fun setHolder(entity: HotlistBean) {
     view.hotdataTv.text=entity.hotdataTv
        view.rankTv.text=entity.rankTv.toString()
        view.listTv.text=entity.listTv
        view.hotImg.load(entity.hotImg,20)
        view.textTv.text=entity.textTv





        }
    }
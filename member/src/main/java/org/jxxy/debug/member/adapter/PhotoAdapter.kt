package org.jxxy.debug.member.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.luck.picture.lib.PictureSelectorPreviewFragment
import com.luck.picture.lib.basic.IBridgeViewLifecycle
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.widget.PreviewTitleBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.databinding.ItemForunPictureBinding
import org.jxxy.debug.member.myListener.OnItemClickListener

class PhotoAdapter(val context: Context,val maxSize:Int,val span:Int): SingleTypeAdapter<String>() {
    override fun getItemCount(): Int = if (data.size>maxSize) maxSize else data.size
    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(v: View?, position: Int) {
            val list = ArrayList<LocalMedia>()
            data.forEach{
                list.add(LocalMedia.generateHttpAsLocalMedia(it))
            }

            PictureSelector.create(context)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setAttachViewLifecycle(object : IBridgeViewLifecycle {
                    override fun onViewCreated(
                        fragment: Fragment?,
                        view: View?,
                        savedInstanceState: Bundle?
                    ) {
                        GlobalScope.launch(Dispatchers.IO) {
                            delay(100)
                            launch(Dispatchers.Main) {
                                (fragment as PictureSelectorPreviewFragment)
                                    .view?.findViewById<PreviewTitleBar>(com.luck.picture.lib.R.id.title_bar)
                                    ?.findViewById<ImageView>(com.luck.picture.lib.R.id.ps_iv_delete)?.gone()
                            }
                        }
                    }
                    override fun onDestroy(fragment: Fragment?) {
                    }
                })
                .startActivityPreview(position, true, list)
        }
        override fun openPicture() {
        }
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = object : SingleViewHolder<ItemForunPictureBinding, String>(
        ItemForunPictureBinding.inflate(inflater,parent,false)){
        override fun setHolder(entity: String) {
            if (span==3){
                view.root.layoutParams.width = (getWidth()/span*0.9).toInt()
                view.root.layoutParams.height = view.root.layoutParams.width
                view.root.layoutParams.width = (getWidth()/span*0.9).toInt()
                view.root.layoutParams.height = view.root.layoutParams.width
            }
            view.pictureIV.load(entity,6)
            if (layoutPosition==maxSize-1&&layoutPosition==3){
                view.moreIV.show()
                view.moreIcon.show()
            }
            view.pictureIV.singleClick {
                onItemClickListener.onItemClick(it,layoutPosition)
            }
        }
    }
}
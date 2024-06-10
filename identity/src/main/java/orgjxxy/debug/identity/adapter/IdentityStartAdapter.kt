package orgjxxy.debug.identity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.identity.databinding.ItemIdentitystartBinding
import orgjxxy.debug.identity.MyClickListener
import orgjxxy.debug.identity.holder.IdentityStartHolder
import orgjxxy.debug.identity.http.viewModel.IdentityViewModel
import kotlin.reflect.KFunction0

class IdentityStartAdapter(val viewModel: IdentityViewModel, val finish: KFunction0<Unit>,val listener:MyClickListener?) :  MultipleTypeAdapter() {

        override fun createViewHolder(
                viewType: Int,
                inflater: LayoutInflater,
                parent: ViewGroup
        ): RecyclerView.ViewHolder? {

                return when (viewType) {
                        0 -> IdentityStartHolder(ItemIdentitystartBinding.inflate(inflater),viewModel,finish,listener)

                        else -> null
                }
        }}
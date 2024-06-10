package org.jxxy.debug.theme.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.databinding.FragmemtChatDialogBinding
import org.jxxy.debug.theme.http.viewModel.ThemeViewModel

class ChatDialogFragment:BottomSheetDialogFragment() {
    private lateinit var find : FragmemtChatDialogBinding
    private val viewModel:ThemeViewModel by lazy { ViewModelProvider(this).get(ThemeViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        find = FragmemtChatDialogBinding.inflate(inflater,container,false)
        return find.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.let {
            val params = it.layoutParams
            params.height = getHeight()/5*4
            it.layoutParams = params
            val behaviour = BottomSheetBehavior.from(it)
            behaviour.peekHeight = getHeight()/5*2
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}
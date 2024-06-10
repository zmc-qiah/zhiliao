package org.jxxy.debug.resources.fragment

import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.resources.databinding.FragmemtPhotoBinding

class PhotoFragment(val url: String) : BaseFragment<FragmemtPhotoBinding>() {
    override fun bindLayout(): FragmemtPhotoBinding = FragmemtPhotoBinding.inflate(layoutInflater)

    override fun initView() {
        find.imageView.loads(url,false,context)
    }

    override fun subscribeUi() {
    }
}

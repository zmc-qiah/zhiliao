package cn.yonghui.hyd.main.ui.cms.activities.seckillactivities.widget.nested

interface OnChildAttachStateListener {
    /**
     * 子布局吸附到顶部时回调
     */
    fun onChildAttachedToTop()

    /**
     * 子布局从顶部脱离时回调
     */
    fun onChildDetachedFromTop()
}

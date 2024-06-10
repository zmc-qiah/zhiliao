package org.jxxy.debug.common.widget.read.view;

import android.view.MotionEvent;

/**
 * create by zhusw on 2020-08-14 18:37
 */
public interface EventProxy {
    boolean onItemViewTouchEvent(MotionEvent event);

    boolean animRunning();
}

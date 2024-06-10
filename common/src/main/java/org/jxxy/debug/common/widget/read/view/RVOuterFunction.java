package org.jxxy.debug.common.widget.read.view;

import android.graphics.Bitmap;

/**
 * create by zhusw on 2020-08-17 10:15
 */
public interface RVOuterFunction {
    void onExpectNext(boolean smooth);

    void onExpectPrevious(boolean smooth);

    Bitmap getPreviousBitmap();

    Bitmap getCurrentBitmap();

    Bitmap getNextBitmap();

    int getFlipMode();
}

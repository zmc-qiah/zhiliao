package org.jxxy.debug.theme.posemon.ml

import android.graphics.Bitmap
import org.jxxy.debug.theme.posemon.data.Person

interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}

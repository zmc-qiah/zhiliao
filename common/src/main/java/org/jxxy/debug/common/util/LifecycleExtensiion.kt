package org.jxxy.debug.common.util

import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@MainThread
fun Lifecycle.isResumed(): Boolean {
    return currentState.isAtLeast(Lifecycle.State.RESUMED)
}

@MainThread
suspend inline fun Lifecycle.awaitResumed() {
    // 已经处于resume状态.
    if (isResumed()) return

    // 注册观察者，等待转换到resume状态才执行后续操作
    observeResume()
}

@MainThread
suspend fun Lifecycle.observeResume() {
    var observer: LifecycleObserver? = null
    try {
        suspendCancellableCoroutine { continuation ->
            observer = object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    if (continuation.isCompleted) {
                        Logger.w("observeResume", owner::class.java.simpleName)
                        return
                    }
                    continuation.resume(Unit)
                }
            }
            observer?.let(::addObserver)
        }
    } finally {
        // 执行完操作后remove掉观察者
        observer?.let(::removeObserver)
    }
}

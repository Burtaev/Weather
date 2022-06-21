package com.burtaev.weather.main.utils

import android.util.Log
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

private const val TAG = "RxExt"

private fun logThrowable(t: Throwable) = Log.e(TAG, t.message, t)

fun <T> Single<T>.subscribeSuccess(consumer: (T) -> Unit): Disposable =
    subscribe(consumer, ::logThrowable)

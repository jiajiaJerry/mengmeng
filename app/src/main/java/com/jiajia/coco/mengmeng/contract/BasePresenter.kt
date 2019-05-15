package com.jiajia.coco.mengmeng.contract

import android.os.Handler
import android.os.Looper

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description Presenter 基类
 */
interface BasePresenter {

    companion object {
        val handler by lazy {
            Handler(Looper.getMainLooper())
        }
    }

    fun uiThread(f: () -> Unit) {
        handler.post { f() }
    }
}
package com.jiajia.coco.mengmeng

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author Create by Jerry
 * @date on 2019-05-14
 * @description Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    /**
     * 初始化公共功能
     */
    open fun init() {

    }

    /**
     * 子类返回布局资源ID
     */
    abstract fun getLayoutResId(): Int
}
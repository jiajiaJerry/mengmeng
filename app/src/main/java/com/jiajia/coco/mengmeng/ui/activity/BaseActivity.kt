package com.jiajia.coco.mengmeng.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

/**
 * @author Create by Jerry
 * @date on 2019-05-14
 * @description Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy {
        ProgressDialog(this)
    }

    private val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

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

    fun showProgress(message: String) {
        progressDialog.setMessage(message)
        progressDialog.show()
    }

    fun dismissProgress() {
        progressDialog.dismiss()
    }

    fun hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
    }
}
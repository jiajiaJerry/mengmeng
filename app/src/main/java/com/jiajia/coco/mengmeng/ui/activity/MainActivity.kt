package com.jiajia.coco.mengmeng.ui.activity

import android.annotation.SuppressLint
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.factory.FragmentFactory
import com.jiajia.coco.mengmeng.utils.TLog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Create by Jerry
 * @date on 2019-05-13
 * @description 主页
 */
class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_main

    @SuppressLint("CommitTransaction")
    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_frame, FragmentFactory.instance.getFragment(tabId)!!)
            beginTransaction.commit()
        }
    }

}

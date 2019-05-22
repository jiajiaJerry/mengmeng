package com.jiajia.coco.mengmeng.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Create by Jerry
 * @date on 2019-05-14
 * @description  Fragment基类
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(getLayoutResId(), null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    /**
     * 初始化公共功能
     */
    open fun init() {
    }

    abstract fun getLayoutResId(): Int
}
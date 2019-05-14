package com.jiajia.coco.mengmeng

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup

/**
 * @author Create by Jerry
 * @date on 2019-05-14
 * @description
 */
class BottomUpDialog : Dialog {
    constructor(context: Context) : this(context,0)
    constructor(context: Context, themeResId: Int) : super(context, R.style.btm_dialog){
        //统一处理所有功能
        //关联布局文件
        setContentView(R.layout.dialog_layout)
        //设置显示位置
        window?.setGravity(Gravity.BOTTOM)
        //设置大小
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
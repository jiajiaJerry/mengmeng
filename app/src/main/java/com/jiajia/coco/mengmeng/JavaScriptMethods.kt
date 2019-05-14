package com.jiajia.coco.mengmeng

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.JavascriptInterface
import kotlinx.android.synthetic.main.dialog_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.net.URL

/**
 * @author Create by Jerry
 * @date on 2019-05-13
 * @description Kotlin与H5通信桥梁类
 */
class JavaScriptMethods(context: Context?) {

    private var mContext: Context? = context

    private val mDiaglo: BottomUpDialog by lazy {
        BottomUpDialog(this.mContext!!)
    }


    @JavascriptInterface
    fun showToast(json: String) {
//        Toast.makeText(mContext, json, Toast.LENGTH_LONG).show()
        mContext?.let { it.toast(json) }
    }

    @JavascriptInterface
    fun getHotelData() {
        mContext?.toast("获取酒店数据")
        doAsync {
            var url = URL(" ")
        }
    }

    @JavascriptInterface
    fun setPhoneDialog(phone: String) {
        callPhone
    }

    //底部弹出Dialog
    @JavascriptInterface
    fun showPhoneDialog(phone: String) {
        mDiaglo.show()
        mDiaglo.btn_call.text = phone
        mDiaglo.btn_call.onClick {
            mContext?.let {
                callPhone(phone)
            }
        }
        mDiaglo.btn_cancel.onClick {
            mDiaglo.dismiss()
        }
    }

    var callPhone = { phone: String ->
        Unit
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.action = "android.intent.action.DIAL"
        intent.addCategory("android.intent.category.DEFAULT")
        intent.addCategory("android.intent.category.BROWSABLE")
        intent.data = Uri.parse("tel:$phone")
        //拨号
        mContext?.let {
            it.startActivity(intent)
        }
    }
}
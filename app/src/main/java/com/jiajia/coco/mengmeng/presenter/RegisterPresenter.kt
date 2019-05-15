package com.jiajia.coco.mengmeng.presenter

import cn.bmob.v3.BmobQuery
import com.jiajia.coco.mengmeng.contract.RegisterContract
import com.jiajia.coco.mengmeng.extentions.isValidPassword
import com.jiajia.coco.mengmeng.extentions.isValidUserName
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.BmobUser
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.jiajia.coco.mengmeng.bean.Person
import com.jiajia.coco.mengmeng.utils.TLog
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Persenter {
    override fun register(userName: String, password: String, confirmPassword: String) {
        if (userName.isValidUserName()) {//检查用户名
            if (password.isValidPassword()) {//检查密码
                if (password.equals(confirmPassword)) {//检查一致
                    view.onStartRegister()
                    registerBmob(userName, password)
                } else view.onConfirmPasswordError()
            } else view.onPasswordError()
        } else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val p = Person()
        p.name = userName
        p.password = password
        p.save(object : SaveListener<String>() {
            override fun done(p0: String?, p1: BmobException?) {
                if (p1 == null) {
                    //注册到环信
                    TLog.i("TAG", "onRegisterSuccess")
                    registerEaseMob(userName, password)
                } else {
                    view.onRegisterFailed()
                }
            }

        })
    }

    private fun registerEaseMob(userName: String, password: String) {
        doAsync {
            try {
                TLog.i("TAG", "onRegisterSuccess")
                //注册失败会抛出HyphenateException
                EMClient.getInstance().createAccount(userName, password)//同步方法
                //注册成功
                uiThread { view.onRegisterSuccess() }
            } catch (e: HyphenateException) {
                TLog.i("TAG", "onRegisterFailed")
                //注册失败
                uiThread { view.onRegisterFailed() }
            }
        }

    }
}
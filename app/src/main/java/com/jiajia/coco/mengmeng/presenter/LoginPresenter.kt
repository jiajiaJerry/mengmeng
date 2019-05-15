package com.jiajia.coco.mengmeng.presenter

import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.jiajia.coco.mengmeng.adapter.EMCallBackAdapter
import com.jiajia.coco.mengmeng.contract.LoginContract
import com.jiajia.coco.mengmeng.extentions.isValidPassword
import com.jiajia.coco.mengmeng.extentions.isValidUserName

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {
    override fun login(userName: String, password: String) {
        if (userName.isValidUserName()) {//用户名是否合法
            if (password.isValidPassword()) {//密码是否合法
                view.onStartLogin()//合法登录
                logEaseMob(userName,password)//登录环信
            } else view.onPasswrodError()
        } else view.onUserNameError()
    }

    //登录环信
    private fun logEaseMob(userName: String, password: String) {
        EMClient.getInstance().login(userName,password,object : EMCallBackAdapter() {
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                //在主线程中通知View层
                uiThread { view.onLoggedInSuccess() }
            }

            override fun onError(code: Int, error: String?) {
                uiThread { view.onLoggedInFailed() }
            }

        })
    }
}
package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 登录界面协议
 */
interface LoginContract {
    interface Presenter : BasePresenter {
        fun login(userName:String,password:String)
    }

    interface View {
        fun onUserNameError()
        fun onPasswrodError()
        fun onStartLogin()
        fun onLoggedInSuccess()
        fun onLoggedInFailed()
    }
}
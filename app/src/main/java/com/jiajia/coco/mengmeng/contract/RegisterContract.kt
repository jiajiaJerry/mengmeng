package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 注册界面协议
 */
interface RegisterContract {

    interface Persenter:BasePresenter{
        fun register(userName:String,password:String,confirmPassword:String)
    }

    interface View{
        fun onUserNameError()
        fun onPasswordError()
        fun onConfirmPasswordError()
        fun onStartRegister()
        fun onRegisterSuccess()
        fun onRegisterFailed()
    }

}
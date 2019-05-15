package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description Splash界面协议
 */
interface SplashContract {

    interface Present:BasePresenter{
        fun checkLoginStatus()//检查登录状态
    }

    interface View{
        fun onNotLoggedIn()//没有登录的ui处理
        fun onLoggedIn()//已经登录的ui处理
    }
}
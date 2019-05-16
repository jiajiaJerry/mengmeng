package com.jiajia.coco.mengmeng.presenter

import com.hyphenate.chat.EMClient
import com.jiajia.coco.mengmeng.contract.SplashContract

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 启动页Present层
 */
class SplashPresenter(private val view: SplashContract.View) : SplashContract.Present {

    override fun checkLoginStatus() {
        if (isLoggedIn()) view.onLoggedIn() else view.onNotLoggedIn()
    }

    private fun isLoggedIn(): Boolean = EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
}
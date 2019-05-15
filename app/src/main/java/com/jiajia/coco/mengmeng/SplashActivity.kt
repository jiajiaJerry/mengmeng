package com.jiajia.coco.mengmeng

import android.os.Bundle
import android.os.Handler
import com.jiajia.coco.mengmeng.contract.SplashContract
import com.jiajia.coco.mengmeng.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() ,SplashContract.View{

    val preserter = SplashPresenter(this)

    companion object {
        val DELAY = 2000L
    }

    val handler by lazy {
        Handler()
    }

    init {
        preserter.checkLoginStatus()
    }

    override fun getLayoutResId(): Int =R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //延迟2s跳转到登录界面
    override fun onNotLoggedIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        },DELAY)
    }

    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }
}

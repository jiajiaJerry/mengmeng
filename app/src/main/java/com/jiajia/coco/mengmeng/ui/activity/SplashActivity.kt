package com.jiajia.coco.mengmeng.ui.activity

import android.os.Handler
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.contract.SplashContract
import com.jiajia.coco.mengmeng.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() ,SplashContract.View{

    private val preserter = SplashPresenter(this)

    companion object {
        const val DELAY = 2000L
    }

    private val handler by lazy {
        Handler()
    }

    override fun init(){
        preserter.checkLoginStatus()
    }

    override fun getLayoutResId(): Int = R.layout.activity_splash


    //延迟2s跳转到登录界面
    override fun onNotLoggedIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, DELAY)
    }

    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }
}

package com.jiajia.coco.mengmeng.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.hyphenate.chat.BuildConfig
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class IMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions())
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
        //Bmob初始化
        Bmob.initialize(this, "de945ad53337bb069b93e1687c77850b")
    }

}
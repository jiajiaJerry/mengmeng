package com.jiajia.coco.mengmeng.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.hyphenate.chat.BuildConfig
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.jiajia.coco.mengmeng.utils.TLog

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description  全局Application
 */
class IMApplication : Application() {

    companion object {
        /**
         * lateinit var 延迟初始化
         * 只能用来修饰类属性,不能修饰基本类型(因为基本类型有初始化默认值)
         * 作用就是让编译期在检查时不要因为属性变量未被初始化而报错
         */
        lateinit var instance: IMApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化
        EMClient.getInstance().init(this, EMOptions())
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
        TLog.init(!BuildConfig.DEBUG)
        //Bmob初始化
        Bmob.initialize(this, "de945ad53337bb069b93e1687c77850b")
    }

}
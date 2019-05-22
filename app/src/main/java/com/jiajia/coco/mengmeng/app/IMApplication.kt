package com.jiajia.coco.mengmeng.app

import android.app.ActivityManager
import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import cn.bmob.v3.Bmob
import com.hyphenate.chat.*
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.EMMessageListenerAdapter
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

    val soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)

    val duan by lazy {
        soundPool.load(instance, R.raw.duan, 0)
    }

    val yulu by lazy {
        soundPool.load(instance, R.raw.yulu, 0)
    }

    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            if (isForeground()) {
                soundPool.play(duan, 1f, 1f, 0, 0, 1f)
            } else {
                soundPool.play(yulu, 1f, 1f, 0, 0, 1f)
                showNotification(messages)
            }
        }
    }

    private fun showNotification(messages: MutableList<EMMessage>?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        messages?.forEach {
            var contentText = getString(R.string.no_text_message)
            if (it.type == EMMessage.Type.TXT) {
                contentText = (it.body as EMTextMessageBody).message
            }
            val notification = Notification.Builder(this)
                .setContentTitle(getString(R.string.receive_new_message))
                .setContentText(contentText)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.avatar1))
                .setSmallIcon(R.mipmap.ic_launcher)
                .notification
            notificationManager.notify(1, notification)
        }
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

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun isForeground(): Boolean {
        //判断app是否在前台
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (runningAppProcess in activityManager.runningAppProcesses) {
            if (runningAppProcess.processName == packageName) {
                //找到了app的进程
                return runningAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

}
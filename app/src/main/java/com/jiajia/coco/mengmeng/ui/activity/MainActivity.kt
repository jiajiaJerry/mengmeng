package com.jiajia.coco.mengmeng.ui.activity

import android.annotation.SuppressLint
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMContactListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.EMMessageListenerAdapter
import com.jiajia.coco.mengmeng.factory.FragmentFactory
import com.jiajia.coco.mengmeng.utils.TLog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author Create by Jerry
 * @date on 2019-05-13
 * @description 主页
 */
class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_main
    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            runOnUiThread { updateBottomBarUnReadCount() }
        }
    }

    @SuppressLint("CommitTransaction")
    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_frame, FragmentFactory.instance.getFragment(tabId)!!)
            beginTransaction.commit()
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener {
            override fun onConnected() {

            }

            override fun onDisconnected(errorCode: Int) {
                if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //多设备登录,跳转登录界面
                    startActivity<LoginActivity>()
                    finish()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        updateBottomBarUnReadCount()
    }

    private fun updateBottomBarUnReadCount() {
        //初始化bottombar未读消息的个数
        val tab = bottomBar.getTabWithId(R.id.tab_conversation)
        tab.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

}

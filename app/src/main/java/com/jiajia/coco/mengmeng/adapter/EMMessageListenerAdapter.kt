package com.jiajia.coco.mengmeng.adapter

import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage

/**
 * @author Create by Jerry
 * @date on 2019-05-21
 * @description 消息接受回调监听适配器
 */
open class EMMessageListenerAdapter : EMMessageListener {
    override fun onMessageChanged(message: EMMessage?, change: Any?) {
        
    }

    override fun onCmdMessageReceived(messages: MutableList<EMMessage>?) {
        
    }

    override fun onMessageReceived(messages: MutableList<EMMessage>?) {
        
    }

    override fun onMessageDelivered(messages: MutableList<EMMessage>?) {
        
    }

    override fun onMessageRead(messages: MutableList<EMMessage>?) {
        
    }
}
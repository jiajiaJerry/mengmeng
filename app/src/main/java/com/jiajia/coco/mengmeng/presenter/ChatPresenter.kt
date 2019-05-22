package com.jiajia.coco.mengmeng.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.jiajia.coco.mengmeng.adapter.EMCallBackAdapter
import com.jiajia.coco.mengmeng.contract.ChatContract
import com.jiajia.coco.mengmeng.data.Message
import com.jiajia.coco.mengmeng.utils.TLog
import org.jetbrains.anko.doAsync


/**
 * @author Create by Jerry
 * @date on 2019-05-21
 * @description
 */
class ChatPresenter(val view: ChatContract.View) : ChatContract.Presenter {
    companion object {
        val PAGE_SIZE = 10
    }

    val messages = mutableListOf<Message>()
    val tmp = mutableListOf<Message>()

    override fun sendMessage(contact: String, message: String) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        val message = EMMessage.createTxtSendMessage(message, contact)
        val m = Message(message)
        message.setMessageStatusCallback(object : EMCallBackAdapter() {
            override fun onSuccess() {
                super.onSuccess()
                uiThread {
                    view.onSendMessageSuccess()
                    view.onScrollToBottom()
                }
            }

            override fun onError(code: Int, error: String?) {
                super.onError(code, error)
                uiThread { view.onSendMessageFailed() }
            }
        })
        messages.add(m)
        view.onStartSendMessage()
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message)
    }

    override fun loadMessages(userName: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(userName)
            //获取此会话的所有消息
            val m = conversation.allMessages
            m.let { list ->
                list!!.forEach {
                    val message = Message(it)
                    messages.add(message)
                }
            }
            view.onMessageLoad()
        }
    }

    override fun loadMoreMessage(userName: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(userName)
            val startMsgId = messages[0].message.msgId
            //获取此会话的所有消息
            val m = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)

            m.let { list ->
                if (list.size > 0) {
                    tmp.clear()
                    list!!.forEach {
                        val message = Message(it)
                        tmp.add(message)
                    }
                    messages.addAll(0, tmp)
                }
            }
            view.onLoadMoreMessage(m.size)
        }
    }
}
package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-21
 * @description 聊天界面协议
 */
interface ChatContract {

    interface Presenter : BasePresenter {
        fun sendMessage(contact: String, message: String)
        fun loadMessages(userName: String)
    }

    interface View {
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFailed()
        fun onScrollToBottom()
        fun onMessageLoad()
    }
}
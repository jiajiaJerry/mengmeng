package com.jiajia.coco.mengmeng.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.jiajia.coco.mengmeng.R
import java.util.*

/**
 * @author Create by Jerry
 * @date on 2019-05-22
 * @description 会话界面Adapter
 */
class ConversationAdapter(layoutResId: Int, data: MutableList<EMConversation>?) :
    BaseQuickAdapter<EMConversation, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: EMConversation) {
        helper.setText(R.id.userName, item.conversationId())
        if (item.lastMessage.type == EMMessage.Type.TXT) {
            helper.setText(R.id.lastMessage, (item.lastMessage.body as EMTextMessageBody).message)
        } else {
            helper.setText(R.id.lastMessage, R.string.no_text_message)
        }

        val date = DateUtils.getTimestampString(Date(item.lastMessage.msgTime))
        helper.setText(R.id.timestamp, date)

        helper.setText(R.id.unreadCount, item.unreadMsgCount.toString())
            .setVisible(R.id.unreadCount, item.unreadMsgCount > 0)
    }
}
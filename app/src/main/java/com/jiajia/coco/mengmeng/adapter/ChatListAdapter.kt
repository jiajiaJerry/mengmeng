package com.jiajia.coco.mengmeng.adapter

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.data.Message
import java.util.*

/**
 * @author Create by Jerry
 * @date on 2019-05-21
 * @description 聊天界面adapter
 */
class ChatListAdapter(data: MutableList<Message>) : BaseMultiItemQuickAdapter<Message, BaseViewHolder>(data) {

    init {
        addItemType(1, R.layout.view_send_message_item)
        addItemType(2, R.layout.view_receive_message_item)
    }

    private fun isShowTimestamp(position: Int): Boolean {
        var showTimestamp = true
        if (position > 0) {
            showTimestamp = !DateUtils.isCloseEnough(data[position].message.msgTime, data[position - 1].message.msgTime)
        }
        return showTimestamp
    }


    override fun convert(helper: BaseViewHolder, item: Message) {
        if (isShowTimestamp(data.indexOf(item))) {
            helper.setText(R.id.timestamp, DateUtils.getTimestampString(Date(item.message.msgTime)))
        }
        if (item.message.type == EMMessage.Type.TXT) {
            when (item.status) {
                1 -> {
                    helper.setText(R.id.sendMessage, (item.message.body as EMTextMessageBody).message)
                    item.message.status().let {
                        when (it) {
                            EMMessage.Status.INPROGRESS -> {
                                helper.setVisible(R.id.sendMessageProgress, true)
                                val animationDrawable =
                                    helper.getView<ImageView>(R.id.sendMessageProgress).drawable as AnimationDrawable
                                animationDrawable.start()
                            }
                            EMMessage.Status.SUCCESS -> {
                                helper.setVisible(R.id.sendMessageProgress, false)
                            }
                            EMMessage.Status.FAIL -> {
                                helper.setVisible(R.id.sendMessageProgress, true)
                                helper.setBackgroundRes(R.id.sendMessageProgress, R.mipmap.msg_error)
                            }

                            else -> {
                            }
                        }
                    }
                }
                2 -> {
                    helper.setText(R.id.receiveMessage, (item.message.body as EMTextMessageBody).message)
                }

            }
        }


    }
}
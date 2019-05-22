package com.jiajia.coco.mengmeng.data

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.hyphenate.chat.EMMessage

/**
 * @author Create by Jerry
 * @date on 2019-05-21
 * @description
 */

class Message(val message: EMMessage) : MultiItemEntity {

    var status: Int = 0

    init {
        if (message.direct() == EMMessage.Direct.SEND)
            status = 1
        else if (message.direct() == EMMessage.Direct.RECEIVE)
            status = 2
    }

    override fun getItemType(): Int {
        return status
    }
}
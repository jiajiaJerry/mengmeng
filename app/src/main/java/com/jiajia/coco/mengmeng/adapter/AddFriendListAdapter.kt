package com.jiajia.coco.mengmeng.adapter

import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.data.AddFriendItem

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description
 */
class AddFriendListAdapter(layoutResId: Int, data: MutableList<AddFriendItem>?) :
    BaseQuickAdapter<AddFriendItem, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: AddFriendItem?) {
        helper?.setText(R.id.userName, item?.userName)
            ?.setText(R.id.timestamp, item?.timestamp)
        val button = helper!!.getView<Button>(R.id.add)
        if (item!!.isAdded) {
            button.text = "已添加"
            button.isEnabled = false
        } else {
            button.text = "添加"
            button.isEnabled = true
        }
    }
}
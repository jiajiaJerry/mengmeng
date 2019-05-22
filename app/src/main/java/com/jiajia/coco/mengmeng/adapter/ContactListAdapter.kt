package com.jiajia.coco.mengmeng.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.data.ContactListItem

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description  联系人Item
 */
class ContactListAdapter(layoutResId: Int, data: MutableList<ContactListItem>) :BaseQuickAdapter<ContactListItem, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ContactListItem?) {
        if (item != null) {
            helper.setText(R.id.firstLetter, item.firstLetter.toString())
                .setGone(R.id.firstLetter, item.showFirstLetter)
                .setText(R.id.userName, item.userName)
        }
    }
}
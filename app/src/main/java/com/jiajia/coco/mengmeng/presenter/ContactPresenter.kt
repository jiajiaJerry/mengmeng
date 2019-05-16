package com.jiajia.coco.mengmeng.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.jiajia.coco.mengmeng.contract.ContactContract
import com.jiajia.coco.mengmeng.data.ContactListItem
import com.jiajia.coco.mengmeng.data.db.Contact
import com.jiajia.coco.mengmeng.data.db.IMDatabase
import org.jetbrains.anko.doAsync

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description
 */
class ContactPresenter(val view: ContactContract.View) : ContactContract.Presenter {


    val contactListItems = mutableListOf<ContactListItem>()

    override fun loadContacts() {
        doAsync {
            try {
                //清空列表
                contactListItems.clear()
                //清空数据库
                IMDatabase.instance.deleteAllContacts()

                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                //根据首字符排序
                usernames.sortBy { it[0] }
                usernames.forEachIndexed { index, s ->
                    val showFirstLetter = index == 0 || s[0] != usernames[index - 1][0]
                    val contactListItem = ContactListItem(s, s[0].toUpperCase(), showFirstLetter)
                    contactListItems.add(contactListItem)
                    //保存到数据库
                    val contact = Contact(mutableMapOf("name" to s))
                    IMDatabase.instance.saveContact(contact)
                }
                uiThread { view.onLoadContactsSuccess() }
            } catch (e: HyphenateException) {
                uiThread { view.onLoadContactsFailed() }
            }
        }

    }

}
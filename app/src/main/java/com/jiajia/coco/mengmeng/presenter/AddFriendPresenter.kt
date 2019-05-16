package com.jiajia.coco.mengmeng.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import com.jiajia.coco.mengmeng.contract.AddFriendContract
import com.jiajia.coco.mengmeng.data.AddFriendItem
import com.jiajia.coco.mengmeng.data.Person
import com.jiajia.coco.mengmeng.data.db.IMDatabase
import com.jiajia.coco.mengmeng.utils.TLog
import org.jetbrains.anko.doAsync

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description 添加好友Present层
 */
class AddFriendPresenter(val view: AddFriendContract.View) : AddFriendContract.Prenter {

    val addFriendItems = mutableListOf<AddFriendItem>()

    override fun search(key: String) {
        val query = BmobQuery<Person>()
        query.addWhereContains("name", key)
            .addWhereEqualTo("name", EMClient.getInstance().currentUser)
            .findObjects(object : FindListener<Person>() {
                override fun done(p0: MutableList<Person>?, p1: BmobException?) {
                    if (p1 == null) {
                        //处理数据
                        //创建AddFriendItem集合
                        addFriendItems.clear()
                        val allContacts = IMDatabase.instance.getAllContacts()
                        doAsync {
                            TLog.i("TAG", "size${p0!!.size}")
                            p0.forEach {

                                //比对是否已经添加过好友
                                var isAdded = false
                                for (contact in allContacts) {
                                    if (contact.name == it.name) {
                                        isAdded = true
                                    }
                                }

                                val addFriendItem = AddFriendItem(it.name!!, it.createdAt, isAdded)
                                addFriendItems.add(addFriendItem)

                            }
                            uiThread { view.onSearchSuccess() }
                        }
                    } else {
                        view.onSearchFailed()
                    }
                }

            })

    }
}
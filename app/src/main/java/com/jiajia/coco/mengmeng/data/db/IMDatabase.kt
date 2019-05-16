package com.jiajia.coco.mengmeng.data.db

import com.jiajia.coco.mengmeng.extentions.toVarargArray
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description  数据库操作类
 */
class IMDatabase {

    companion object {
       private val databasesHelper = DatabasesHelper()
        val instance = IMDatabase()
    }

    fun saveContact(contact: Contact) {
        databasesHelper.use {
            //SQLiteDatabase的扩展方法
            insert(ContactTable.NAME, *contact.map.toVarargArray())
        }
    }

    fun getAllContacts(): List<Contact> {
        return databasesHelper.use {
            select(ContactTable.NAME).parseList(object : MapRowParser<Contact> {
                override fun parseRow(columns: Map<String, Any?>): Contact =
                    Contact(columns.toMutableMap())
            })
        }
    }

    fun deleteAllContacts() {
        databasesHelper.use {
            delete(ContactTable.NAME, null, null)
        }
    }
}
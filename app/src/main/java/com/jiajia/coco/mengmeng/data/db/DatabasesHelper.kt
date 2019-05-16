package com.jiajia.coco.mengmeng.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.jiajia.coco.mengmeng.app.IMApplication
import org.jetbrains.anko.db.*

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description 创建数据库
 */
class DatabasesHelper(ctx: Context=IMApplication.instance) :
    ManagedSQLiteOpenHelper(ctx, NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(ContactTable.NAME,true,ContactTable.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
            ContactTable.CONTACT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ContactTable.NAME,true)
        onCreate(db)
    }

    companion object {
        val NAME = "im.db"
        val VERSION =1
    }
}
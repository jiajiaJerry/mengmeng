package com.jiajia.coco.mengmeng.data.db

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description 数据库联系人实体类
 */
data class Contact(val map: MutableMap<String, Any?>) {
    val _id by map
    val name by map
}
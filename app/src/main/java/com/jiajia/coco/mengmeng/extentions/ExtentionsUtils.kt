package com.jiajia.coco.mengmeng.extentions

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description  字符串扩展
 */
fun String.isValidUserName():Boolean=this.matches(Regex("^[a-zA-Z]\\w{2,19}$"))
fun String.isValidPassword():Boolean=this.matches(Regex("^[0-9]{3,20}$"))

fun<K,V> MutableMap<K,V>.toVarargArray():Array<Pair<K,V>> {
    //将MutableMap转换成Pair类型的数组
    return map {
        Pair(it.key, it.value)
    }.toTypedArray()
}
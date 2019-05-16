package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description 添加好友协议
 */
interface AddFriendContract {

    interface Prenter:BasePresenter{
        fun search(key:String)
    }

    interface View{
        fun onSearchSuccess()
        fun onSearchFailed()
    }
}
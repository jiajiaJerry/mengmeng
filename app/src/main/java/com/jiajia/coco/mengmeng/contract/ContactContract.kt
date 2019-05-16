package com.jiajia.coco.mengmeng.contract

/**
 * @author Create by Jerry
 * @date on 2019-05-16
 * @description 联系人协议
 */
interface ContactContract {

    interface Presenter:BasePresenter{
        fun loadContacts()
    }

    interface View{
        fun onLoadContactsSuccess()
        fun onLoadContactsFailed()
    }
}
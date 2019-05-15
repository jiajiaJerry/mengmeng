package com.jiajia.coco.mengmeng.factory

import android.support.v4.app.Fragment
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.ui.fragment.ContactFragment
import com.jiajia.coco.mengmeng.ui.fragment.ConversationFragment
import com.jiajia.coco.mengmeng.ui.fragment.DynamicFragment

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class FragmentFactory private constructor(){

    private val conversation by lazy {
        ConversationFragment()
    }
    private val contact by lazy {
        ContactFragment()
    }
    private val dynamic by lazy {
        DynamicFragment()
    }

    companion object {
        val instance = FragmentFactory()
    }

    fun getFragment(tabId:Int): Fragment? {
        when (tabId) {
            R.id.tab_conversation-> return conversation
            R.id.tab_contacts-> return contact
            R.id.tab_dynamic-> return dynamic
        }
        return null
    }
}
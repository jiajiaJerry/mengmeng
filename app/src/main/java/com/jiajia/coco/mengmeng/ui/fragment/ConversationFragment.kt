package com.jiajia.coco.mengmeng.ui.fragment

import com.jiajia.coco.mengmeng.R
import kotlinx.android.synthetic.main.header.*

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 会话界面
 */
class ConversationFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_conversation

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.message)
    }
}
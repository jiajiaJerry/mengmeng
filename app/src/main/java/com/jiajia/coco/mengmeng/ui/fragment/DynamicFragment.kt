package com.jiajia.coco.mengmeng.ui.fragment

import com.hyphenate.chat.EMClient
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.EMCallBackAdapter
import com.jiajia.coco.mengmeng.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 动态界面
 */
class DynamicFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_dynamic

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.dynamic)

        val logoutString = String.format(getString(R.string.logout), EMClient.getInstance().currentUser)
        logout.text = logoutString
        logout.onClick { logout() }
    }

    private fun logout() {
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
            override fun onSuccess() {
                context!!.runOnUiThread {
                    toast(R.string.logout_success)
                    context!!.startActivity<LoginActivity>()
                    activity!!.finish()
                }
            }

            override fun onError(code: Int, error: String?) {
                context!!.runOnUiThread { toast(R.string.logout_failed) }
            }
        })
    }
}
package com.jiajia.coco.mengmeng.ui.fragment

import android.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hyphenate.chat.EMClient
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.ContactListAdapter
import com.jiajia.coco.mengmeng.adapter.EMCallBackAdapter
import com.jiajia.coco.mengmeng.contract.ContactContract
import com.jiajia.coco.mengmeng.presenter.ContactPresenter
import com.jiajia.coco.mengmeng.ui.activity.AddFriendActivity
import com.jiajia.coco.mengmeng.ui.activity.ChatActivity
import com.jiajia.coco.mengmeng.widget.SlideBar
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 联系人界面
 */
class ContactFragment : BaseFragment(), ContactContract.View {

    override fun getLayoutResId(): Int = R.layout.fragment_contacts

    val presenter = ContactPresenter(this)

    private lateinit var adapter: ContactListAdapter

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE
        add.setOnClickListener { startActivity<AddFriendActivity>() }

        //下拉刷新
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.qq_blue)
            isRefreshing = true
            setOnRefreshListener { presenter.loadContacts() }
        }

        //初始化列表
        recyclerView.apply {
            //长宽固定优化布局
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        adapter = ContactListAdapter(R.layout.view_contact_item, presenter.contactListItems)
        recyclerView.adapter = adapter
        //item点击事件
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<ChatActivity>("userName" to presenter.contactListItems[position].userName)
        }
        adapter.setOnItemLongClickListener { _, _, position ->
            val userName = presenter.contactListItems[position].userName
            val message = String.format(getString(R.string.delete_friend_message), userName)
            AlertDialog.Builder(context)
                .setTitle(R.string.delete_friend_title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    deleteFriend(userName, position)
                }
                .show()
            true
        }

        slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
            override fun onSectionChange(firstLetter: String) {
                section.text = firstLetter
                section.visibility = View.VISIBLE
                recyclerView.smoothScrollToPosition(getPosition(firstLetter))
            }

            override fun onSildFinish() {
                section.visibility = View.GONE

            }

        }

        presenter.loadContacts()
    }


    private fun getPosition(firstLetter: String): Int =
        presenter.contactListItems.binarySearch { contactListItem ->
            contactListItem.firstLetter.minus(firstLetter[0])
        }


    private fun deleteFriend(userName: String?, position: Int) {
        EMClient.getInstance().contactManager().aysncDeleteContact(userName, object : EMCallBackAdapter() {
            override fun onSuccess() {
                adapter.remove(position)
                context?.runOnUiThread { toast(R.string.delete_friend_success) }
            }

            override fun onError(code: Int, error: String?) {
                context?.runOnUiThread { toast(R.string.delete_friend_failed) }
            }
        })
    }

    override fun onLoadContactsSuccess() {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onLoadContactsFailed() {
        swipeRefreshLayout.isRefreshing = false
        context!!.toast(R.string.load_contacts_failed)
    }
}
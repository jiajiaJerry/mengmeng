package com.jiajia.coco.mengmeng.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.widget.TextView
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.AddFriendListAdapter
import com.jiajia.coco.mengmeng.contract.AddFriendContract
import com.jiajia.coco.mengmeng.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class AddFriendActivity : BaseActivity(), AddFriendContract.View {

    override fun getLayoutResId(): Int = R.layout.activity_add_friend

    private val presenter = AddFriendPresenter(this)

    private lateinit var addAdapter: AddFriendListAdapter

    override fun init() {
        headerTitle.text = getString(R.string.add_friend)

        addAdapter = AddFriendListAdapter(R.layout.view_add_friend_item, presenter.addFriendItems)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = addAdapter
        }

        search.onClick { search() }
        userName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                search()
                return true
            }

        })

    }

    private fun search() {
        hideSoftKeyboard()
        showProgress(getString(R.string.searching))
        val key = userName.text.trim().toString()
        presenter.search(key)
    }

    override fun onSearchSuccess() {
        dismissProgress()
        toast(R.string.search_success)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun onSearchFailed() {
        dismissProgress()
        toast(R.string.search_failed)
    }
}

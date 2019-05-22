package com.jiajia.coco.mengmeng.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.ConversationAdapter
import com.jiajia.coco.mengmeng.adapter.EMMessageListenerAdapter
import com.jiajia.coco.mengmeng.ui.activity.ChatActivity
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description 会话界面
 */
class ConversationFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_conversation
    lateinit var adapter: ConversationAdapter
    private val conversations = mutableListOf<EMConversation>()
    private val messageListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            loadConversations()
        }
    }

    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.message)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        adapter = ConversationAdapter(R.layout.view_conversation_item, conversations)
        recyclerView.adapter = adapter

        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                startActivity<ChatActivity>("userName" to conversations[position].conversationId())
            }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    override fun onResume() {
        super.onResume()
        loadConversations()
    }

    private fun loadConversations() {
        doAsync {
            conversations.clear()
            val allConversations = EMClient.getInstance().chatManager().allConversations
            conversations.addAll(allConversations.values)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}
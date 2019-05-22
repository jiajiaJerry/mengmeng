package com.jiajia.coco.mengmeng.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.adapter.ChatListAdapter
import com.jiajia.coco.mengmeng.adapter.EMMessageListenerAdapter
import com.jiajia.coco.mengmeng.contract.ChatContract
import com.jiajia.coco.mengmeng.data.Message
import com.jiajia.coco.mengmeng.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ChatActivity : BaseActivity(), ChatContract.View {

    override fun getLayoutResId(): Int = R.layout.activity_chat

    val present = ChatPresenter(this)
    lateinit var userName: String
    lateinit var adapter: ChatListAdapter

    private val msgListener = object : EMMessageListenerAdapter() {
        override fun onMessageReceived(messages: MutableList<EMMessage>?) {
            messages.let { list ->
                list!!.forEach {
                    val message = Message(it)
                    present.messages.add(message)
                }
                onMessageLoad()
                val conversation = EMClient.getInstance().chatManager().getConversation(userName)
                conversation.markAllMessagesAsRead()

            }
        }
    }

    override fun init() {
        super.init()
        initHeader()
        initEditText()
        initRecyclerView()
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
        send.onClick { send() }
        present.loadMessages(userName)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    //当RecyclerView为空闲状态
                    //检查是否滑到顶部,加载更多数据
                    if (newState==RecyclerView.SCROLL_STATE_IDLE){
                        //如果第一个可见条目的位置是0,为顶部
                        val linearLayoutMessage = layoutManager as LinearLayoutManager
                        if (linearLayoutMessage.findFirstVisibleItemPosition()==0){
                            //加载更多数据
                            present.loadMoreMessage(userName)
                        }
                    }
                }
            })

        }
        adapter = ChatListAdapter(present.messages)
        recyclerView.adapter = adapter
    }

    private fun send() {
        hideSoftKeyboard()
        val message = edit.text.trim().toString()
        if (!TextUtils.isEmpty(message))
            present.sendMessage(userName, message)
    }

    private fun initEditText() {
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //如果用户输入的文本长度大于0,发送按钮enable
                send.isEnabled = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        edit.setOnEditorActionListener { _, _, _ ->
            send()
            true
        }
    }

    private fun initHeader() {
        back.visibility = View.VISIBLE
        back.setOnClickListener { finish() }

        //获取聊天的用户名
        userName = intent.getStringExtra("userName")
        val titleString = String.format(getString(R.string.chat_title), userName)
        headerTitle.text = titleString

    }

    override fun onStartSendMessage() {
        //通知RecyclerView刷新列表
    }

    override fun onSendMessageSuccess() {
        edit.text.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onSendMessageFailed() {
        edit.text.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onMessageLoad() {
        runOnUiThread {
            adapter.notifyDataSetChanged()
            onScrollToBottom()
        }
    }

    override fun onLoadMoreMessage(size:Int) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(size)
        }
    }

    override fun onScrollToBottom() {
        recyclerView.scrollToPosition(present.messages.size - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
    }
}

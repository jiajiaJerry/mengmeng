package com.jiajia.coco.mengmeng.ui.activity

import android.os.Bundle
import com.jiajia.coco.mengmeng.R
import com.jiajia.coco.mengmeng.contract.RegisterContract
import com.jiajia.coco.mengmeng.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity() ,RegisterContract.View{

    private val registerPresenter = RegisterPresenter(this)

    override fun init() {
        register.onClick { register() }
        confirmPassword.setOnEditorActionListener { _, _, _ ->
            register()
            true
        }
    }

    private fun register() {
        hideSoftKeyboard()
        val userName = userName.text.trim().toString()
        val password = password.text.trim().toString()
        val confirmPassword = confirmPassword.text.trim().toString()
        registerPresenter.register(userName,password,confirmPassword)
    }

    override fun onUserNameError() {
        userName.error=getString(R.string.user_name_error)
    }

    override fun onPasswordError() {
        password.error=getString(R.string.password_error)
    }

    override fun onConfirmPasswordError() {
        confirmPassword.error=getString(R.string.confirm_password_error)
    }

    override fun onStartRegister() {
        showProgress(getString(R.string.registering))
    }

    override fun onRegisterSuccess() {
        dismissProgress()
        finish()
    }

    override fun onRegisterFailed() {
        dismissProgress()
        toast(R.string.register_failed)
    }

    override fun onUserExist() {
        dismissProgress()
        toast(R.string.user_already_exist)
    }

    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

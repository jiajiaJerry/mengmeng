package com.jiajia.coco.mengmeng

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.jiajia.coco.mengmeng.contract.LoginContract
import com.jiajia.coco.mengmeng.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class LoginActivity : BaseActivity(), LoginContract.View {

    private val loginPresenter = LoginPresenter(this)

    override fun init() {
        login.setOnClickListener { login() }
        password.setOnEditorActionListener { v, actionId, event ->
            login()
            true
        }

    }

    private fun login() {
        hideSoftKeyboard()
        if (hasWriteExternalStoragePermission()) {
            val userNameString = userName.text.trim().toString()
            val passwordString = password.text.trim().toString()
            loginPresenter.login(userNameString, passwordString)
        }else applyWriteExteranlStoragePermission()
    }

    //申请数据库写入权限
    private fun applyWriteExteranlStoragePermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissions, 0)

    }

    //查看是否有数据库写入权限
    private fun hasWriteExternalStoragePermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    //权限请求回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            login()
        }else toast(R.string.permission_denied)
    }

    override fun onUserNameError() {
        userName.error = getString(R.string.user_name_error)
    }

    override fun onPasswrodError() {
        password.error = getString(R.string.password_error)
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgress(getString(R.string.logging))
    }

    override fun onLoggedInSuccess() {
        dismissProgress()
        startActivity<MainActivity>()
        finish()
    }

    override fun onLoggedInFailed() {
        dismissProgress()
        toast(R.string.login_failed)
    }

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

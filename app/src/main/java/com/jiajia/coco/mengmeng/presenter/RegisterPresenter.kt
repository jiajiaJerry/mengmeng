package com.jiajia.coco.mengmeng.presenter

import com.jiajia.coco.mengmeng.contract.RegisterContract
import com.jiajia.coco.mengmeng.extentions.isValidPassword
import com.jiajia.coco.mengmeng.extentions.isValidUserName

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class RegisterPresenter(private val view:RegisterContract.View):RegisterContract.Persenter {
    override fun register(userName: String, password: String, confirmPassword: String) {
        if (userName.isValidUserName()) {//检查用户名
            if (password.isValidPassword()){//检查密码
                if (confirmPassword.isValidPassword()){//检查确认密码
                    view.onStartRegister()//检查一致

                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }
}
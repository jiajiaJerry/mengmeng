package com.jiajia.coco.mengmeng

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

/**
 * @author Create by Jerry
 * @date on 2019-05-13
 * @description 主页
 */
class MainActivity : AppCompatActivity() {
    private val mWebView by lazy {
        webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWebView()
    }

    //lambad表达式
    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    var setWebView = {
        //1.开启Kotlin与H5通信开关
        mWebView.settings.javaScriptEnabled=true
        mWebView.webViewClient = MyWebViewClient()
        mWebView.webChromeClient=MyWebChromeClient()

        //Kotlin与H5通信方式1:H5调用Kotlin方法
        //设置Kotlin与H5通信桥梁类
        mWebView.addJavascriptInterface(JavaScriptMethods(this),"jsInterface")

        //加载网页
        mWebView.loadUrl("http://192.168.31.34:8080/WEB22/index.html")


    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //调用H5的方法
//            mWebView.loadUrl("javascript:方法名(参数)")
            val json = JSONObject()
            json.put("name","Kotlin")
            mWebView.loadUrl("javascript:showMessage($json)")
        }
    }

    private class MyWebChromeClient:WebChromeClient(){
        //加载进度条
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

}

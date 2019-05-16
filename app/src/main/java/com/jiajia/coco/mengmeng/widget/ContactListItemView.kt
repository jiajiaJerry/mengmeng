package com.jiajia.coco.mengmeng.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.jiajia.coco.mengmeng.R
import org.jetbrains.anko.themedImageSwitcher

/**
 * @author Create by Jerry
 * @date on 2019-05-15
 * @description
 */
class ContactListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.view_contact_item, themedImageSwitcher())
    }
}
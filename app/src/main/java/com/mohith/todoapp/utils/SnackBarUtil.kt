package com.mohith.todoapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.mohith.todoapp.R

fun showSnack(view: View,text:String,backgroundColor:Int?,textColor:Int?,anchorView:View?){
    val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    if (backgroundColor != null) {
        snackbar.setBackgroundTint(backgroundColor)
    }
    if (textColor != null) {
        snackbar.setTextColor(textColor)
    }
    if(anchorView!=null){
        snackbar.anchorView = anchorView
    }
    snackbar.show()
}
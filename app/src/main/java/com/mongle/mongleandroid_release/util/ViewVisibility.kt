package com.mongle.mongleandroid_release

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout

fun change_visible(view : View) {
    view.visibility = VISIBLE
}

fun change_gone(view : View) {
    view.visibility = GONE
}

fun goNextPage(current : ConstraintLayout, next : ConstraintLayout){
    current.visibility = View.GONE
    next.visibility = View.VISIBLE
}
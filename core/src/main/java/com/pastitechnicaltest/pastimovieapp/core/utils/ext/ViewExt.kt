package com.pastitechnicaltest.pastimovieapp.core.utils.ext

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pastitechnicaltest.pastimovieapp.core.BuildConfig

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.setImageFromUrl(url: String) {
    Glide.with(this.context)
        .load(BuildConfig.IMAGE_BASE_URL + url)
        .centerInside()
        .into(this)
}
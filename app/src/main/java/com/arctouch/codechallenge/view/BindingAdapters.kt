package com.arctouch.codechallenge.view

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder", "centerCrop")
    fun ImageView.loadImage(url: String, placeholder: Drawable?, centerCrop: Boolean = false) {
        val options = RequestOptions().placeholder(placeholder)
        if (centerCrop) options.centerCrop()
        Glide.with(context).load(url).apply(options).into(this)
    }

    @JvmStatic
    @BindingAdapter("showVisible")
    fun View.showVisible(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}
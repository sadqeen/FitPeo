package com.example.fitpeo.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fitpeo.R

object CustomBindingAdapter {

    @JvmStatic
    @BindingAdapter("bindImage")
    fun bindImage(view: ImageView, url: String?) {
        if (!url.isNullOrBlank() && url.isNotEmpty()) {
            PicassoInitializer.get()
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .fit()
                .tag(view.context)
                .into(view)
        } else {
            view.setImageResource(R.drawable.ic_error)
        }


    }
}
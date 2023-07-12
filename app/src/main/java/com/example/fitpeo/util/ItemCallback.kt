package com.example.fitpeo.util

import android.widget.ImageView
import com.example.fitpeo.model.ResponseData

interface ItemCallback {
    fun pictureClicked(vdata:ResponseData)
}
package com.example.fitpeo.util

import com.example.fitpeo.model.ResponseData

object ValidationUtils {

    fun validateUrl(data:ResponseData) : Boolean {
        if (data.url?.isNotEmpty() == true) {
            return true
        }
        return false
    }

    fun validateThumbnail(data:ResponseData) : Boolean {
        if (data.thumbnailUrl?.isNotEmpty() == true) {
            return true
        }
        return false
    }
}
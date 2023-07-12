package com.example.fitpeo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseData(@SerializedName("albumId"      ) var albumId      : Int?    = null,
                        @SerializedName("id"           ) var id           : Int?    = null,
                        @SerializedName("title"        ) var title        : String? = null,
                        @SerializedName("url"          ) var url          : String? = null,
                        @SerializedName("thumbnailUrl" ) var thumbnailUrl : String? = null,
var desc:String?=""):Parcelable {
}
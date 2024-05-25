package com.ersiver.test_krunal.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommanResponseModel<T>(
    @SerializedName("PostOffice")
    val data:ArrayList<T> = ArrayList(),
    @SerializedName("Message")
    val message:String?,
    @SerializedName("Status")
    val status:String?): Serializable

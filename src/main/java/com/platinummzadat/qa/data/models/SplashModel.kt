package com.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by WOLF
 * at 16:39 on Monday 10 June 2019
 */
data class SplashModel(
    @SerializedName("status") val status:Int
 //   @SerializedName("items") val items: ArrayList<AuctionItemModel>

) {
}

data class DDD(
    @SerializedName("data")
    val `data`: SplashModelData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class SplashModelData(
    @SerializedName("status")
    val status: Int
)
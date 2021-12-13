package com.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by WOLF
 * at 10:32 on Tuesday 02 April 2019
 */
data class DashboardItemModel1(
    @SerializedName("id") val id: Int,
    @SerializedName("category") val name: String,
    @SerializedName("auction_count") val count: Int,
    @SerializedName("image") val imgUrl: String
)
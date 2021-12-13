package com.platinummzadat.qa.data.models

import com.google.gson.annotations.SerializedName
import com.platinummzadat.qa.R

/**
 * Created by WOLF
 * at 14:23 on Wednesday 24 April 2019
 */
data class DepositDetailsModel(
    @SerializedName("date") val date: String,
    @SerializedName("mode") val remarks: String,
    @SerializedName("refund_button") val refund_button: String,
    @SerializedName("deposit_id") val deposit_id: String,

    @SerializedName("amount") val amount: String,
    @SerializedName("payment_type") val paymentType: Int
) {
    val textColor: Int
        get() = when (paymentType) {
            1 -> R.color.colorDepositAmountRed
            else -> R.color.colorDepositAmountBlack
        }
}
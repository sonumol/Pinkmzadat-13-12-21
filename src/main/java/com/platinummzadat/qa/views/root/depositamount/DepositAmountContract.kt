package com.platinummzadat.qa.views.root.depositamount

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.AmountData
import com.platinummzadat.qa.data.models.DepositModel
import com.platinummzadat.qa.data.models.RefundRequestRes

interface DepositAmountContract {
    interface View : BaseView<Presenter> {
        fun showData(data: DepositModel)
        fun showRefundData(data: RefundRequestRes)
        fun showAmountData(data: AmountData)
    }


    interface Presenter :BasePresenter {
        fun fetchDepositAmount()
        fun getAmount()
        fun getRefundRequest()
    }
}
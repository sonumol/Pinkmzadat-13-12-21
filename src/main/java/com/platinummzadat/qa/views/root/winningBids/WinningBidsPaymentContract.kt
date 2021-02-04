package com.platinummzadat.qa.views.root.companyfees

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface WinningBidsPaymentContract {
    interface View : BaseView<Presenter> {
        fun showData(data: String)
    }
    interface Presenter : BasePresenter {
        fun winningBidsPayment(bidId: Int)
    }
}
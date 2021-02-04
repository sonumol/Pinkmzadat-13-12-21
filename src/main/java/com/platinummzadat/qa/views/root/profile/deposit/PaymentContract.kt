package com.platinummzadat.qa.views.root.profile.deposit

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface PaymentContract {
    interface View : BaseView<Presenter> {
        fun showData(data: String)
    }
    interface Presenter : BasePresenter {
        fun doPayment(amount: String)
    }
}
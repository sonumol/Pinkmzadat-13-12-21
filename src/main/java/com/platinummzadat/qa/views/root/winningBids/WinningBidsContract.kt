package com.platinummzadat.qa.views.root.companyfees

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.WinningBidsDetails

interface WinningBidsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ArrayList<WinningBidsDetails>)
    }
    interface Presenter :BasePresenter {
        fun winningBids()
    }
}
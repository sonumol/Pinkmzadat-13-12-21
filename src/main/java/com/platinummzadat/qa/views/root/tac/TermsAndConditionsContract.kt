package com.platinummzadat.qa.views.root.tac

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.TacModel

interface TermsAndConditionsContract {
    interface View : BaseView<Presenter> {
        fun showTac(data: TacModel)
    }
    interface Presenter : BasePresenter {
        fun fetchTac()
    }
}
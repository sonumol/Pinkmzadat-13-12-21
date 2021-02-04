package com.platinummzadat.qa.views.root

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface RootContract {
    interface View : BaseView<Presenter> {
        fun lastActiveUpdated()
    }
    interface Presenter : BasePresenter {
        fun updateLastActive()
    }
}
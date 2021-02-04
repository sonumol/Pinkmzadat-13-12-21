package com.platinummzadat.qa.views.splash

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.SplashModel

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun showSuccess(data: SplashModel)
        fun showBlocked()
    }
    interface Presenter : BasePresenter {
        fun splash()
    }
}
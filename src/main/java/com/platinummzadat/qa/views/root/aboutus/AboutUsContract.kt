package com.platinummzadat.qa.views.root.aboutus

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.AboutUsModel

interface AboutUsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: AboutUsModel)
    }

    interface Presenter : BasePresenter {
        fun fetchAboutUs()
    }
}
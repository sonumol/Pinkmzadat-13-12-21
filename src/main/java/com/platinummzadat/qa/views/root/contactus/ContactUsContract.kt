package com.platinummzadat.qa.views.root.contactus

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.ContactUsModel

interface ContactUsContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ContactUsModel)
    }

    interface Presenter : BasePresenter {
        fun fetchContactUs()
    }
}
package com.platinummzadat.qa.views.root.faq

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.FaqRes

interface FAQContract {
    interface View : BaseView<Presenter> {
        fun showData(data: FaqRes)
    }

    interface Presenter : BasePresenter {
        fun fetchFaq()
    }
}
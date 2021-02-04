package com.platinummzadat.qa.views

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface Contract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter {

    }
}
package com.platinummzadat.qa.views.root.companyfees

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.CompanyFeesData
import com.platinummzadat.qa.data.models.CompanyFeesModel
import com.platinummzadat.qa.data.models.CompanyFeesRespose

interface CompanyFeesContract {
    interface View : BaseView<Presenter> {
        fun showData(data: CompanyFeesRespose)
    }
    interface Presenter : BasePresenter {
        fun fetchFees()
    }
}
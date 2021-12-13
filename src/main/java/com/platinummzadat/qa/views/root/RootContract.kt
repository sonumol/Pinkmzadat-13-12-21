package com.platinummzadat.qa.views.root

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.DashboardModel

interface RootContract {
    interface View : BaseView<Presenter> {
        fun lastActiveUpdated()
        fun showDashboard(data: DashboardModel)

    }
    interface Presenter : BasePresenter {
        fun updateLastActive()
        fun fetchDashboard()
    }
}
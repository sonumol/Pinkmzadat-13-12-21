package com.platinummzadat.qa.views.root.dashboard

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.DashboardModel
import com.platinummzadat.qa.data.models.FavAutionIdsRes

interface DashboardContract {
    interface View : BaseView<Presenter> {
        fun showDashboard(data:DashboardModel)
        fun showFavIds(data: FavAutionIdsRes)
    }
    interface Presenter : BasePresenter {
        fun fetchDashboard()
        fun fetchFavAutionsIDs()
    }
}
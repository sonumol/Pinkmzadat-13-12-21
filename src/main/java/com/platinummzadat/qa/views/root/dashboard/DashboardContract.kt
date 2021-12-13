package com.platinummzadat.qa.views.root.dashboard

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.DashboardItemModel1
import com.platinummzadat.qa.data.models.DashboardModel
import com.platinummzadat.qa.data.models.FavAutionIdsRes
import com.platinummzadat.qa.data.models.NotificationModel


interface DashboardContract {
    interface View : BaseView<Presenter> {
        fun showDashboard(data: DashboardModel)
        fun showFavIds(data: FavAutionIdsRes)
        fun lastActiveUpdated()
//        fun showData(dataSet: ArrayList<DashboardItemModel1>)
    }
    interface Presenter : BasePresenter {
        fun fetchDashboard(type: Int)
//        fun category_list(type: Int)
        fun fetchFavAutionsIDs()
        fun updateLastActive()
    }
}
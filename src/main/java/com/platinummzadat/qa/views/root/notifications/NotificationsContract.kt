package com.platinummzadat.qa.views.root.notifications

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.NotificationModel

interface NotificationsContract {
    interface View : BaseView<Presenter> {
        fun showData(dataSet: ArrayList<NotificationModel>)
    }
    interface Presenter : BasePresenter {
        fun fetchNotifications()
    }
}
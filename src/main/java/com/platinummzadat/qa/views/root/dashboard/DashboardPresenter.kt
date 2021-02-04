package com.platinummzadat.qa.views.root.dashboard

import com.platinummzadat.qa.MApp.Companion.MzRepo
import com.platinummzadat.qa.currentUserId
import com.platinummzadat.qa.firebaseId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:19 on Tuesday 02 April 2019
 */
class DashboardPresenter(private val view: DashboardContract.View) :
    DashboardContract.Presenter {

    init {
        view.presenter = this
    }

    override fun fetchDashboard() {
        view.showLoading()
        MzRepo.fetchDashboard(firebaseId) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showDashboard(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

    override fun fetchFavAutionsIDs() {
        view.showLoading()
        MzRepo.fetchFavAutionsIDs() { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showEmptyData()
                }
                status -> {
                    view.showFavIds(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}
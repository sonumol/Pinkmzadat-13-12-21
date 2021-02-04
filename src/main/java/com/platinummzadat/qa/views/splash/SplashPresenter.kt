package com.platinummzadat.qa.views.splash

import com.platinummzadat.qa.MApp.Companion.MzRepo
import com.platinummzadat.qa.currentUserId
import com.platinummzadat.qa.firebaseId
import com.platinummzadat.qa.trendingSearch
import raj.nishin.wolfpack.gson
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:08 on Tuesday 14 May 2019
 */
class SplashPresenter(private val view:SplashContract.View):SplashContract.Presenter {
    init {
        view.presenter =this
    }

    override fun splash() {
        view.showLoading()
        MzRepo.splash(currentUserId, firebaseId){status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                error == ERROR.BLOCKED_USER -> {
                    view.showBlocked()
                }
                null == data->{
                    view.showApiError()
                }
                status -> {
                  //  trendingSearch = gson.toJson(data.items.map { it.name })
                    view.showSuccess(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}
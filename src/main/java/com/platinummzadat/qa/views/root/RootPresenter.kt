package com.platinummzadat.qa.views.root

import com.platinummzadat.qa.MApp.Companion.MzRepo
import com.platinummzadat.qa.currentUserId

/**
 * Created by WOLF
 * at 16:04 on Saturday 20 April 2019
 */
class RootPresenter(private val view: RootContract.View) : RootContract.Presenter {
    init {
        view.presenter = this
    }

    override fun updateLastActive() {
        view.showLoading()
        MzRepo.updateLastActive(currentUserId){status, error ->
            view.hideLoading()
            view.lastActiveUpdated()
        }
    }
}
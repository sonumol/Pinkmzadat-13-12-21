package com.platinummzadat.qa.views.registration.password

import com.platinummzadat.qa.MApp.Companion.MzRepo
import com.platinummzadat.qa.currentUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:33 on Friday 19 April 2019
 */
class PasswordPresenter(private val view: PasswordContract.View) : PasswordContract.Presenter {
    init {
        view.presenter = this
    }

    override fun updateProfile(name: String, email: String, password: String) {
        view.showLoading()
        MzRepo.updateProfile(currentUserId, name, email, password) { status, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.DUPLICATE_DETAILS -> {
                    view.showDuplicate()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    view.showSuccess()
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

    override fun setPassword(password: String) {
        view.showLoading()
        MzRepo.updatePassword(currentUserId, password) { status, error ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                status -> {
                    view.showSuccess()
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }
}
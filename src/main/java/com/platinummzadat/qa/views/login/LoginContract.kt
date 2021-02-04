package com.platinummzadat.qa.views.login

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showInvalidCredentials()
        fun showBlockedUser(messageResId: Int, append: String)
    }

    interface Presenter : BasePresenter {
        fun authenticate(
            qatarId: String,
            phone: String,
            hash: String
        )
    }
}
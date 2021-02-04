package com.platinummzadat.qa.views.registration.password

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface PasswordContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showDuplicate()
    }

    interface Presenter : BasePresenter {
        fun setPassword(password: String)
        fun updateProfile(name: String, email: String, password: String)
    }
}
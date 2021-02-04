package com.platinummzadat.qa.views.registration.profilephoto

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface ProfilePhotoContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
    }
    interface Presenter : BasePresenter {
        fun uploadPhoto(path:String)
    }
}
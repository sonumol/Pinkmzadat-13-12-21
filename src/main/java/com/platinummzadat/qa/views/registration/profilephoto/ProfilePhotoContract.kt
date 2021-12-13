package com.platinummzadat.qa.views.registration.profilephoto

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.ProfileModel

interface ProfilePhotoContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showData(data: ProfileModel)
    }
    interface Presenter : BasePresenter {
        fun uploadPhoto(path:String)
        fun fetchProfile()
    }
}
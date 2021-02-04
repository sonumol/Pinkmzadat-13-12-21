package com.platinummzadat.qa.views.root.profile

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.AmountData
import com.platinummzadat.qa.data.models.ProfileModel

interface ProfileContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ProfileModel)
        fun showDataAmount(data: AmountData)
        fun showUploadingQid()
        fun hideUploadingQid()
        fun showUploadedQid(side:String)
        fun showUploadQidFailed(side:String)
        fun showUploadedProfilePhoto()
        fun showUploadProfilePhotoFailed()
    }

    interface Presenter : BasePresenter {
        fun fetchProfile()
        fun getAmount()
//        fun uploadQid(path:String)
    }
}
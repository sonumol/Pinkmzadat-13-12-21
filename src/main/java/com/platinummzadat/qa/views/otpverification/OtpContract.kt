package com.platinummzadat.qa.views.otpverification

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView

interface OtpContract {
    interface View : BaseView<Presenter> {
        fun showSuccessLogin( data : String)
        fun showSuccessRegister()
        fun showTimeError()
        fun showCompanyCheck()
        fun showRegisterAndCompanyCheck()
        fun showSuccessLoginWithCompany()
        fun showWrongOTP()
        fun showOTPExpire()
        fun showBlockedUser(messageResId: Int, append: String)
        fun showFailed()
    }

    interface Presenter :BasePresenter {
        fun verifyOtp(otp: String,mobile:String)
        fun resendOtp(hash:String,mobile: String)
    }
}
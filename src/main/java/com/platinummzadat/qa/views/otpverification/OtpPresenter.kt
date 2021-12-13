package com.platinummzadat.qa.views.otpverification

import com.platinummzadat.qa.*
import com.platinummzadat.qa.MApp.Companion.MzRepo
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_BLOCKED
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_INVALID
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_LOGIN
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_MORE
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_MORE_EXPIRE
import com.platinummzadat.qa.data.MzadatRepository.Companion.OTP_TYPE_REGISTER
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 15:49 on Wednesday 17 April 2019
 */
class OtpPresenter(private val view: OtpContract.View) : OtpContract.Presenter {
    init {
        view.presenter = this
    }

    override fun resendOtp(hash: String, mobile: String) {
        MzRepo.resendOtp(mobile, hash) { status, error ->

        }
    }

    override fun verifyOtp(otp: String, mobile: String) {
        view.showLoading()
        MzRepo.verifyOtp(mobile, otp, firebaseId) { message, data, error, status ->
            view.hideLoading()
            when {
                error == ERROR.API_ERROR -> {
                    view.showApiError()
                }
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                OTP_TYPE_INVALID==message -> {
                    view.showWrongOTP()
                }

               status->{

                   if(accountType=="Personal" || accountType=="شخصي") {
                       when (message) {
                           OTP_TYPE_LOGIN -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLogin(data)
                           }
                           OTP_TYPE_MORE -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLogin( data)
                           }
                           OTP_TYPE_REGISTER -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessRegister()
                           }
                       }

                   }else{
                       when (message) {
                           OTP_TYPE_LOGIN -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showSuccessLoginWithCompany()
                           }
                           OTP_TYPE_MORE -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showCompanyCheck()
                           }
                           OTP_TYPE_REGISTER -> {
                               currentUserId = tempUserId
                               tempUserId = -1
                               mToken=data
                               view.showRegisterAndCompanyCheck()
                           }
                       }
                   }
                }

                !status ->{
                   if(OTP_TYPE_MORE == message) {
                        view.showTimeError()
                   }
                }

                OTP_TYPE_MORE_EXPIRE == message -> {
                    view.showOTPExpire()
                }
                OTP_TYPE_BLOCKED == message-> {
                    view.showBlockedUser(R.string.account_blocked, "")

                }
            }
        }
    }

}
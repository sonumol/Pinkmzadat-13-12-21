package com.platinummzadat.qa.views.otpverification

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.platinummzadat.qa.*
import com.platinummzadat.qa.views.registration.password.PasswordActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.platinummzadat.qa.views.companyregister.RegisterAsCompanyActivity
import kotlinx.android.synthetic.main.activity_otp_verification.*
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*
import java.util.regex.Pattern
import kotlin.system.exitProcess

class OtpVerificationActivity : MzActivity(), OtpContract.View, MySMSBroadcastReceiver.OTPReceiveListener {
    override lateinit var presenter: OtpContract.Presenter
   // val smsBroadcastListener = MySMSBroadcastReceiver()
    private lateinit var progress: ProgressDialog
    var mobile=""
    private var mTracker: Tracker?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        OtpPresenter(this)
        mobile=intent.getStringExtra("mobile")
        progress = getProgressDialog()
        tvResendCode.onClick {
            presenter.resendOtp(AppSignatureHelper(this@OtpVerificationActivity).appSignatures[0], mobile)
            toast(getString(R.string.otp_resend2))
        }
        etOtp1.afterTextChanged {
            if (it.isNotEmpty()) {
                etOtp2.requestFocus()
            }
        }

        etOtp2.afterTextChanged {
            if (it.isNotEmpty()) {
                etOtp3.requestFocus()
            }
        }

        etOtp3.afterTextChanged {
            if (it.isNotEmpty()) {
                etOtp4.requestFocus()
            }
        }

        clVerify.onClick {
            verifyOtp()
        }
        startSmsListener()

        val application=application as MApp
        mTracker=application.getDefaultTracker()
        mTracker!!.send(
            HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build()
        )
//
    }
    override fun onResume() {
        super.onResume()

        mTracker!!.setScreenName("Image~" + "OtpVerificationActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }

    private fun verifyOtp() {
        if (etOtp1.value.isEmpty() || etOtp1.value.isEmpty() || etOtp1.value.isEmpty() || etOtp1.value.isEmpty()) {
            toast(getString(R.string.enter_4_digit_otp))
        } else {
            presenter.verifyOtp("${etOtp1.value}${etOtp2.value}${etOtp3.value}${etOtp4.value}", mobile)
        }
    }

    override fun onOTPReceived(sms: String) {
        val otp = parseCode(sms).toCharArray()
        etOtp1.setText("${otp[0]}")
        etOtp2.setText("${otp[1]}")
        etOtp3.setText("${otp[2]}")
        etOtp4.setText("${otp[3]}")
        verifyOtp()
    }

    private fun parseCode(message: String): String {
        val p = Pattern.compile("\\b\\d{4}\\b")
        val m = p.matcher(message)
        var code = ""
        while (m.find()) {
            code = m.group(0)
        }
        return code
    }

    override fun onOTPTimeOut() {

    }

    private fun startSmsListener() {
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
//        task.addOnSuccessListener {
//            Log.e(this@OtpVerificationActivity::class.java.simpleName, "SmsRetrieverStarted")
//            smsBroadcastListener.initOTPListener(this)
//            registerReceiver(smsBroadcastListener, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
//        }

        task.addOnFailureListener {
            Log.e(this@OtpVerificationActivity::class.java.simpleName, "SmsRetriever-FAILED-TO-Start")
        }
    }

    override fun showSuccessLogin( data :String) {

        val intent = Intent(this@OtpVerificationActivity, RootActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        this.finish()
        Log.d("hah",data)



    }

    override fun showSuccessRegister() {
        val intent = Intent(this@OtpVerificationActivity, PasswordActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        this.finish()
    }

    override fun showTimeError() {
        alert(Appcompat, resources.getString(R.string.three_more_time_msg), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }

    override fun showCompanyCheck() {
        val intent = Intent(this@OtpVerificationActivity, RegisterAsCompanyActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("toPath","company")
        startActivity(intent)
        this.finish()
    }

    override fun showRegisterAndCompanyCheck() {
        val intent = Intent(this@OtpVerificationActivity, RegisterAsCompanyActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("toPath","company")
        startActivity(intent)
        this.finish()
    }

    override fun showSuccessLoginWithCompany() {
        val intent = Intent(this@OtpVerificationActivity, RootActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        this.finish()
    }

    override fun showWrongOTP() {
        alert(Appcompat, resources.getString(R.string.incorrect_otp_please_try_again), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }

    override fun showOTPExpire() {
        alert(Appcompat, resources.getString(R.string.otp_expire_message), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }

    override fun showFailed() {
        root.longSnackbar(getString(R.string.incorrect_otp_please_try_again), getString(R.string.retry)) {
            verifyOtp()
        }
    }

    override fun showBlockedUser(messageResId: Int, append: String) {
        alert(Appcompat, getString(messageResId, append), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }


    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        etOtp1.lock()
        etOtp1.lock()
        etOtp1.lock()
        etOtp1.lock()
        clVerify.lock()
    }

    override fun hideLoading() {
        progress.hide()
        etOtp1.unlock()
        etOtp1.unlock()
        etOtp1.unlock()
        etOtp1.unlock()
        clVerify.unlock()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred_try_again), getString(R.string.retry)) {
            verifyOtp()
        }
    }

    override fun sessionTimeOut() {
    }

    override fun onDestroy() {
       // unregisterReceiver(smsBroadcastListener)
        super.onDestroy()
    }

    var backPressTime = 0L
    override fun onBackPressed() {
        if (currentLocalTimeInMillis < backPressTime + 500) {
            super.onBackPressed()
        } else {
            backPressTime = currentLocalTimeInMillis
            toast(getString(R.string.press_back_again_to_exit))
            finishAffinity()
            exitProcess(0)
        }
    }
}

package com.platinummzadat.qa.views.login

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.platinummzadat.qa.*
import com.platinummzadat.qa.views.otpverification.OtpVerificationActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.currentLocalTimeInMillis
import raj.nishin.wolfpack.lock
import raj.nishin.wolfpack.unlock
import raj.nishin.wolfpack.value

class LoginActivity : MzActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter
    private lateinit var progress: ProgressDialog
    lateinit var radioButton: RadioButton
    var radioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginPresenter(this)
        radioGroup = findViewById(R.id.radioGroupType)

        tvSkip.onClick {


            startActivity<RootActivity>()
            finish()
        }
        clSubmit.onClick {
            authenticate()
        }
        progress = getProgressDialog()
        tvTac?.onClick {
            startActivity<TermsOfServiceActivity>()
        }
    }

    private fun authenticate() {
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
        when {
            etQatarId.value.isEmpty() -> {
                etQatarId.requestFocus()
                etQatarId.errorShake { }
            }
            etMobileNumber.value.isEmpty() -> {
                etMobileNumber.errorShake { }
                etMobileNumber.requestFocus()
            }
            !cbAccept.isChecked->{
                llTac.errorShake {  }
                toast(getString(R.string.accept_terms_and_conditions))
            }
            else -> {
                accountType=radioButton.text.toString()
                presenter.authenticate(etQatarId.value, etMobileNumber.value, AppSignatureHelper(this).appSignatures[0])
            }
        }
    }

    override fun showSuccess() {
        startActivity<OtpVerificationActivity>("mobile" to etMobileNumber.value)
        finish()
    }

    override fun showInvalidCredentials() {
//        etQatarId.error = getString(R.string.invalid_credentials)
//        etQatarId.errorShake { }
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        alert(Appcompat, getString(R.string.invalid_credentials), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
     //   showBlockedUser(R.string.invalid_credentials2, "")
    }

    override fun showBlockedUser(messageResId: Int, append: String) {
        etMobileNumber.text = null
        etQatarId.text = null
        alert(Appcompat, getString(messageResId, append), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }

//    override fun showBlockedUser(message: String) {
//        etMobileNumber.text = null
//        etQatarId.text = null
//        alert(Appcompat, message, getString(R.string.failed)) {
//            positiveButton(getString(R.string.ok)) { it.dismiss() }
//        }.show()
//    }


    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        etQatarId.lock()
        etMobileNumber.lock()
    }

    override fun hideLoading() {
        progress.hide()
        etQatarId.unlock()
        etMobileNumber.unlock()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            authenticate()
        }
    }

    override fun sessionTimeOut() {
    }

    var backPressTime = 0L
    override fun onBackPressed() {
        if (currentLocalTimeInMillis < backPressTime + 500) {
            super.onBackPressed()
        } else {
            backPressTime = currentLocalTimeInMillis
            toast(getString(R.string.press_back_again_to_exit))
        }
    }
}

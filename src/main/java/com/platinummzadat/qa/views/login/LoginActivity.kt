package com.platinummzadat.qa.views.login

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.platinummzadat.qa.*
import com.platinummzadat.qa.views.otpverification.OtpVerificationActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
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
import kotlin.system.exitProcess

class LoginActivity : MzActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter
    private lateinit var progress: ProgressDialog
    lateinit var radioButton: RadioButton
    var radioGroup: RadioGroup? = null
    private val sharedPrefFile = "kotlinsharedpreference"
    private var mTracker: Tracker?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this)
        radioGroup = findViewById(R.id.radioGroupType)


        radioPersonal.onClick {
            // Toast.makeText(baseContext, radioPersonal.text, Toast.LENGTH_SHORT).show()
            etcrnumber.setVisibility(View.GONE)
            etMobileNumber1.setVisibility(View.GONE)
            etQatarId.setVisibility(View.VISIBLE)
            etMobileNumber.setVisibility(View.VISIBLE)
        }
        radioCompany.onClick {
            etcrnumber.setVisibility(View.VISIBLE)
            etMobileNumber1.setVisibility(View.VISIBLE)
            etQatarId.setVisibility(View.GONE)
            etMobileNumber.setVisibility(View.GONE)
            // Toast.makeText(baseContext, radioCompany.text, Toast.LENGTH_SHORT).show()
        }



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

        mTracker!!.setScreenName("Image~" + "LoginActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }
    private fun authenticate() {
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        val name:String =radioButton.text.toString()
        editor.putString("id_key", name)
        editor.apply()
        editor.commit()
//        Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
//        when {
//            etQatarId.value.isEmpty() -> {
//                etQatarId.requestFocus()
//                etQatarId.errorShake { }
//            }
//            etMobileNumber.value.isEmpty() -> {
//                etMobileNumber.errorShake { }
//                etMobileNumber.requestFocus()
//            }
//            !cbAccept.isChecked->{
//                llTac.errorShake {  }
//                toast(getString(R.string.accept_terms_and_conditions))
//            }
//            else -> {
//                accountType=radioButton.text.toString()
//                presenter.authenticate(etQatarId.value, etMobileNumber.value, AppSignatureHelper(this).appSignatures[0])
//            }
//        }


        if(name=="Personal" || name=="شخصي")
        {
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
                    presenter.authenticate(etQatarId.value, etMobileNumber.value,etcrnumber.value, AppSignatureHelper(this).appSignatures[0])
                }
            }
        }
        else{
            when {
                etcrnumber.value.isEmpty() -> {
                    etcrnumber.requestFocus()
                    etcrnumber.errorShake { }
                }
                etMobileNumber1.value.isEmpty() -> {
                    etMobileNumber1.errorShake { }
                    etMobileNumber1.requestFocus()
                }
                !cbAccept.isChecked->{
                    llTac.errorShake {  }
                    toast(getString(R.string.accept_terms_and_conditions))
                }
                else -> {
                    val sharedPreferences: SharedPreferences= this.getSharedPreferences("crnumber",
                        Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    val name:String =radioButton.text.toString()
                    editor.putString("ER number", etcrnumber.value)
                    editor.apply()
                    editor.commit()
                    accountType=radioButton.text.toString()

                    presenter.authenticate(etQatarId.value, etMobileNumber1.value,etcrnumber.value, AppSignatureHelper(this).appSignatures[0])
                    // Toast.makeText(applicationContext,""+etMobileNumber1.value ,Toast.LENGTH_LONG).show()
                }
            }
        }






    }

    override fun showSuccess() {
        val sharedPreferences: SharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
//        val sharedIdValue = sharedPreferences.getInt("id_key","")
        val sharedNameValue = sharedPreferences.getString("id_key","")
        //Toast.makeText(applicationContext,""+sharedNameValue ,Toast.LENGTH_LONG).show()
        if( sharedNameValue=="Personal" ||  sharedNameValue=="شخصي")
        {
            startActivity<OtpVerificationActivity>("mobile" to etMobileNumber.value)
            finish()
        }
        else
        {

            startActivity<OtpVerificationActivity>("mobile" to etMobileNumber1.value)
            finish()
        }
    }

    override fun showInvalidCredentials() {
//        etQatarId.error = getString(R.string.invalid_credentials)
//        etQatarId.errorShake { }
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.invalid_credentials), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
     //   showBlockedUser(R.string.invalid_credentials2, "")
    }
    override fun showInvalidCredentials1() {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.invalid_credentials3), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showInvalidCredentials2() {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.approved_person_can_resister), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showInvalidCredentials3() {
        etQatarId.requestFocus()
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
        alert(Appcompat, getString(R.string.login_duplicate_mobile), getString(R.string.failed)) {
            positiveButton(getString(R.string.ok)) { it.dismiss() }
        }.show()
    }
    override fun showBlockedUser(messageResId: Int, append: String) {
        etMobileNumber.text = null
        etQatarId.text = null
        etcrnumber.text=null
        etMobileNumber1.text=null
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
            finishAffinity()
            exitProcess(0)
        }
    }
}

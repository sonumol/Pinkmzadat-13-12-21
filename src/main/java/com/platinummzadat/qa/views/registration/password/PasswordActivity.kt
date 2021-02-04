package com.platinummzadat.qa.views.registration.password

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import com.platinummzadat.qa.*
import com.platinummzadat.qa.views.companyregister.RegisterAsCompanyActivity
import com.platinummzadat.qa.views.registration.profilephoto.ProfilePhotoRegistrationActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.platinummzadat.qa.views.root.company.CompanyRegisterFragment
import com.platinummzadat.qa.views.root.details.DetailsFragment
import com.platinummzadat.qa.views.tosactivity.TermsOfServiceActivity
import kotlinx.android.synthetic.main.activity_password.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.*

class PasswordActivity : MzActivity(), PasswordContract.View {
    override lateinit var presenter: PasswordContract.Presenter
    private lateinit var progress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        PasswordPresenter(this)
        clConfirm.onClick {
            updatePassword()
        }
        llTac.onClick {
            /*val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                this@PasswordActivity,
                Uri.parse(TOS_URL)
            )*/
            startActivity<TermsOfServiceActivity>()
        }
        progress = getProgressDialog()
    }

    private fun updatePassword() {
        when {
            etName.value.isEmpty() -> {
                etName.errorShake { }
                etName.requestFocus()
            }
            etEmail.value.isEmpty() -> {
                etEmail.errorShake { }
                etEmail.requestFocus()
            }
            etPassword.value.isEmpty() -> {
                etPassword.errorShake { }
                etPassword.requestFocus()
            }
            etConfirmPassword.value.isEmpty() -> {
                etConfirmPassword.errorShake { }
                etConfirmPassword.requestFocus()
            }
            etPassword.value != etConfirmPassword.value -> {
                toast(getString(R.string.passwords_do_not_match))
                etPassword.requestFocus()
                etPassword.errorWobble { }
                etConfirmPassword.errorWobble { }
                etPassword.text = null
                etConfirmPassword.text = null
            }
            else -> {
                presenter.updateProfile(etName.value, etEmail.value, etPassword.value)
            }
        }
    }

    override fun showSuccess() {
        var tempflap=true
        with(intent?.extras?.keySet()) {
            if (null != this) {
                if (this.contains("toPath")) {
                    if ("company" == intent?.extras?.getString("toPath")) {
                        tempflap=false
                        val i = Intent(this@PasswordActivity, RegisterAsCompanyActivity::class.java)
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                        finish()
                    }
                }

            }
        }
        if(tempflap){
            val i = Intent(this@PasswordActivity, ProfilePhotoRegistrationActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            finish()
        }



    }

    override fun showDuplicate() {
        root.longSnackbar(getString(R.string.try_with_diffferent_data), getString(R.string.ok)) {
            updatePassword()
        }
    }

    override fun showNoInternet() {
        noInternetAlert()
    }

    override fun showLoading() {
        progress.show()
        etPassword.lock()
        etConfirmPassword.lock()
        clConfirm.lock()
    }

    override fun hideLoading() {
        progress.hide()
        etPassword.unlock()
        etConfirmPassword.unlock()
        clConfirm.unlock()
    }

    override fun showApiError() {
        root.longSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            updatePassword()
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

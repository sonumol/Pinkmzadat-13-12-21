package com.platinummzadat.qa.views.splash

import android.os.Bundle
import com.google.firebase.iid.FirebaseInstanceId
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.SplashModel
import com.platinummzadat.qa.views.registration.password.PasswordActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.platinummzadat.qa.views.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import net.idik.lib.cipher.so.CipherClient
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.wlog
// emulator 19
// phone 18
class SplashActivity : MzActivity(), SplashContract.View {
    override lateinit var presenter: SplashContract.Presenter
    private var itemId = -1
    private var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashPresenter(this)
        type = intent?.extras?.getString("type") ?: ""
        itemId = (intent?.extras?.getString("item_id")?:"-1").toInt()
//        currentUserId = -1
//        currentUserId = 19
        setContentView(R.layout.activity_splash)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            wlog("token: ${it.token}")
            firebaseId = it?.token ?: ""
            /*Handler().postDelayed({
                if (-1 == currentUserId) {
                    startActivity<WelcomeActivity>()
                } else {
                    startActivity<RootActivity>()
                }
                finish()
            }, 1000)*/
            presenter.splash()
        }

    }

    override fun showSuccess(data: SplashModel) {
        if (-1 == currentUserId) {
            startActivity<WelcomeActivity>()
        } else {
            if (5 == data.status.toInt()) {
                startActivity<PasswordActivity>()
            } else {
                startActivity<RootActivity>("type" to type, "item_id" to itemId)
            }
        }
        finish()
    }

    override fun showBlocked() {
        alert(Appcompat,  getString(R.string.account_blocked, ""), getString(R.string.failed)) {
            positiveButton(getString(R.string.exit)) {
                it.dismiss()
                finish()
            }
        }.show()
    }

    override fun showNoInternet() {
        noInternetAlert()
        showApiError()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showApiError() {
        root?.indefiniteSnackbar(getString(R.string.some_error_occurred), getString(R.string.retry)) {
            presenter.splash()
        }
    }

    override fun sessionTimeOut() {
    }


}

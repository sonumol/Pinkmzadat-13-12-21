package com.platinummzadat.qa.views.welcome

import android.os.Bundle
import com.platinummzadat.qa.MzActivity
import com.platinummzadat.qa.R
import com.platinummzadat.qa.appLanguage
import com.platinummzadat.qa.mToken
import com.platinummzadat.qa.views.login.LoginActivity
import com.platinummzadat.qa.views.root.RootActivity
import com.platinummzadat.qa.views.splash.SplashActivity
import kotlinx.android.synthetic.main.content_welcome.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.currentLocalTimeInMillis

class WelcomeActivity : MzActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
//        startActivity<LoginActivity>()
//        finish()
        clCreateAccount.onClick {
            startActivity<LoginActivity>()
            finish()
        }

        tvSkip.onClick {
            mToken =""
            startActivity<RootActivity>()
            finish()
        }
        tvSwitch.onClick {
            alert(Appcompat, getString(R.string.language_change_message), getString(R.string.change_language)) {
                positiveButton(getString(R.string.restart2)) {
                    appLanguage = if ("en" == appLanguage) "ar" else "en"
                    startActivity<SplashActivity>()
                    finish()
//                        recreate()
                }
                negativeButton(getString(R.string.cancel2)) {
                    it.dismiss()
                }
            }.show()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
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

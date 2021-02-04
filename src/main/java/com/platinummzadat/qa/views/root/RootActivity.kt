package com.platinummzadat.qa.views.root

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.data.models.DashboardItemModel
import com.platinummzadat.qa.data.models.WinningBidsDetails
import com.platinummzadat.qa.views.login.LoginActivity
import com.platinummzadat.qa.views.root.aboutus.AboutUsFragment
import com.platinummzadat.qa.views.root.auctions.*
import com.platinummzadat.qa.views.root.company.CompanyRegisterFragment
import com.platinummzadat.qa.views.root.companyfees.CompanyFeesFragment
import com.platinummzadat.qa.views.root.companyfees.WinningBidsFragment
import com.platinummzadat.qa.views.root.companyfees.WinningBidsPaymentFragment
import com.platinummzadat.qa.views.root.contactus.ContactUsFragment
import com.platinummzadat.qa.views.root.dashboard.DashboardFragment
import com.platinummzadat.qa.views.root.depositamount.DepositAmountFragment
import com.platinummzadat.qa.views.root.details.DetailsFragment
import com.platinummzadat.qa.views.root.drawer.MainDrawerFragment
import com.platinummzadat.qa.views.root.drawer.MzNav
import com.platinummzadat.qa.views.root.drawer.MzNav.*
import com.platinummzadat.qa.views.root.faq.FAQFragment
import com.platinummzadat.qa.views.root.notifications.NotificationsFragment
import com.platinummzadat.qa.views.root.profile.ProfileFragment
import com.platinummzadat.qa.views.root.profile.deposit.PaymentActivity
import com.platinummzadat.qa.views.root.tac.TermsAndConditionsFragment
import com.platinummzadat.qa.views.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.appbar_root.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.share
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.clearAndShow
import raj.nishin.wolfpack.replaceFragment
import kotlin.system.exitProcess


class RootActivity : MzActivity(),MzFragmentListener, RootContract.View {
    override lateinit var presenter: RootContract.Presenter
    lateinit var progress: ProgressDialog
    val dashboardFragment by lazy { DashboardFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RootPresenter(this)
        setContentView(R.layout.appbar_root)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            com.platinummzadat.qa.R.string.navigation_drawer_open,
            com.platinummzadat.qa.R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(dashboardFragment, R.id.container)
        progress = ProgressDialog(this)
        progress.setMessage(getString(R.string.please_wait))
        progress.setCancelable(false)
        handleEntry(intent)

    }
    override fun onResume() {
        super.onResume()
        setTitle(getString(com.platinummzadat.qa.R.string.REGISTER_AS_COMPANY))
    }

    private fun handleEntry(intent: Intent?) {
        with(intent?.extras?.keySet()) {
            if (null != this) {
                if (this.contains("type")) {
                    if ("notification" == intent?.extras?.getString("type")) {
                        dashboardFragment.refresh = true
                        showNotifications()
                    } else if ("redirect" == intent?.extras?.getString("type")) {
                        val itemId = intent?.extras?.getInt("item_id")!!
                        if (-1 != itemId) {
                            dashboardFragment.refresh = true
                            replaceFragment(
                                DetailsFragment.newInstance(itemId),
                                R.id.container, true
                            )
                        }
                    }else if("profile"==intent?.extras?.getString("type")){
                        dashboardFragment.refresh = true
                        showProfile()
                        //
//                        if (this.contains("toPath")) {
//                            if ("company" == intent?.extras?.getString("toPath")) {
//                                replaceFragment(CompanyRegisterFragment.newInstance(111), R.id.container, true)
//                            }
//                        }else{
//                            showProfile()
//                        }
                    }else if("company"==intent?.extras?.getString("type")){
                        dashboardFragment.refresh = true
                        replaceFragment(CompanyRegisterFragment(), R.id.container, true)
                    }
                }

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleEntry(intent)
    }
    override fun lastActiveUpdated() {
       quitApp()
    }

    override fun showNoInternet() {
    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun showApiError() {
    }

    override fun sessionTimeOut() {
    }

    override fun onError(callback: () -> Unit) {
        onErrorWithMessage(getString(com.platinummzadat.qa.R.string.some_error_occurred_try_again), null, callback)
    }

    override fun onErrorWithMessage(message: String, actionText: String?, callback: () -> Unit) {
        root.indefiniteSnackbar(message, actionText ?: getString(com.platinummzadat.qa.R.string.retry)) {
            callback.invoke()
        }
    }

    override fun onErrorWithAutoHideMessage(
        message: String,
        actionText: String?,
        callback: () -> Unit
    ) {
        root.longSnackbar(message, actionText ?: getString(com.platinummzadat.qa.R.string.retry)) {
            callback.invoke()
        }
    }

    override fun onSelectDashboardItem(item: DashboardItemModel) {
        replaceFragment(AuctionsFragment.newInstance(item.id, item.name), R.id.container, true)
    }

    override fun onSelectDepositAmount(item: DepositAmountFragment) {
        replaceFragment(DepositAmountFragment(), R.id.container, true)
    }

    override fun onSelectAuctionItem(item: AuctionItemModel) {
        replaceFragment(DetailsFragment.newInstance(item.id), R.id.container, true)
    }

    override fun onSelectWinningBidItem(item: WinningBidsDetails) {
        replaceFragment(WinningBidsPaymentFragment.newInstance(item.id), R.id.container, true)
    }

//    override fun onSelectPaymentItem(amount: String) {
//        replaceFragment(WinningBidsPaymentFragment.newInstance(amount), R.id.container, true)
//    }

    override fun refreshProfileInfo() {
        (supportFragmentManager.findFragmentById(R.id.mainNavFragment) as MainDrawerFragment).setUserDetails()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun onSelectNavItem(navItem: MzNav) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        when (navItem) {
            AUCTIONS -> {
                clearAndShow(dashboardFragment)
            }

            MY_PROFILE -> {
                if (-1 != currentUserId) {
                    replaceFragment(ProfileFragment(), R.id.container, true)
                } else {
                    showLoginSnack()
                }

            }
            MY_DEPOSIT_AMOUNT -> {
                if (-1 != currentUserId) {
                    replaceFragment(DepositAmountFragment(), R.id.container, true)
                } else {
                    showLoginSnack()
                }
            }
            MY_BIDS -> {
                if (-1 != currentUserId) {
                    replaceFragment(
                        AuctionsFragment.newInstance(
                            -1,
                            getString(com.platinummzadat.qa.R.string.my_bids),
                            AUCTION_MODE_MY_BIDS
                        ),
                        R.id.container,
                        true
                    )
                } else {
                    showLoginSnack()
                }

            }
            WISHING_BIDS -> {
                if (-1 != currentUserId) {
                    replaceFragment(
                        AuctionsFragment.newInstance(
                            -1,
                            getString(com.platinummzadat.qa.R.string.wishing_bids),
                            AUCTION_MODE_WISHING_BIDS
                        ),
                        R.id.container,
                        true
                    )
                } else {
                    showLoginSnack()
                }
            }
            WINNING_BIDS -> {
                if (-1 != currentUserId) {
                    replaceFragment(WinningBidsFragment(), R.id.container, true)
                } else {
                    showLoginSnack()
                }
            }
            ABOUT_US -> {
                replaceFragment(AboutUsFragment(), R.id.container, true)
            }
            NOTIFICATIONS -> {
                showNotifications()
            }
            COMPANY_FEES -> {
                replaceFragment(CompanyFeesFragment(), R.id.container, true)
            }

            TAC -> {
                /* val builder = CustomTabsIntent.Builder()
                 val customTabsIntent = builder.build()
                 customTabsIntent.launchUrl(
                     this@RootActivity,
                     Uri.parse(RemoteDataSource.TOS_URL)
                 )*/
                replaceFragment(TermsAndConditionsFragment(), R.id.container, true)
            }
            CONTACT -> {
                replaceFragment(ContactUsFragment(), R.id.container, true)
            }
            SHARE_APP -> {
                share(getString(R.string.share_content))
            }
            FAQ -> {
                replaceFragment(FAQFragment(), R.id.container, true)
            }
            REGISTER_AS_COMPANY -> {
                replaceFragment(CompanyRegisterFragment(), R.id.container, true)
            }
            RATE_APP -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.platinummzadat.qa")
                    )
                )
            }
            LOGOUT -> {
                if (-1 != currentUserId) {
                    MApp.logout()
                    toast(getString(R.string.logged_out))
                    startActivity<SplashActivity>()
                    finish()
                } else {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        }
    }

    private fun showNotifications() {
        replaceFragment(NotificationsFragment(), R.id.container, true)
    }

    private fun showProfile() {
        replaceFragment(ProfileFragment(), R.id.container, true)
    }

    private fun showLoginSnack() {
        root.longSnackbar(getString(R.string.please_login_to_continue), getString(R.string.login)) {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RC_SEARCH == requestCode && resultCode == Activity.RESULT_OK) {
            val query = data!!.getStringExtra("query")
            replaceFragment(
                AuctionsFragment.newInstance(
                    -1,
                    getString(R.string.search_format, query),
                    AUCTION_MODE_SEARCH,
                    query
                ),
                R.id.container,
                true
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.platinummzadat.qa.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
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
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (0 == supportFragmentManager.backStackEntryCount)
                alert(Appcompat, getString(R.string.sure_exit_now), getString(R.string.exit)) {
                    positiveButton(getString(R.string.exit)) {
                        presenter.updateLastActive()
                        it.dismiss()
                    }
                    negativeButton(getString(R.string.cancel)) {
                        it.dismiss()
                    }
                }.show()
            else
               super.onBackPressed()
        }
    }
    fun quitApp() {
        finishAffinity()
        System.exit(0)
    }
}

package com.platinummzadat.qa.views.root

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.data.models.DashboardItemModel
import com.platinummzadat.qa.data.models.WinningBidsDetails
import com.platinummzadat.qa.views.login.LoginActivity
import com.platinummzadat.qa.views.root.aboutus.AboutUsFragment
import com.platinummzadat.qa.views.root.auctions.AUCTION_MODE_MY_BIDS
import com.platinummzadat.qa.views.root.auctions.AUCTION_MODE_SEARCH
import com.platinummzadat.qa.views.root.auctions.AUCTION_MODE_WISHING_BIDS
import com.platinummzadat.qa.views.root.auctions.AuctionsFragment
import com.platinummzadat.qa.views.root.company.CompanyRegisterFragment
import com.platinummzadat.qa.views.root.companyfees.CompanyFeesFragment
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
import com.platinummzadat.qa.views.root.tac.TermsAndConditionsFragment
import com.platinummzadat.qa.views.splash.SplashActivity
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.platinummzadat.qa.data.models.DashboardModel
import com.platinummzadat.qa.networking.DatabaseHelper
import com.platinummzadat.qa.views.root.companyfees.WinningBidsFragment
import com.platinummzadat.qa.views.root.companyfees.WinningBidsPaymentFragment
import com.platinummzadat.qa.views.root.profile.ProfileFragment1
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.appbar_root.*
import kotlinx.android.synthetic.main.fragment_main_drawer.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.share
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import raj.nishin.wolfpack.clearAndShow
import raj.nishin.wolfpack.loadAvatar
import raj.nishin.wolfpack.replaceFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class RootActivity : MzActivity(),MzFragmentListener, RootContract.View {
    override lateinit var presenter: RootContract.Presenter
    lateinit var progress: ProgressDialog
    private var mTracker: Tracker?=null
    var refresh = false
    private val sharedPrefFile = "kotlinsharedpreference"
    val dashboardFragment by lazy { DashboardFragment() }
    var mDatabaseHelper: DatabaseHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RootPresenter(this)
        setContentView(R.layout.appbar_root)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(dashboardFragment, R.id.container)
        mDatabaseHelper=DatabaseHelper(this)

        presenter.fetchDashboard()
        progress = ProgressDialog(this)
        progress.setMessage(getString(R.string.please_wait))
        progress.setCancelable(false)
        handleEntry(intent)
//
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
        //setTitle(getString(R.string.REGISTER_AS_COMPANY))
        dashboardFragment.refresh = true
        mTracker!!.setScreenName("Image~" + "RootActivity")
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
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
    override fun showDashboard(data: DashboardModel) {
//        Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
//        val data1=mDatabaseHelper!!.getItemID(data.user_id)
//        if (data1.count == 0) {
//
//            // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
//        } else {
//            while (data1.moveToNext()) {
//
//                val path=data1.getString(3)
//                profilePhotoUrl = path
//                // Toast.makeText(applicationContext,"aaa"+path,Toast.LENGTH_LONG).show()
//                ivProfile.loadAvatar(path.toString(), R.drawable.ic_nav_account)
//                val sharedPreferences: SharedPreferences=this.getSharedPreferences("path",
//                    Context.MODE_PRIVATE)
//                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//                editor.putString("path", path)
//                editor.apply()
//                editor.commit()
//            }
//        }


    }
    override fun onError(callback: () -> Unit) {
        onErrorWithMessage(getString(R.string.some_error_occurred_try_again), null, callback)
    }

    override fun onErrorWithMessage(message: String, actionText: String?, callback: () -> Unit) {
        root.indefiniteSnackbar(message, actionText ?: getString(R.string.retry)) {
            callback.invoke()
        }
    }

    override fun onErrorWithAutoHideMessage(
        message: String,
        actionText: String?,
        callback: () -> Unit
    ) {
        root.longSnackbar(message, actionText ?: getString(R.string.retry)) {
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
      //  Toast.makeText(applicationContext,""+item.listing_count,Toast.LENGTH_LONG).show()
        replaceFragment(DetailsFragment.newInstance(item.id), R.id.container, true)
        val sharedPreferences: SharedPreferences= this.getSharedPreferences("item.listing_count",
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("item.listing_count", item.listing_count)
        editor.apply()
        editor.commit()

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
    override fun onapprove(id:Int) {
        // Toast.makeText(applicationContext,"sony"+id,Toast.LENGTH_LONG).show()
//       confirm==id

        val sharedPreferences: SharedPreferences= this.getSharedPreferences("approvedid",
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
//        val name:String =radioButton.text.toString()
        editor.putInt("id", id)
        editor.apply()
        editor.commit()



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
                    refresh = true
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
                            getString(R.string.my_bids),
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
                            getString(R.string.wishing_bids),
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
                val sharedPreferences: SharedPreferences= this.getSharedPreferences("approvedid",
                    Context.MODE_PRIVATE)
                val confirm = sharedPreferences.getInt("id",0)
                if(confirm==0)
                {

//                   Snackbar.make(view,getString(R.string.only_approved_person),
//                       Snackbar.LENGTH_LONG).setAction("Action",null).show()
                    root.longSnackbar(getString(R.string.only_approved_person), getString(R.string.cancel)) {
                        startActivity<RootActivity>()
                        finish()
                    }
                }

                else if(confirm==1)
                {
                    replaceFragment(CompanyRegisterFragment(), R.id.container, true)
                }
                else
                {
                    showLoginSnack()
                }


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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                alert(
                    Appcompat,
                    getString(R.string.language_change_message),
                    getString(R.string.change_language)
                ) {
                    positiveButton(getString(R.string.restart2)) {
                        appLanguage=if ("en" == appLanguage) "ar" else "en"
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

//
//                        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm:a ")
//                        val currentDateAndTime: String = simpleDateFormat.format(Date())
                        val date=Date()
                        val df: DateFormat=SimpleDateFormat("dd MMM yyyy, hh:mm:a" ,Locale.ENGLISH)//
                        df.setTimeZone(TimeZone.getTimeZone("Asia/Qatar"))
                       // )

                        val sharedPreferences: SharedPreferences = getSharedPreferences(
                            sharedPrefFile,
                            Context.MODE_PRIVATE
                        )
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("date", df.format(date))

                        editor.apply()
                        editor.commit()
//                        val simpleDateFormat1 = SimpleDateFormat("hh:mm:a ")
//                        val currentDateAndTime1: String = simpleDateFormat1.format(Date())
//                        if(currentDateAndTime1=="12:00:AM ")
//                        {
//                            presenter.updateLastActive()
//                              }
                        finishAffinity()
                        exitProcess(0)

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

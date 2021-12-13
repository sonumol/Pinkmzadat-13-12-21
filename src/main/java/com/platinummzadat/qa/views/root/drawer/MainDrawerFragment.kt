package com.platinummzadat.qa.views.root.drawer


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.NavDrawerModel
import kotlinx.android.synthetic.main.fragment_main_drawer.*
import raj.nishin.wolfpack.itemOffset
import raj.nishin.wolfpack.layoutManager
import raj.nishin.wolfpack.loadAvatar

class MainDrawerFragment : MzFragment() {
    private val navItems by lazy { initNavItems() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserDetails()
        rvNavDrawer layoutManager LinearLayoutManager(activity)
        rvNavDrawer.adapter = DrawerAdapter(navItems) {
            fragmentListener?.onSelectNavItem(it.choice)
        }
        rvNavDrawer itemOffset 16
    }

    fun setUserDetails() {
        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences("path",
            Context.MODE_PRIVATE)
        val path = sharedPreferences.getString("path","")
        ivProfile.loadAvatar(path.toString(), R.drawable.ic_nav_account)
//        ivProfile.loadAvatar(profilePhotoUrl, R.drawable.ic_nav_account)
        tvUsername.text = username
        tvMobileNumber.text = mobileNumber
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_drawer, container, false)
    }

    private fun initNavItems() = ArrayList<NavDrawerModel>().apply {
        add(NavDrawerModel(getString(R.string.nav_auctions), R.drawable.ic_nav_auctions, MzNav.AUCTIONS))
        add(NavDrawerModel(getString(R.string.nav_my_bids), R.drawable.ic_nav_my_bids, MzNav.MY_BIDS))
        add(NavDrawerModel(getString(R.string.wishing_bids), R.drawable.ic_nav_wishing_bids, MzNav.WISHING_BIDS))
         add(NavDrawerModel(getString(R.string.winning_bids), R.drawable.ic_badge, MzNav.WINNING_BIDS))
        add(NavDrawerModel(getString(R.string.nav_my_profile), R.drawable.ic_nav_my_profile, MzNav.MY_PROFILE))
        add(
            NavDrawerModel(
                getString(R.string.nav_deposit_amount),
                R.drawable.ic_nav_deposit_amount,
                MzNav.MY_DEPOSIT_AMOUNT
            )
        )
        add(NavDrawerModel(getString(R.string.register_as_company), R.drawable.ic_outline_business_center_24, MzNav.REGISTER_AS_COMPANY))
        add(NavDrawerModel(getString(R.string.nav_notifications), R.drawable.ic_nav_notifications, MzNav.NOTIFICATIONS))
        add(NavDrawerModel(getString(R.string.nav_company_fees), R.drawable.ic_nav_company_fees, MzNav.COMPANY_FEES))
        add(NavDrawerModel(getString(R.string.nav_terms_and_conditions), R.drawable.ic_nav_tac, MzNav.TAC))
        add(NavDrawerModel(getString(R.string.nav_contact_us), R.drawable.ic_nav_contact_us, MzNav.CONTACT))
        add(NavDrawerModel(getString(R.string.nav_faq), R.drawable.ic_faq, MzNav.FAQ))
        add(NavDrawerModel(getString(R.string.nav_share_this_app), R.drawable.ic_nav_share, MzNav.SHARE_APP))

        add(NavDrawerModel(getString(R.string.nav_rate_this_app), R.drawable.ic_nav_rate_this_app, MzNav.RATE_APP))

        if (-1 == currentUserId) {
            add(NavDrawerModel(getString(R.string.nav_login_register), R.drawable.ic_nav_logout, MzNav.LOGOUT))
        } else {
            add(NavDrawerModel(getString(R.string.nav_logout), R.drawable.ic_nav_logout, MzNav.LOGOUT))
        }
    }


}

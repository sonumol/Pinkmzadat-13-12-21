package com.platinummzadat.qa.views.root.dashboard


import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.fxn.stash.Stash
import com.google.android.material.button.MaterialButton
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.DashboardModel
import com.platinummzadat.qa.data.models.FavAutionIdsRes
import com.platinummzadat.qa.views.login.LoginActivity
import com.platinummzadat.qa.views.root.drawer.MzNav
import com.platinummzadat.qa.views.root.search.SearchActivity
import com.platinummzadat.qa.views.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import java.util.HashSet
import kotlin.system.exitProcess


class DashboardFragment : MzFragment(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter
    var refresh = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLoad) {
            presenter.fetchDashboard()
            presenter.fetchFavAutionsIDs()
        }


        llMyProfile?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.MY_PROFILE)
        }
        llMyBids?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.MY_BIDS)
        }
        llWishingBids?.onClick {
            fragmentListener?.onSelectNavItem(MzNav.WISHING_BIDS)
        }
        llSearch?.onClick {
            activity?.startActivityForResult<SearchActivity>(RC_SEARCH)
        }
        tvUploadQid?.onClick {
            refresh = true
            fragmentListener?.onSelectNavItem(MzNav.MY_PROFILE)
        }
    }
    private fun showAppUpdateDialog() {
        val dialog = Dialog(this@DashboardFragment.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.update_available_diaog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            val appPackageName: String = this@DashboardFragment.requireActivity().getPackageName() // getPackageName() from Context or Activity object
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
        dialog.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(dialogInterface: DialogInterface?) {
                //do whatever you want when dialog is dismissed
                this@DashboardFragment.requireActivity().moveTaskToBack(true);
                exitProcess(-1)
            }
        })

        dialog.show()



    }
    override fun showDashboard(data: DashboardModel) {
        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
//        if(!data.version_name!!.equals(versionName)){
//            showAppUpdateDialog()
//        }else{
            if(data.blocked!!){
                activity!!.alert(Appcompat, resources.getString(R.string.account_blocked), getString(R.string.failed)) {
                    isCancelable = false
                    positiveButton(getString(R.string.ok)) {
                        if (-1 != currentUserId) {
                            MApp.logout()
                            toast(getString(R.string.logged_out))
                            activity!!.startActivity<SplashActivity>()
                            activity!!.finish()
                        } else {
                            activity!!.startActivity<LoginActivity>()
                            activity!!.finish()
                        }
                    }
                }.show()
            }else{
                mobileNumber = data.mobileNumber ?: ""
                profilePhotoUrl = data.profilePhoto ?: ""
                username = data.username ?: ""
                fragmentListener?.refreshProfileInfo()
                tvUploadQid?.visibility(if (data.showQidUpload) gone else visible)
                tvLastLogin?.text = getString(R.string.last_login_format, data.lastLogin)
                tvUsername?.text = getString(R.string.greet_format, data.username)
                tvScroll?.text = data.scrollText
                tvScroll?.visibility(if (data.scrollText.trim().isEmpty()) gone else visible)
                rvDashboard?.layoutManager(GridLayoutManager(activity, 2))
                rvDashboard?.nestedScroll(false)
//        rvDashboard?.addItemDecoration(SimpleDividerItemDecoration(activity))

                if (null != activity) {
                    val verticalDecoration = DividerItemDecoration(
                            activity,
                            DividerItemDecoration.HORIZONTAL
                    )
                    val verticalDivider = activity?.resources!!.getDrawable(R.drawable.grid_vertical_divider)
                    verticalDecoration.setDrawable(verticalDivider)
                    rvDashboard?.addItemDecoration(verticalDecoration)

                    val horizontalDecoration = DividerItemDecoration(
                            activity,
                            DividerItemDecoration.VERTICAL
                    )
                    val horizontalDivider = activity?.resources!!.getDrawable(R.drawable.grid_horizontal_divider)
                    horizontalDecoration.setDrawable(horizontalDivider)
                    rvDashboard?.addItemDecoration(horizontalDecoration)
                }

                rvDashboard?.adapter = DashboardAdapter(data.items) {
                    fragmentListener?.onSelectDashboardItem(it)
                }
                ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle_primary_dark)
                content?.visibility(visible)
                llUserDetails?.visibility(if (-1 == currentUserId) gone else visible)
                tvUploadQid?.visibility(
                        if (-1 == currentUserId) gone else {
                            if (data.showQidUpload) visible else gone
                        }
                )
                tvUploadQid?.text = data.qidMessage ?: getString(R.string.upload_qid_info_dashboard)
                tvScroll?.requestFocus()
           // }
        }




    }

    override fun showFavIds(data: FavAutionIdsRes) {
        var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
        followAuctionList.clear()
        if(data.data!=null){
         data.data.forEach {
             followAuctionList.add(it)
             Log.d("favoriteProductIdsList",it )
         }

            Stash.put("favoriteProductIdsList", followAuctionList)
        }



    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
        error?.visibility(gone)
    }

    override fun hideLoading() {

        loading?.visibility(gone)
//        swipeRefresh.isRefreshing = false
    }

    override fun showApiError() {
        error?.visibility(visible)
        fragmentListener?.onError {
            presenter.fetchDashboard()
        }
    }


    override fun sessionTimeOut() {
    }

    override fun onResume() {
        super.onResume()
        if (refresh) {
            refresh = false
            presenter.fetchDashboard()
        }
        fragmentListener?.setTitle(getString(R.string.court_mzadat))
        tvScroll?.requestFocus()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        DashboardPresenter(this)
        return super.onCreateView(R.layout.fragment_dashboard, inflater, container)
    }

}

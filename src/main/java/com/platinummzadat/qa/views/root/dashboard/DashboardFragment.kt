package com.platinummzadat.qa.views.root.dashboard


import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fxn.stash.Stash
import com.google.android.material.button.MaterialButton
import com.platinummzadat.qa.*
import com.platinummzadat.qa.data.models.*
import com.platinummzadat.qa.networking.DatabaseHelper
import com.platinummzadat.qa.views.login.LoginActivity
import com.platinummzadat.qa.views.root.drawer.MzNav
import com.platinummzadat.qa.views.root.notifications.NotificationsAdapter
import com.platinummzadat.qa.views.root.search.SearchActivity
import com.platinummzadat.qa.views.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.content
import kotlinx.android.synthetic.main.fragment_dashboard.ivProfilePhoto
import kotlinx.android.synthetic.main.fragment_dashboard.loading
import kotlinx.android.synthetic.main.fragment_dashboard.tvUsername
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.*
import java.util.*
import kotlin.system.exitProcess


class DashboardFragment : MzFragment(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter
    var refresh = false
    private val sharedPrefFile = "kotlinsharedpreference"
    private var date1="jjj"
    private var type = 0
    var mDatabaseHelper: DatabaseHelper?=null
    var handler: Handler=Handler()
    lateinit var refresh1: Runnable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabaseHelper=DatabaseHelper(context!!)
        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("id_key","")
        if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {

            type=1

        }
        else{
            type=2


        }
//        refresh1=Runnable { // Do something
//            handler.postDelayed(refresh1, 5000)
//            presenter.fetchDashboard(type)
//        }
//        handler.post(refresh1)

        if (firstLoad) {
            presenter.fetchDashboard(type)
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


            if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {

                refresh = true
                fragmentListener?.onSelectNavItem(MzNav.MY_PROFILE)

            }
            else{
                refresh = true
                fragmentListener?.onSelectNavItem(MzNav.REGISTER_AS_COMPANY)
            }

        }

        val preferences: SharedPreferences=context!!.getSharedPreferences("item.listing_count", 0)
        preferences.edit().remove("item.listing_count").commit()


    }
    private  fun showDashboard1(DashboardItemModel: Any?)
    {

    }
    private fun showAppUpdateDialog() {
        val dialog = Dialog(this@DashboardFragment.requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.update_available_diaog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val btnPayment = dialog.findViewById(R.id.btnSubmit) as MaterialButton
        btnPayment.setOnClickListener {
            val appPackageName: String = this@DashboardFragment.requireActivity().getPackageName() // getPackageName() from Context or Activity object
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
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
//    override fun showData(dataSet: ArrayList<DashboardItemModel1>) {
//        Toast.makeText(MApp.applicationContext(),"a"+dataSet,Toast.LENGTH_LONG).show()
//    }

    override fun showDashboard(data: DashboardModel) {
        fragmentListener?.onapprove(data.user_approved)
        val sharedPreferences: SharedPreferences= context!!.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("id_key","")
        if(sharedNameValue=="Personal" || sharedNameValue=="شخصي") {

            type=1
            tvUsername?.text = getString(R.string.greet_format, data.username)
            username = data.username ?: ""
        }
        else{
            type=2
            tvUsername?.text = getString(R.string.greet_format, data.company_name)
            username = data.company_name ?: ""

        }



        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
        //toast(data.blocked)
        // Toast.makeText(MApp.applicationContext(),"a"+data.blocked,Toast.LENGTH_LONG).show()
        if(!data.version_name!!.equals(versionCode.toString())){
            showAppUpdateDialog()
        }else{
            if(data.blocked!!){
                activity!!.alert(
                    Appcompat, resources.getString(R.string.account_blocked), getString(
                        R.string.failed
                    )
                ) {
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



                //profilePhotoUrl = data.profilePhoto ?: ""
                username = data.username ?: ""
                fragmentListener?.refreshProfileInfo()
                tvUploadQid?.visibility(if (data.showQidUpload) gone else visible)
                val preferences: SharedPreferences=this.activity!!.getSharedPreferences(
                    "kotlinsharedpreference", Context.MODE_PRIVATE
                )
                // date1 =activity.sharedPreferences.getString("lid", null).toString()
                // val pref=activity!!.getPreferences(Context.MODE_PRIVATE)
                val id=preferences.getString("date", "")

                if(id!="")
                {
                    // tvLastLogin?.text = getString(R.string.last_login_format1)
                    val s=getString(R.string.lastara)
                    //Toast.makeText(activity,""+s,Toast.LENGTH_LONG).show()
//                    val format=SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//                   val s1=  format.id

                    if(s=="آخر تسجيل دخول")
                    {
                        tvLastLogin?.setText(id+" : "+s)
                        tvLastLogin.textAlignment = View.TEXT_ALIGNMENT_TEXT_END

//                        val rtl="id+\" : \"+s\u200F(سلام)"
//                        tvLastLogin?.setText(rtl)
                    }
                    else
                    {
                        tvLastLogin?.setText(s+" : "+id)
                    }
                }
//                val toast = Toast.makeText(activity, ""+data.lastLogin+""+id, Toast.LENGTH_LONG)
//                toast.show()

                //  tvLastLogin?.text = getString(R.string.last_login_format,id)



                // tvUsername?.text = getString(R.string.greet_format, data.username)
                tvScroll?.text = data.scrollText
                tvScroll?.visibility(if (data.scrollText.trim().isEmpty()) gone else visible)
                rvDashboard?.layoutManager(GridLayoutManager(activity, 2))
                rvDashboard?.nestedScroll(false)
        rvDashboard?.addItemDecoration(SimpleDividerItemDecoration(activity))

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
                  //  showDashboard_category(data.items)

                rvDashboard?.adapter = DashboardAdapter(data.items) {
                    fragmentListener?.onSelectDashboardItem(it)
                }
                val sharedPreferences1: SharedPreferences=context!!.getSharedPreferences("user_id",
                    Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences1.edit()
                editor.putString("user_id", data.user_id)
                editor.apply()
                editor.commit()

                val data1=mDatabaseHelper!!.getItemID(data.user_id)
                if (data1.count == 0) {

                    // Toast.makeText(MApp.applicationContext(), "no elements", Toast.LENGTH_SHORT).show();
                } else {
                    while (data1.moveToNext()) {

                        val path=data1.getString(3)
                        profilePhotoUrl = path
                        //Toast.makeText(MApp.applicationContext(), "no elements"+path, Toast.LENGTH_SHORT).show();
                        ivProfilePhoto?.loadAvatar(profilePhotoUrl, R.drawable.ic_account_circle_primary_dark)

                        val sharedPreferences2: SharedPreferences=context!!.getSharedPreferences("path",
                            Context.MODE_PRIVATE)
                        val editor1: SharedPreferences.Editor =  sharedPreferences2.edit()
                        editor1.putString("path", path)
                        editor1.apply()
                        editor1.commit()
                    }
                }

//                ivProfilePhoto?.loadAvatar(
//                    profilePhotoUrl,
//                    R.drawable.ic_account_circle_primary_dark
//                )
                content?.visibility(visible)
                llUserDetails?.visibility(if (-1 == currentUserId) gone else visible)
                tvUploadQid?.visibility(
                    if (-1 == currentUserId) gone else {
                        if (data.showQidUpload) visible else gone
                    }
                )
                tvUploadQid?.text = data.qidMessage ?: getString(R.string.upload_qid_info_dashboard)
                tvScroll?.requestFocus()
            }
        }




    }
    fun showDashboard_category(data: ArrayList<DashboardItemModel>) {

//        Toast.makeText(MApp.applicationContext(),""+data,Toast.LENGTH_LONG).show()




    }

    override fun showFavIds(data: FavAutionIdsRes) {
        var followAuctionList = Stash.getStringSet("favoriteProductIdsList")
        followAuctionList.clear()
        if(data.data!=null){
            data.data.forEach {
                followAuctionList.add(it)
                Log.d("favoriteProductIdsList", it)
            }

            Stash.put("favoriteProductIdsList", followAuctionList)
        }



    }

    override fun lastActiveUpdated() {
        // quitApp()
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
            presenter.fetchDashboard(type)
        }
    }
    fun quitApp() {
        //  finishAffinity()
        System.exit(0)
    }

    override fun sessionTimeOut() {
    }

    override fun onResume() {
        super.onResume()
        if (refresh) {
            refresh = false
            presenter.fetchDashboard(type)
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

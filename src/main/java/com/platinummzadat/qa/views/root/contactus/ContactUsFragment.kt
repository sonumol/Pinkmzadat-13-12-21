package com.platinummzadat.qa.views.root.contactus


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.platinummzadat.qa.MzFragment
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.ContactUsModel
import com.platinummzadat.qa.noInternetAlert
import kotlinx.android.synthetic.main.fragment_contact_us.*
import org.jetbrains.anko.email
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import raj.nishin.wolfpack.dialNumber
import raj.nishin.wolfpack.gone
import raj.nishin.wolfpack.visibility
import raj.nishin.wolfpack.visible


class ContactUsFragment : MzFragment(), ContactUsContract.View {
    override lateinit var presenter: ContactUsContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstLoad){
            presenter.fetchContactUs()
        }
    }

    override fun showData(data: ContactUsModel) {
        llPhone.onClick {
            activity?.dialNumber(data.telephone)
        }
        /*llMap.onClick {
            startActivity(
                Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=loc:25.282752,51.505959")
                )
            )
        }*/
        llEmail.onClick {
            activity?.email(data.email)
        }
        tvEmail?.text = data.email
        tvAddress?.text = data.address
        tvPhone?.text = "Tel ${data.telephone}\nFax ${data.fax}"
        content?.visibility(visible)
    }

    override fun showNoInternet() {
        activity?.noInternetAlert()
    }

    override fun showLoading() {
        loading?.visibility(visible)
        content?.visibility(gone)
    }

    override fun hideLoading() {
        loading?.visibility(gone)
    }

    override fun showApiError() {
        toast(getString(R.string.some_error_occurred_try_again))
    }

    override fun sessionTimeOut() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ContactUsPresenter(this)
        return super.onCreateView(com.platinummzadat.qa.R.layout.fragment_contact_us, inflater, container)
    }

    override fun onResume() {
        super.onResume()
        fragmentListener?.setTitle(getString(com.platinummzadat.qa.R.string.nav_contact_us))
    }
}

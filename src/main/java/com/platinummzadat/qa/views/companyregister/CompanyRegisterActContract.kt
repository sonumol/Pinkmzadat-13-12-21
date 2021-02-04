package com.platinummzadat.qa.views.companyregister

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.CheckCompanyRegisterRes
import com.platinummzadat.qa.data.models.ComputerCheckRes
import com.platinummzadat.qa.data.models.FaqRes

interface CompanyRegisterActContract {
    interface View : BaseView<Presenter> {
        fun showData(data: CheckCompanyRegisterRes)
        fun showDataNoRegistration(data:CheckCompanyRegisterRes)
        fun showSuccess()
        fun showFailure()
        fun showSuccessComputerCard(data: ComputerCheckRes)
        fun showFailureComputerCard(data: ComputerCheckRes)
    }

    interface Presenter : BasePresenter {
        fun checkCompanyRegistartion()
        fun checkComputerCard( cr_no: String)
        fun registerCompany(
                cr_no: String,
                company_name: String,
                company_sign_id: String
        )
    }
}
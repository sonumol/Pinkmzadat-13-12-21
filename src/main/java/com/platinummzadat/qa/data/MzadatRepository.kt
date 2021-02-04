package com.platinummzadat.qa.data

import com.platinummzadat.qa.data.models.*
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 11:16 on Tuesday 02 April 2019
 */


class MzadatRepository(private val remote: MzDataSource) : MzDataSource {
    override fun fetchCompanyFees(
        userId: Int,
        result: (status: Boolean,data: CompanyFeesRespose, error: ERROR) -> Unit
    ) {
        remote.fetchCompanyFees(userId, result)
    }

    override fun getAmount(userId: Int, result: (status: Boolean, data: AmountData?, error: ERROR) -> Unit) {
        remote.getAmount(userId, result)
    }

    override fun registerCompany(cr_no: String, company_name: String, company_sign_id: String, result: (status: Boolean, data: CompanyRegistrationRes?, error: ERROR) -> Unit) {
        remote.registerCompany(cr_no, company_name,company_sign_id,result)
    }

    override fun checkCompanyRegistration(result: (status: Boolean, data: CheckCompanyRegisterRes?, error: ERROR) -> Unit) {
        remote.checkCompanyRegistration( result)
    }

    override fun checkComputerCard(cr_no: String, result: (status: Boolean, data: ComputerCheckRes?, error: ERROR) -> Unit) {
        remote.checkComputerCard(cr_no, result)
    }

    override fun getRefundRequest(imei: String, result: (status: String, data: RefundRequestRes?, error: ERROR) -> Unit) {
        remote.getRefundRequest(imei, result)
    }


    override fun fetchContactUs(result: (status: Boolean, data: ContactUsModel?, error: ERROR) -> Unit) {
        remote.fetchContactUs(result)
    }

    override fun fetchAboutUs(result: (status: Boolean, data: AboutUsModel?, error: ERROR) -> Unit) {
        remote.fetchAboutUs(result)
    }

    override fun splash(
        userId: Int,
        firebaseId: String,
        result: (status: Boolean, data: SplashModel?, error: ERROR) -> Unit
    ) {
        remote.splash(userId, firebaseId, result)
    }

    override fun FAQ(result: (status: Boolean, data: FaqRes?, error: ERROR) -> Unit) {
        remote.FAQ(  result)
    }

    override fun fetchTac(result: (status: Boolean, data: TacModel?, error: ERROR) -> Unit) {
        remote.fetchTac(result)
    }

    override fun fetchDepositHistory(
        userId: Int,
        result: (status: Boolean, data: DepositModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDepositHistory(userId, result)
    }

    override fun uploadQid(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.uploadQid(userId, path, result)
    }

    override fun fetchNotifications(
        userId: Int,
        result: (status: Boolean, data: ArrayList<NotificationModel>, error: ERROR) -> Unit
    ) {
        remote.fetchNotifications(userId, result)
    }

    override fun submitFeedback(
        userId: Int,
        auctionId: Int,
        content: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.submitFeedback(userId, auctionId, content, result)
    }

    override fun updateLastActive(
        userId: Int,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updateLastActive(userId, result)
    }

    override fun resendOtp(
        mobile: String,
        hash: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.resendOtp(mobile, hash, result)
    }

    override fun uploadProfilePhoto(
        userId: Int,
        path: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.uploadProfilePhoto(userId, path, result)
    }

    override fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updateProfile(userId, name, email, password, result)
    }

    override fun updatePassword(
        userId: Int,
        password: String,
        result: (status: Boolean, error: ERROR) -> Unit
    ) {
        remote.updatePassword(userId, password, result)
    }

    override fun fetchProfile(
        userId: Int,
        result: (status: Boolean, data: ProfileModel?, error: ERROR) -> Unit
    ) {
        remote.fetchProfile(userId, result)
    }

    override fun addToWishList(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: String?, error: ERROR) -> Unit
    ) {
        remote.addToWishList(userId, auctionId, result)
    }

    override fun placeBid(
        userId: Int,
        auctionId: Int,
        amount: Double,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.placeBid(userId, auctionId, amount, result)
    }

    override fun wishingBids(
        userId: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.wishingBids(userId, result)
    }
    override fun winningBids(
            userId: Int,

            result: (status: Boolean, data: ArrayList<WinningBidsDetails>, error: ERROR) -> Unit
    ) {
        remote.winningBids(userId, result)
    }
    override fun winningBidsPayment(
            userId: Int,
            bidId:Int,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    ) {
        remote.winningBidsPayment(userId, bidId,result)
    }
    override fun doPayment(
            userId: Int,
            amount:String,
            result: (status: Boolean, data: String, error: ERROR) -> Unit
    ) {
        remote.doPayment(userId, amount,result)
    }
    override fun myBids(
        userId: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.myBids(userId, result)
    }

    override fun searchAuctions(
        userId: Int,
        searchQuery: String,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.searchAuctions(userId, searchQuery, result)
    }

    override fun verifyOtp(
        mobile_no: String,
        otp: String,
        firebaseId: String,
        result: (message: Int, data: String, error: ERROR,status:Boolean) -> Unit
    ) {
        remote.verifyOtp(mobile_no, otp, firebaseId, result)
    }

    override fun fetchDetails(
        userId: Int,
        auctionId: Int,
        result: (status: Boolean, data: DetailsModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDetails(userId, auctionId, result)
    }

    override fun fetchAuctions(
        userId: Int,
        categoryId: Int,
        filter: Int,
        offset: Int,
        limit: Int,
        result: (status: Boolean, data: ArrayList<AuctionItemModel>, error: ERROR) -> Unit
    ) {
        remote.fetchAuctions(userId, categoryId, filter, offset, limit, result)
    }

    override fun fetchDashboard(
        firebaseId: String,
        result: (status: Boolean, data: DashboardModel?, error: ERROR) -> Unit
    ) {
        remote.fetchDashboard(firebaseId, result)
    }




    override fun fetchFavAutionsIDs(
            result: (status: Boolean, data: FavAutionIdsRes?, error: ERROR) -> Unit
    ) {
        remote.fetchFavAutionsIDs( result)
    }

    override fun login(
        qatarId: String,
        phone: String,
        hash: String,
        result: (status: Boolean, data: Int, error: ERROR) -> Unit
    ) {
        remote.login(qatarId, phone, hash, result)

    }

    companion object {
        const val OTP_TYPE_REGISTER = 1
        const val OTP_TYPE_LOGIN = 2
        const val OTP_TYPE_MORE= 3
        const val OTP_TYPE_MORE_EXPIRE=4
        const val OTP_TYPE_BLOCKED=5
        const val OTP_TYPE_INVALID=0
    }
}
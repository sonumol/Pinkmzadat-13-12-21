package com.platinummzadat.qa.networking;
import androidx.annotation.Keep
import com.platinummzadat.qa.data.models.*
import kotlinx.coroutines.Deferred

import retrofit2.http.*
import retrofit2.http.POST

@Keep
interface CoroutineRestfulAPI {
    @GET("get_minimum_deposit_amount")
    fun getAmount(): Deferred<AmountModel>

    @FormUrlEncoded
    @POST("faq")
    fun getFAQ(@Field("lang") lang: String
    ): Deferred<FaqRes>


    @POST("get_wishlist")
    fun getFavAutionsIds(
            @Header("Token") token: String
    ): Deferred<FavAutionIdsRes>

    @FormUrlEncoded
    @POST("company_registration")
    fun companyRegistration(
            @Header("Token") token: String,
            @Field("cr_no") cr_no: String,
            @Field("company_name") company_name:String,
            @Field("company_sign_id") company_sign_id:String
    ): Deferred<CompanyRegistrationRes>

    @FormUrlEncoded
    @POST("check_computer_card")
    fun checkComputerCard(
            @Header("Token") token: String,
            @Field("lang") lang: String,
            @Field("cr_no") cr_no: String
    ): Deferred<ComputerCheckRes>


    @FormUrlEncoded
    @POST("check_company_registration")
    fun checkCompanyRegistration(
            @Header("Token") token: String,
            @Field("lang") lang: String
    ): Deferred<CheckCompanyRegisterRes>

    @FormUrlEncoded
    @POST("refund_request_new")
    fun getRefundRequest(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String,
            @Field("deposit_id") depositid: String
    ): Deferred<RefundRequestRes>


    @FormUrlEncoded
    @POST("profile")
    fun fetchProfile(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<ProfileModelRes>


    @FormUrlEncoded
    @POST("get_payment")
    fun fetchCompanyFees(
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<CompanyFeesRespose>



    @FormUrlEncoded
    @POST("add_bid")
    fun placeBid(
            @Header("Token") token: String,
            @Field("auction_id") auction_id: Int,
            @Field("amount") amount: Double,
            @Field("type") type: Int,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<DetailsModelRes>




    @FormUrlEncoded
    @POST("add_feedback")
    fun submitFeedback(
            @Header("Token") token: String,
            @Field("content") content: String,
            @Field("auction_id") auction_id: Int,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<CommonResponse>

    @FormUrlEncoded
    @POST("wishlist")
    fun wishingBids(
            @Header("Token") token: String,
            @Field("wishlist_id") wislist_id:String,
            @Field("imei") imei: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<MyBidsRes>

        @FormUrlEncoded
    @POST("view_auction")
    fun wishingBids2(
            @Field("user_id") user_id: String,
            @Field("wish_user_id") wish_user_id: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<MyBidsRes>

    @FormUrlEncoded
    @POST("winning_bids")
    fun winningBids(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<WinninggDatasRes>

    @FormUrlEncoded
    @POST("whishlist")
    fun fetchwishingBids(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<WinninggDatasRes>

    @FormUrlEncoded
    @POST("winning_bid_payment_ios")
    fun winningBidsPayment(
            @Header("Token") token: String,
            @Field("bid_id") bid_id: Int,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<WinninggBidsPaymentDatasRes>


    @FormUrlEncoded
    @POST("payment_ios_new")
    fun doPayment(
            @Header("Token") token: String,
            @Field("amount") amount: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<WinninggBidsPaymentDatasRes>

    @FormUrlEncoded
    @POST("my_bids")
    fun myBids(
            @Header("Token") token: String,
            @Field("lang") lang: String): Deferred<MyBidsRes>


    @FormUrlEncoded
    @POST("search_data")
    fun searchAuctions(
            @Header("Token") token: String,
            @Field("search_data") search_data: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<MyBidsRes>


    @FormUrlEncoded
    @POST("auction_details_android")
    fun fetchDetails(
            @Header("Token") token: String,
            @Field("auction_id") auction_id: Int,
            @Field("device_type") device_type: Int,
            @Field("type") type: Int,
            @Field("lang") lang: String): Deferred<fetchDetailsRes>

    @FormUrlEncoded
    @POST("view_auction")
    fun fetchAuctions(
            @Header("Token") token: String,
            @Field("category_id") category_id: Int,
            @Field("filter") filter: Int,
            @Field("offset") offset: Int,
            @Field("limit") limit: Int,
            @Field("type") type: Int,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<FetchAuctionsRes>



    @FormUrlEncoded
    @POST("login_android")
    fun login(
            @Field("qatar_id") qatar_id: String,
            @Field("mobile_no") mobile_no: String,
            @Field("cr_num") cr_no: String,
            @Field("app_hash") app_hash: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<LoginRes>


    @FormUrlEncoded
    @POST("add_wishlist")
    fun addToWishList(
            @Header("Token") token: String,
            @Field("auction_id") auction_id: Int,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<addWishRes>

    @FormUrlEncoded
    @POST("get_aboutus")
    fun fetchAboutUs(
            @Field("lang") lang: String): Deferred<AboutUsModelRes>


    @FormUrlEncoded
    @POST("resend_otp")
    fun resendOtp(
            @Field("mobile_no") mobile_no: String,
            @Field("app_hash") app_hash: String,
            @Field("device_type") device_type: Int): Deferred<ResendRes>



    @FormUrlEncoded
    @POST("last_active")
    fun updateLastActive(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<CommonResponse>

    @FormUrlEncoded
    @POST("get_contactus")
    fun fetchContactUs(
            @Field("lang") lang: String): Deferred<ContactUsModelRes>



    @FormUrlEncoded
    @POST("dashboard")
    fun fetchDashboard(
            @Header("Token") token: String,
            @Field("firebase_id") firebase_id: String,
            @Field("device_type") device_type: Int,
            @Field("type") type: Int,
            @Field("lang") lang: String): Deferred<DashResponse>

    @FormUrlEncoded
    @POST("splash")
    fun splash(
           @Header("Token") token: String,
            @Field("firebase_id") firebase_id: String,
            @Field("device_type") device_type: Int,
           @Field("user_type") user: Int,
            @Field("lang") lang: String): Deferred<SplashModelRes>

    @FormUrlEncoded
    @POST("terms_of_use")
    fun fetchTac(
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<TacModelRes>



    @FormUrlEncoded
    @POST("set_profile")
    fun updateProfile(
            @Header("Token") token: String,
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String): Deferred<SetprofileRes>



    @FormUrlEncoded
    @POST("set_password")
    fun updatePassword(
            @Header("Token") token: String,
            @Field("password") password: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<CommonResponse>

    @FormUrlEncoded
    @POST("check_otp")
    fun verifyOtp(
            @Field("otp") otp: String,
            @Field("mobile_no") mobile_no: String,
            @Field("firebase_id") firebase_id: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<VerifyResponse>



    @FormUrlEncoded
    @POST("get_deposit_amount")
    fun fetchDepositHistory(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<DepositModelRes>



    @FormUrlEncoded
    @POST("notifications")
    fun fetchNotifications(
            @Header("Token") token: String,
            @Field("device_type") device_type: Int,
            @Field("lang") lang: String): Deferred<NotificationModelRes>
//    @FormUrlEncoded
//    @POST("category_list")
//    fun category_list(
////        @Header("Token") token: String,
////        @Field("firebase_id") firebase_id: String,
////        @Field("device_type") device_type: Int,
//        @Field("type") type: Int,
//        @Field("lang") lang: String): Deferred<category_listModelRes>
}


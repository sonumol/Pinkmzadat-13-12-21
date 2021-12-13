package com.platinummzadat.qa.views.root.details

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.DetailsModel

interface DetailsContract {
    interface View : BaseView<Presenter> {
        fun refreshing()
        fun refreshFailed()
        fun refreshData(data: DetailsModel)
        fun showData(data: DetailsModel)
        fun showBidPlaced(data: DetailsModel)
        fun showBidExpired()
        fun showErrorPlacingBid()
        fun showAddedToWishList(data: String)
        fun showErrorAddingToWishList()
        fun showFeedbackSubmitted()
        fun showFeedbackSubmissionFailed()
    }

    interface Presenter : BasePresenter {
        fun fetchData(itemId: Int,type:Int)
        fun placeBid(
            auctionId: Int,
            amount: Double,
            type: Int
        )

        fun addToWishList(
            auctionId: Int
        )

        fun refreshAuction(itemId: Int,type: Int)
        fun submitFeedback(itemId: Int, feedback: String)
    }
}
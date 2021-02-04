package com.platinummzadat.qa.views.root.auctions

import com.platinummzadat.qa.BasePresenter
import com.platinummzadat.qa.BaseView
import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.data.models.WishingBiData

interface AuctionsContract {
    interface View : BaseView<Presenter> {
        fun showData(dataSet: ArrayList<AuctionItemModel>)
     //   fun showDataWishing(dataSet: ArrayList<WishingBiData>)

        fun refreshData(dataSet: ArrayList<AuctionItemModel>, requestTime: Long)
    //    fun refreshDataWishing(dataSet: ArrayList<WishingBiData>, requestTime: Long)
        fun showBidPlaced()
        fun showErrorPlacingBid()
    }

    interface Presenter : BasePresenter {

        fun fetchAuctions(categoryId: Int, filter: Int, offset: Int, limit: Int, requestTime: Long, refresh: Boolean)
        fun searchAuctions(searchQuery: String, requestTime: Long, refresh: Boolean)
        fun fetchMyBids(requestTime: Long, refresh: Boolean)
        fun fetchWishingBids(requestTime: Long, refresh: Boolean,appHash:String)

        fun placeBid(
            auctionId: Int,
            amount: Double
        )
    }
}
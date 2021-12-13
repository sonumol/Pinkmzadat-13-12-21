package com.platinummzadat.qa.views.root

import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.data.models.DashboardItemModel
import com.platinummzadat.qa.data.models.WinningBidsDetails
import com.platinummzadat.qa.views.root.depositamount.DepositAmountFragment
import com.platinummzadat.qa.views.root.drawer.MzNav

/**
 * Created by WOLF
 * at 12:55 on Tuesday 02 April 2019
 */
interface MzFragmentListener {
    fun onError(callback: () -> Unit)
    fun onErrorWithMessage(message: String, actionText: String?, callback: () -> Unit)
    fun onErrorWithAutoHideMessage(message: String, actionText: String?, callback: () -> Unit)
    fun refreshProfileInfo()
    fun setTitle(title: String)
    fun onSelectDashboardItem(item: DashboardItemModel)
    fun onSelectDepositAmount(item: DepositAmountFragment)
    fun onSelectAuctionItem(item: AuctionItemModel)
    fun onSelectWinningBidItem(item: WinningBidsDetails)
    fun onSelectNavItem(item: MzNav)
    fun onapprove(approve:Int)
}
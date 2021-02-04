package com.platinummzadat.qa.views.root.auctions

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.databinding.ItemAuctionGridBinding
import com.platinummzadat.qa.databinding.ItemAuctionLinearBinding
import kotlinx.android.synthetic.main.fragment_details.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.getTimeStamp

const val AUCTION_ADAPTER_LIST = 1
const val AUCTION_ADAPTER_GRID = 2

class AuctionAdapter(
    val context:Context,
    val dataSet: ArrayList<AuctionItemModel> = ArrayList(),
    private val type: Int = AUCTION_ADAPTER_LIST,
    private val fnOnClickItem: ((AuctionItemModel) -> Unit)? = null,
    private val fnOnClickBidNow: ((AuctionItemModel) -> Unit)? = null
) : RecyclerView.Adapter<AuctionAdapter.VH>() {
    override fun getItemId(position: Int): Long {
        return dataSet[position].price.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun refresh(newData: ArrayList<AuctionItemModel>) {
        for (i in 0 until dataSet.size) {
            val newItem = newData.find { it.id == dataSet[i].id }
            if (null != newItem) {
                dataSet.removeAt(i)
                dataSet.add(i, newItem)
            }
        }
        notifyDataSetChanged()
    }

    fun insertItems(items: ArrayList<AuctionItemModel>) {
        val startPos = dataSet.size
        dataSet.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }

    fun resetItems(items: ArrayList<AuctionItemModel>) {
        dataSet.removeAll(dataSet)
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                if (AUCTION_ADAPTER_LIST == type) R.layout.item_auction_linear else R.layout.item_auction_grid,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bindData(dataSet[position])

    }


    inner class VH(val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var timer: CountDownTimer? = null
        fun bindData(data: AuctionItemModel) {
            if (AUCTION_ADAPTER_LIST == type) {
                with(itemBinding as ItemAuctionLinearBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }
                    if (data.auction_status==3) {
                        tvTimeLeft.text = data.button_name
                        btnBidNow.text =  data.button_name
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))

                    } else if (data.auction_status==2) {
                        btnBidNow.setText(data.button_name)
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.upComimgBidTimer, 1000L) {
                            override fun onFinish() {
                                btnBidNow.text = root.context.getString(R.string.bid_now)
                                btnBidNow.isEnabled=true
                                btnBidNow.isClickable=true

                                if (null != timer)
                                    timer?.cancel()
                                timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                                    override fun onFinish() {
                                        tvTimeLeft.text = root.context.getString(R.string.expired)
                                        btnBidNow.text = root.context.getString(R.string.expired)
                                        btnBidNow.isEnabled=false
                                        btnBidNow.isClickable=false
                                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                                    }

                                    override fun onTick(millisUntilFinished: Long) {
                                        tvTimeLeft.text =
                                                getTimeStamp(millisUntilFinished)
                                    }
                                }
                                timer?.start()
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                tvTimeLeft.text =
                                    getTimeStamp(millisUntilFinished)
                            }
                        }
                        timer?.start()
                    }else if(data.auction_status==1){
                        btnBidNow.setText(data.button_name)
                        btnBidNow.isEnabled=true
                        btnBidNow.isClickable=true
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                            override fun onFinish() {
                                tvTimeLeft.text = root.context.getString(R.string.expired)
                                btnBidNow.text = root.context.getString(R.string.expired)
                                btnBidNow.isEnabled=false
                                btnBidNow.isClickable=false
                                btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                tvTimeLeft.text =
                                        getTimeStamp(millisUntilFinished)
                            }
                        }
                        timer?.start()
                    }else if(data.auction_status==4){
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft.text = data.button_name
                    }
                    item = data

                }
            } else {
                /*           with(itemBinding as ItemAuctionGridBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }

                    if (data.expired) {
                        tvTimeLeft.text = root.context.getString(R.string.expired)
                        btnBidNow.setText(root.context.getString(R.string.expired))
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                    } else {
                        btnBidNow.setText(root.context.getString(R.string.bid_now))
                        try{
                            if (null != timer)
                                timer?.cancel()
                            timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                                override fun onFinish() {
                                    tvTimeLeft.text = root.context.getString(R.string.expired)
                                    btnBidNow.text = root.context.getString(R.string.expired)
                                    btnBidNow.isEnabled=false
                                    btnBidNow.isClickable=false
                                }

                                override fun onTick(millisUntilFinished: Long) {
                                    tvTimeLeft.text =
                                            getTimeStamp(millisUntilFinished)
                                }
                            }
                            timer?.start()
                        }catch (e:Exception){

                        }

                    }
                    btnBidNow.setText(data.button_name)
                    item = data
                }*/
                with(itemBinding as ItemAuctionGridBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }
                    if (data.auction_status==3) {
                        tvTimeLeft.text = data.button_name
                        btnBidNow.text =  data.button_name
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                    } else if (data.auction_status==2) {
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setText(data.button_name)
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.upComimgBidTimer, 1000L) {
                            override fun onFinish() {
                                btnBidNow.text = root.context.getString(R.string.bid_now)
                                btnBidNow.isEnabled=true
                                btnBidNow.isClickable=true
                                if (null != timer)
                                    timer?.cancel()
                                timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                                    override fun onFinish() {
                                        tvTimeLeft.text = root.context.getString(R.string.expired)
                                        btnBidNow.text = root.context.getString(R.string.expired)
                                        btnBidNow.isEnabled=false
                                        btnBidNow.isClickable=false
                                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                                    }

                                    override fun onTick(millisUntilFinished: Long) {
                                        tvTimeLeft.text =
                                                getTimeStamp(millisUntilFinished)
                                    }
                                }
                                timer?.start()

                            }

                            override fun onTick(millisUntilFinished: Long) {
                                tvTimeLeft.text =
                                        getTimeStamp(millisUntilFinished)
                            }
                        }
                        timer?.start()
                    }else if(data.auction_status==1){
                        btnBidNow.setText(data.button_name)
                        btnBidNow.isEnabled=true
                        btnBidNow.isClickable=true
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                            override fun onFinish() {
                                tvTimeLeft.text = root.context.getString(R.string.expired)
                                btnBidNow.text = root.context.getString(R.string.expired)
                                btnBidNow.isEnabled=false
                                btnBidNow.isClickable=false
                                btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                            }

                            override fun onTick(millisUntilFinished: Long) {
                                tvTimeLeft.text =
                                        getTimeStamp(millisUntilFinished)
                            }
                        }
                        timer?.start()
                    }else if(data.auction_status==4){
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft.text = data.button_name
                    }
                    item = data
                }
            }
        }

    }
}
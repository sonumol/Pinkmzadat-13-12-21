package com.platinummzadat.qa.views.root.auctions

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.AuctionItemModel
import com.platinummzadat.qa.databinding.ItemAuctionGridBinding
import com.platinummzadat.qa.databinding.ItemAuctionLinearBinding
import com.bumptech.glide.request.RequestOptions
import com.platinummzadat.qa.MApp
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
            val requestOptions = RequestOptions().error(R.drawable.bg_dark)
            //val imageView = findViewById(R.id.iv_click_me) as ImageView
//     Toast.makeText(MApp.applicationContext(),"a"+data.auction_status,Toast.LENGTH_LONG).show()
            if (AUCTION_ADAPTER_LIST == type) {

                with(itemBinding as ItemAuctionLinearBinding) {
                    root.onClick {
                        fnOnClickItem?.invoke(data)
                    }
                    Glide.with(context)
                        .load(data.imgUrl)
                        .apply(requestOptions)
                        .into(ivItem)
                    btnBidNow.onClick {
                        fnOnClickBidNow?.invoke(data)
                    }
                    if (data.auction_status==3) {
                       // Toast.makeText(context,"1",Toast.LENGTH_LONG).show()




                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)



                        tvTimeLeft1.text = data.button_name
                        btnBidNow.text =  data.button_name
                        tvTimeLeft.visibility=View.GONE

                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false


                    } else if (data.auction_status==2) {
                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)
                        //Toast.makeText(context,"2",Toast.LENGTH_LONG).show()
                        btnBidNow.setText(data.button_name)
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        tvTimeLeft1.setText (root.context.getString(R.string.starton)+" ")

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
                        //Toast.makeText(context,"3",Toast.LENGTH_LONG).show()

                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)


                        btnBidNow.setText(data.button_name)
                        tvTimeLeft1.text =root.context.getString(R.string.expireson)+" ";
                        btnBidNow.isEnabled=true
                        btnBidNow.isClickable=true
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                            override fun onFinish() {

                                Glide.with(context)
                                    .load(data.imgUrl)
                                    .apply(requestOptions)
                                    .into(ivItem)


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
                       // Toast.makeText(context,"4",Toast.LENGTH_LONG).show()

                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)

                        tvTimeLeft.visibility=View.GONE
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft1.text = data.button_name
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

                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)
                        tvTimeLeft.visibility=View.GONE

                        tvTimeLeft1.text = data.button_name
                        btnBidNow.text =  data.button_name
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                    } else if (data.auction_status==2) {


                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)


                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft1.setText (root.context.getString(R.string.starton)+" ")
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.upComimgBidTimer, 1000L) {
                            override fun onFinish() {

                                Glide.with(context)
                                    .load(data.imgUrl)
                                    .apply(requestOptions)
                                    .into(ivItem)


                                btnBidNow.text = root.context.getString(R.string.bid_now)
                                btnBidNow.isEnabled=true
                                btnBidNow.isClickable=true
                                if (null != timer)
                                    timer?.cancel()
                                timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                                    override fun onFinish() {
                                        Glide.with(context)
                                            .load(data.imgUrl)
                                            .apply(requestOptions)
                                            .into(ivItem)


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
                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)
                        //Toast.makeText(context,"5",Toast.LENGTH_LONG).show()
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft1.setText (root.context.getString(R.string.expireson)+" ")
                        btnBidNow.isEnabled=true
                        btnBidNow.isClickable=true
                        if (null != timer)
                            timer?.cancel()
                        timer = object : CountDownTimer(data.millisUntilExpiry, 1000L) {
                            override fun onFinish() {
                                Glide.with(context)
                                    .load(data.imgUrl)
                                    .apply(requestOptions)
                                    .into(ivItem)
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
                        //Toast.makeText(context,"6",Toast.LENGTH_LONG).show()
                        Glide.with(context)
                            .load(data.imgUrl)
                            .apply(requestOptions)
                            .into(ivItem)
                        btnBidNow.isEnabled=false
                        btnBidNow.isClickable=false
                        btnBidNow.setTextColor(context.resources.getColor(R.color.solid_black))
                        btnBidNow.setText(data.button_name)
                        tvTimeLeft1.text = data.button_name
                        tvTimeLeft.visibility=View.GONE
                    }
                    item = data
                }
            }
        }

    }
}
package com.platinummzadat.qa.views.root.depositamount

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.platinummzadat.qa.MApp
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.DepositDetailsModel
import com.platinummzadat.qa.data.models.RefundRequestRes
import com.platinummzadat.qa.databinding.ItemDepositAmountBinding
import com.platinummzadat.qa.views.root.RootActivity
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_my_deposit_amount.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import raj.nishin.wolfrequest.WolfRequest.Companion.context


class DepositAmountAdapter(
    private val dataSet: ArrayList<DepositDetailsModel> = ArrayList(),
    private val fnOnClickItem: ((DepositDetailsModel) -> Unit)? = null

) : RecyclerView.Adapter<DepositAmountAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_deposit_amount,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            holder.itemBinding.item = this
            holder.itemBinding.root.onClick {
                fnOnClickItem?.invoke(this@with)
            }
            val a=dataSet[position].refund_button
            val deposit_id=dataSet[position].deposit_id
            if(a=="0")
            {
                holder.itemBinding.tvrefund.visibility=View.INVISIBLE
            }
            else if(a=="1")
            {
                holder.itemBinding.tvrefund.visibility=View.VISIBLE
                holder.itemBinding.tvrefund.isEnabled=true
                holder.itemBinding.tvrefund.isClickable=true
                holder.itemBinding.tvrefund.setOnClickListener {
                    holder.itemBinding.tvrefund.setText("Refunding...")

                    MApp.MzRepo.getRefundRequest(deposit_id) { status, data, error ->
                        if( status=="true")  {
                        data?.let {
                            Toast.makeText(MApp.applicationContext(),""+data.message,Toast.LENGTH_LONG).show()
                            holder.itemBinding.tvrefund.setText("Refund")
                        }
                    }
                    }
                }

            }
            else if(a=="2")
            {
                holder.itemBinding.tvrefund.setText("Refunded")
            }

           // Toast.makeText(MApp.applicationContext(),"jjj"+a,Toast.LENGTH_LONG).show()

        }


    }

    private fun showRefundData(data: RefundRequestRes): Any {
        Toast.makeText(MApp.applicationContext(),""+data.message,Toast.LENGTH_LONG).show()

           //Snackbar.make((context as Activity).findViewById(android.R.id.content), data.message,Snackbar.LENGTH_INDEFINITE)
//
        return true;
    }

    inner class VH(val itemBinding: ItemDepositAmountBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


}
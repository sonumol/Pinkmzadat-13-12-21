package com.platinummzadat.qa.views.root.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.MApp
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.DashboardItemModel
import com.platinummzadat.qa.databinding.ItemDashboardBinding
import com.platinummzadat.qa.profilePhotoUrl
import org.jetbrains.anko.sdk27.coroutines.onClick
import raj.nishin.wolfpack.loadAvatar


class DashboardAdapter(
    private val dataSet: ArrayList<DashboardItemModel> = ArrayList(),
    private val fnOnClickItem: ((DashboardItemModel) -> Unit)? = null
) : RecyclerView.Adapter<DashboardAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_dashboard,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            //holder.itemBinding.item = this
            holder.itemBinding.clRoot.onClick {
                fnOnClickItem?.invoke(this@with)
            }
            holder.itemBinding.tvName.setText(dataSet[position].name)
            holder.itemBinding.ivItem.loadAvatar(dataSet[position].imgUrl)
             val count=dataSet[position].count
           // Toast.makeText(MApp.applicationContext(),""+count,Toast.LENGTH_LONG).show()
 if (count==0)
 {
    holder.itemBinding.tvCount.visibility=View.GONE
 }
            else
 {
     holder.itemBinding.tvCount.visibility=View.VISIBLE
     holder.itemBinding.tvCount.setText(""+count)
 }
          //  holder.itemBinding.tvCount.setText(dataSet[position].count)

        }

    }

    inner class VH(val itemBinding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
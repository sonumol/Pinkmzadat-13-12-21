package com.platinummzadat.qa.views.root.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.DashboardItemModel
import com.platinummzadat.qa.databinding.ItemDashboardBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


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
            holder.itemBinding.item = this
            holder.itemBinding.clRoot.onClick {
                fnOnClickItem?.invoke(this@with)
            }

        }

    }

    inner class VH(val itemBinding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
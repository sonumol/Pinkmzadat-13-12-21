package com.platinummzadat.qa.views.root.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.R
import com.platinummzadat.qa.databinding.ItemSearchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class TrendingSearchAdapter(
    private val dataSet: ArrayList<String> = ArrayList(),
    private val fnOnClickItem: ((String) -> Unit)? = null
) : RecyclerView.Adapter<TrendingSearchAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search,
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

        }

    }

    inner class VH(val itemBinding: ItemSearchBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
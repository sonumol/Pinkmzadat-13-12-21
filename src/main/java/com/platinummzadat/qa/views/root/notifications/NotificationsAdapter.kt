package com.platinummzadat.qa.views.root.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.platinummzadat.qa.R
import com.platinummzadat.qa.data.models.NotificationModel
import com.platinummzadat.qa.databinding.ItemNotificationBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class NotificationsAdapter(
    private val dataSet: ArrayList<NotificationModel> = ArrayList(),
    private val fnOnClickItem: ((NotificationModel) -> Unit)? = null
) : RecyclerView.Adapter<NotificationsAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_notification,
                parent,
                false
            )
        )

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(dataSet[position]) {
            holder.itemBinding.item = this
            holder.itemBinding.llContent.onClick {
                fnOnClickItem?.invoke(this@with)
            }

        }

    }

    inner class VH(val itemBinding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
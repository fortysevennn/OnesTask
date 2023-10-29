package com.tugaypamuk.app.onestask.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugaypamuk.app.onestask.data.local.entitiy.OnesLogEntitiy
import com.tugaypamuk.app.onestask.databinding.RowLogsBinding

class LogsAdapter : RecyclerView.Adapter<LogsAdapter.LogsViewHolder>() {

    class LogsViewHolder(var logsView : RowLogsBinding) : RecyclerView.ViewHolder(logsView.root)

    private val diffUtil = object  : DiffUtil.ItemCallback<OnesLogEntitiy>(){
        override fun areItemsTheSame(oldItem: OnesLogEntitiy, newItem: OnesLogEntitiy): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OnesLogEntitiy, newItem: OnesLogEntitiy): Boolean {
            return oldItem == newItem
        }

    }
    private val listDiffer = AsyncListDiffer(this,diffUtil)
    var list : MutableList<OnesLogEntitiy>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            logsView = RowLogsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        holder.logsView.logs = list[position]
    }

}
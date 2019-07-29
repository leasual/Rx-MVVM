package com.common.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.common.core.extensions.basicDiffUtil

class SingleItemAdapter<T> (private val layoutId: Int, val bindItem: (itemView: View, model: T, position: Int) -> Unit,
                            private val onItemClick: ((model: T, position: Int) -> Unit),
                            areItemsTheSame: ((T, T) -> Boolean) = { _, _ -> false }, areContentsTheSame: ((T, T) -> Boolean) = { _, _ -> false })
    : RecyclerView.Adapter<SingleItemAdapter<T>.RecyclerViewHolder>() {

    var dataList: List<T> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = areItemsTheSame,
        areContentsTheSame = areContentsTheSame
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindItem(holder.itemView, dataList[position], position)
        holder.itemView.setOnClickListener { onItemClick.invoke(dataList[position], position) }
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}
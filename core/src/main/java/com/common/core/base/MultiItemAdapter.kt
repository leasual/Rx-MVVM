package com.common.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.common.core.extensions.basicDiffUtil

class MultiItemAdapter(val getItemView: (model: Any) -> Int, val bindItem: (itemView: View, model: Any, position: Int) -> Unit,
                       private val onItemClick: ((model: Any, position: Int) -> Unit),
                       areItemsTheSame: ((Any, Any) -> Boolean) = { _, _ -> false }, areContentsTheSame: ((Any, Any) -> Boolean) = { _, _ -> false })
    : RecyclerView.Adapter<MultiItemAdapter.RecyclerViewHolder>() {

    var dataList: List<Any> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = areItemsTheSame,
        areContentsTheSame = areContentsTheSame
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int = getItemView(dataList[position])

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindItem(holder.itemView, dataList[position], position)
        holder.itemView.setOnClickListener { onItemClick.invoke(dataList[position], position) }
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}
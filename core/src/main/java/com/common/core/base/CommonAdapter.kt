package com.common.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.common.core.extensions.basicDiffUtil

class CommonAdapter(private val layoutIds: Array<Int>, private val modelTypes: Array<Class<*>>,
                    val bindItem: (itemView: View, model: Any, position: Int) -> Unit,
                    private val onItemClick: ((model: Any, position: Int) -> Unit),
                    areItemsTheSame: ((Any, Any) -> Boolean) = { _, _ -> true },
                    areContentsTheSame: ((Any, Any) -> Boolean) = { _, _ -> true })
    : RecyclerView.Adapter<CommonAdapter.RecyclerViewHolder>() {

    var dataList: MutableList<Any> by basicDiffUtil(
        arrayListOf(),
        areItemsTheSame = areItemsTheSame,
        areContentsTheSame = areContentsTheSame
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        var layoutId = 0
        for (index in modelTypes.indices) {
            val classType = modelTypes[index].simpleName
            val dataType = dataList[position].javaClass.simpleName
            //Log.d("test", "index= $index classType= $classType position= $position classType= ${dataList[position].javaClass.simpleName}")
            if (classType == dataType) {
                layoutId = layoutIds[index]
                break
            }
        }
        if (layoutId == 0) {
            throw IllegalArgumentException("it can't find this type's layout")
        }
        return layoutId
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindItem(holder.itemView, dataList[position], position)
        holder.itemView.setOnClickListener { onItemClick.invoke(dataList[position], position) }
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}
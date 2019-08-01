package com.common.rxmvvm.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.common.core.extensions.basicDiffUtil
import com.common.core.extensions.inflate
import com.common.rxmvvm.R
import com.common.rxmvvm.models.FeedData
import kotlinx.android.synthetic.main.listitem_feed.view.*

class FeedAdapter(private val listener: (FeedData) -> Unit): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var feedList: MutableList<FeedData> by basicDiffUtil(
        arrayListOf(),
        areItemsTheSame = {old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.listitem_feed, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedData = feedList[position]
        holder.bind(feedData)
        holder.itemView.setOnClickListener { listener(feedData) }
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(feedData: FeedData) {
            itemView.tv_desc.text = feedData.desc
            itemView.tv_type.text = feedData.type
            itemView.tv_date.text = feedData.publishedAt
        }
    }
}
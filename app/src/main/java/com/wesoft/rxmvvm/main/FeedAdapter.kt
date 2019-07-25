package com.wesoft.rxmvvm.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wesoft.rxmvvm.R
import com.wesoft.rxmvvm.base.extension.basicDiffUtil
import com.wesoft.rxmvvm.base.extension.inflate
import com.wesoft.rxmvvm.data.base.FeedData
import kotlinx.android.synthetic.main.listitem_feed.view.*

class FeedAdapter(private val listener: (FeedData) -> Unit): RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var feedList: List<FeedData> by basicDiffUtil(
        emptyList(),
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
package kr.co.pod.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_feed.view.*
import kr.co.pod.R
import kr.co.pod.model.Feed

class FeedAdapter(data: List<Feed>): BaseAdapter() {
    private val dataList = mutableListOf<Feed>()

    init {
        if (data != null) {
            dataList.addAll(data)
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Feed {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<Feed>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
            holder = DataViewHolder(view)
            view?.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }

        holder.bindData(getItem(position))

        return view
    }

    /**
     * Static view items holder
     */
    class DataViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun bindData(feed: Feed) {
            Glide.with(view.context)
                .load(feed.imageUrl)
                .centerInside()
                .into(view.imageView)

            val uri = Uri.parse(feed.originLink)

            view.linkTextView.text = uri.host
            view.titleTextView.text = feed.title
            view.dateTextView.text = feed.registerDateTime

            for(tag in feed.tags) {
                val tagChip = Chip(view.context)
                tagChip.text = tag
                view.tagChips.addView(tagChip)
            }
        }

    }
}
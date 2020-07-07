package kr.co.pod.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_bookmark.view.*
import kr.co.pod.R
import kr.co.pod.model.FeedRealm

class BookMarkAdapter(private val feedList :List<FeedRealm>,
private val feedClick :(FeedRealm) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return BookMarkViewHolder(view)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookMarkViewHolder).bind(feedList[position], feedClick)
    }

    inner class BookMarkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(feed: FeedRealm, feedClick: (FeedRealm) -> Unit) {
            Glide.with(itemView.context)
                .load(feed.imageUrl)
                .centerCrop()
                .into(itemView.feedImageView)

            itemView.titleTextView.text = feed.title

            val uri = Uri.parse(feed.originLink)
            itemView.linkTextView.text = uri.host

            itemView.setOnClickListener {
                feedClick(feed)
            }
        }
    }
}
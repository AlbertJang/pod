package kr.co.pod.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_book_mark.*
import kr.co.pod.R
import kr.co.pod.adapter.BookMarkAdapter
import kr.co.pod.model.Feed
import kr.co.pod.model.FeedRealm

class BookMarkFragment : Fragment() {
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_mark, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get feed all
        realm?.executeTransaction { realm ->
            val result = realm.where(FeedRealm::class.java).findAll()

            val bookMarkAdapter = BookMarkAdapter(result.toList()) { feedRealm ->
                // feed click
                val feed = feedRealmToFeed(feedRealm)

                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra("feed", feed)
                startActivity(intent)
            }

            bookMarkRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            bookMarkRecyclerView?.adapter = bookMarkAdapter
        }
    }

    private fun feedRealmToFeed(feedRealm: FeedRealm): Feed {
        val feed = Feed()

        feed.title = feedRealm.title
        feed.imageUrl = feedRealm.imageUrl
        feed.link = feedRealm.link
        feed.originLink = feedRealm.originLink
        feed.description = feedRealm.description
        feed.pubDate = feedRealm.pubDate
        feed.registerDateTime = feedRealm.registerDateTime

        feed.tags = feedRealm.tags.toList()

        return feed
    }
}
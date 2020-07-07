package kr.co.pod.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.yalantis.library.KolodaListener
import kotlinx.android.synthetic.main.fragment_home.*
import kr.co.pod.R
import kr.co.pod.adapter.FeedAdapter
import kr.co.pod.model.Feed

class HomeFragment : Fragment() {
    private var feedAdapter: FeedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addTab(tabLayout.newTab().setText("추천"))

        // get tags
        val tagDb = FirebaseFirestore.getInstance().collection("tags").get()
            .addOnSuccessListener { tags ->
                for(tag in tags) {
                    tabLayout.addTab(tabLayout.newTab().setText(tag["name"].toString()))
                }
            }

        // get total feed
        val feeds = FirebaseFirestore.getInstance().collection("feed").get()
            .addOnSuccessListener { feeds ->
                val feedList = feeds.toObjects(Feed::class.java)

                for(feed in feedList) {
                    Log.d("HomeFragment", "home title = ${feed.title}")
                }

                feedAdapter = FeedAdapter(feedList)
                koloda.adapter = feedAdapter
            }

        koloda.kolodaListener = object : KolodaListener {
            override fun onCardSingleTap(position: Int) {
                super.onCardSingleTap(position)

                val feed = feedAdapter?.getItem(position + 1)

                Log.d("position", "position = $position")
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra("feed", feed)
                startActivity(intent)
            }
        }

        initTab()
    }

    private fun initTab() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tag = tab?.text.toString()

                FirebaseFirestore.getInstance().collection("feed")
                    .whereArrayContains("tags", tag)
                    .get()
                    .addOnSuccessListener { feeds ->
                        val feedList = feeds.toObjects(Feed::class.java)

                        for(feed in feedList) {
                            Log.d("HomeFragment", "tab title = ${feed.title}")
                        }

                        feedAdapter = FeedAdapter(feedList)
                        koloda.adapter = feedAdapter
                        koloda.reloadPreviousCard()
                        koloda.reloadAdapterData()
                    }
            }
        })
    }
}
package kr.co.pod.view

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_webview.*
import kr.co.pod.R
import kr.co.pod.model.Feed
import kr.co.pod.model.FeedRealm

class WebViewActivity : AppCompatActivity() {
    private var realm: Realm? = null
    private var feed: Feed? = null

    private var isBookMark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        realm = Realm.getDefaultInstance()
        feed = intent.getParcelableExtra("feed")

        initBookMark()
        configureListener()

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.loadUrl(feed?.link)
    }

    private fun configureListener() {
        closeButton?.setOnClickListener {
            finish()
        }

        bookMarkButton?.setOnClickListener {
            if (isBookMark) {
                // 북마크 해제
                realm?.executeTransaction { realm ->
                    val bookMarkFeed = realm.where(FeedRealm::class.java)
                        ?.equalTo("title", feed?.title)
                        ?.equalTo("imageUrl", feed?.imageUrl)
                        ?.equalTo("link", feed?.link)
                        ?.equalTo("originLink", feed?.originLink)
                        ?.equalTo("description", feed?.description)
                        ?.equalTo("pubDate", feed?.pubDate)
                        ?.equalTo("registerDateTime", feed?.registerDateTime)
                        ?.findAll()

                    bookMarkFeed?.deleteAllFromRealm()

                    deleteBookMark()
                }
            } else {
                // 북마크 설정
                realm?.executeTransaction { realm ->
                    if(feed != null) {
                        val feedRealm = realm.createObject(FeedRealm::class.java)

                        feedRealm.title = feed!!.title
                        feedRealm.imageUrl = feed!!.imageUrl
                        feedRealm.link = feed!!.link
                        feedRealm.originLink = feed!!.originLink
                        feedRealm.description = feed!!.description
                        feedRealm.pubDate = feed!!.pubDate
                        feedRealm.registerDateTime = feed!!.registerDateTime

                        val realmList = RealmList<String>()
                        realmList.addAll(feed!!.tags)
                        feedRealm.tags = realmList

                        insertBookMark()
                    }
                }
            }
        }

        shareButton?.setOnClickListener {

        }

        commentButton?.setOnClickListener {
            showCommentBottomFragment()
        }
    }

    private fun initBookMark() {
        val bookMarkFeed = realm?.where(FeedRealm::class.java)
            ?.equalTo("title", feed?.title)
            ?.equalTo("imageUrl", feed?.imageUrl)
            ?.equalTo("link", feed?.link)
            ?.equalTo("originLink", feed?.originLink)
            ?.equalTo("description", feed?.description)
            ?.equalTo("pubDate", feed?.pubDate)
            ?.equalTo("registerDateTime", feed?.registerDateTime)
            ?.findAll()

        if (bookMarkFeed != null && bookMarkFeed.size > 0) {
            insertBookMark()
        } else {
            deleteBookMark()
        }
    }

    private fun insertBookMark() {
        isBookMark = true
        bookMarkButton?.setImageResource(R.drawable.ic_large_bookmark_select)
        Toast.makeText(this, "북마크가 있습니다.", Toast.LENGTH_LONG).show()
    }

    private fun deleteBookMark() {
        isBookMark = false
        bookMarkButton?.setImageResource(R.drawable.ic_large_bookmark)
        Toast.makeText(this, "북마크가 없습니다.", Toast.LENGTH_LONG).show()
    }

    private fun showCommentBottomFragment() {
        val commentFragment = CommentBottomDialog()
        commentFragment.show(supportFragmentManager, CommentBottomDialog.TAG)
    }
}
package kr.co.pod.model

import io.realm.RealmList
import io.realm.RealmObject

open class FeedRealm (
    var title: String = "",
    var imageUrl: String = "",
    var link: String = "",
    var originLink: String = "",
    var description: String = "",
    var pubDate: String = "",
    var registerDateTime: String = "",
    var tags: RealmList<String> = RealmList()
): RealmObject()

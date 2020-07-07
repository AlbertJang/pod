package kr.co.pod.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed(
    var title: String = "",
    var imageUrl: String = "",
    var link: String = "",
    var originLink: String = "",
    var description: String = "",
    var pubDate: String = "",
    var registerDateTime: String = "",
    var tags: List<String> = emptyList()
) :Parcelable
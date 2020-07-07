package kr.co.pod.model

data class Comment(
    val nickName: String,
    val contents: String,
    val regDate: String,
    val comments: ArrayList<Comment>?
)
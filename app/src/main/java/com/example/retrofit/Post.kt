package com.example.retrofit

import com.google.gson.annotations.SerializedName

class Post(
    var userId: Int, var title: String, @SerializedName("body")
    var text: String
) {
    var id = 0

}

class Comments {
    var postId = 0
    var id = 0
    lateinit var name: String
    @SerializedName("body")
    lateinit var text: String
    lateinit var email: String
}
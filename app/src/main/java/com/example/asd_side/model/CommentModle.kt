package com.example.asd_side.model

data class CommentModle(
    var id :String,
    var commentDate: String,
    var commentContent:String?,
    var userName:String,
    var userImageUri : String,
    var commentImageUri : String?,
)
package com.example.asd_side.model


class MealModle(
    var id: String,
    var name: String,
    var category: String,
    var steps: ArrayList<String>,
    var imageChefUir: String,
    var imvMealImage: String,
    var sheName: String,
    var cheefId: String,
    var ratingg: Float,
    var desribtion: String,


    var ingredients: ArrayList<String>,
    var comments: ArrayList<CommentModle>,
    var time: String
)

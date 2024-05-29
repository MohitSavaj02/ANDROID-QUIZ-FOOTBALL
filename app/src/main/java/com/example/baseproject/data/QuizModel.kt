package com.example.baseproject.data

data class QuizModel(
    var question: String? = null,
    var options: ArrayList<String> = ArrayList(),
    var answer: String? = null,
    var hasImage: Boolean = false,
    var imageURL: String? = null,
    var isRightAnswer: Boolean? = null,
)

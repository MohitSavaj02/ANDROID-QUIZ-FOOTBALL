package com.example.baseproject.app

import com.example.baseproject.data.QuizModel

object AppConstant {
    const val RAPID_API_KEY = "4982e1aad423644ca6e9f5ef946ea485"
    const val RAPID_HOST = "api-football-v1.p.rapidapi.com"
    const val BASE_URL = "https://v3.football.api-sports.io/"
    const val PLAY_STORE_LINK: String = "https://play.google.com/store/apps/details?id="
    const val PREF_KEY = "BWPreference"

    const val CONSTANT_AUTH = "Authorization"
    const val SESSION_BROADCAST = "SESSION_BROADCAST"
    const val DATA_TYPE = "data_type"
    const val SCORE_LIST = "score_list"
    const val SOUND_PREF = "sound_pref"
    const val MAX_TIME_PER_QUESTION = 30    //In Second
    const val POINT_PER_RIGHT = 10
    val QUESTIONS_LIST: ArrayList<QuizModel> = ArrayList()
    var QUIZ_25_SET: ArrayList<QuizModel> = ArrayList()
}
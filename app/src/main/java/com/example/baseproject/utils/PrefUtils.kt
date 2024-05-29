package com.example.baseproject.utils

import android.content.SharedPreferences
import com.example.baseproject.app.AppConstant
import com.example.baseproject.data.ScoreModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefUtils(private val sharedPreferences: SharedPreferences) {

    fun saveAuthToken(token: String?) {
        sharedPreferences.edit().putString(AppConstant.CONSTANT_AUTH, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AppConstant.CONSTANT_AUTH, null)
    }


    fun isUserLoggedIn(): Boolean {
        return !getAuthToken().isNullOrEmpty()
    }

    fun clearPref() {
        sharedPreferences.edit().clear().apply()
    }

    fun addScore(model: ScoreModel) {
        val list = getScoreList()
        list.add(0, model)
        saveScoreList(ArrayList(list.take(10)))
    }

    fun setSoundEnable(isEnable: Boolean) {
        sharedPreferences.edit().putBoolean(AppConstant.SOUND_PREF, isEnable).apply()
    }

    fun isSoundEnable(): Boolean {
        return sharedPreferences.getBoolean(AppConstant.SOUND_PREF, true)
    }

    private fun saveScoreList(list: ArrayList<ScoreModel>) {
        sharedPreferences.edit().putString(AppConstant.SCORE_LIST, Gson().toJson(list)).apply()
    }

    fun getScoreList(): ArrayList<ScoreModel> {
        val json = sharedPreferences.getString(AppConstant.SCORE_LIST, null)
        val type = object : TypeToken<ArrayList<ScoreModel>>() {}.type
        return Gson().fromJson(json, type) ?: ArrayList()
    }

}
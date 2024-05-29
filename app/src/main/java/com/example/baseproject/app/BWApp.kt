package com.example.baseproject.app

import android.app.Application
import android.media.MediaPlayer
import com.example.baseproject.R
import com.example.baseproject.utils.PrefUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BWApp : Application() {
    @Inject
    lateinit var prefUtils: PrefUtils

    companion object {
        lateinit var BWApp: BWApp
        lateinit var mediaPlayer: MediaPlayer
        fun getAppInstance(): BWApp {
            return BWApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        BWApp = this
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_sound)
        mediaPlayer.isLooping = true
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            stopBgMusic()
        }
    }

    fun playBgMusic() {
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_sound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    fun stopBgMusic() {
        mediaPlayer.pause()
    }

    fun playButtonCLickSound() {
        if (prefUtils.isSoundEnable()) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.button_click)
            mediaPlayer.start()
        }
    }

    fun playCorrectAnswerSound() {
        if (prefUtils.isSoundEnable()) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.correct_answer)
            mediaPlayer.start()
        }
    }

    fun playWrongAnswerSound() {
        if (prefUtils.isSoundEnable()) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.wrong_answer)
            mediaPlayer.start()
        }
    }
}
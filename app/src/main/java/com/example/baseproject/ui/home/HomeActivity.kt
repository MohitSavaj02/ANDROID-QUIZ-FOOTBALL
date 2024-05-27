package com.example.baseproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityHomeBinding
import com.example.baseproject.ui.quiz.QuizActivity
import com.example.baseproject.ui.score.ScoreActivity
import com.example.baseproject.ui.settings.SettingsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("CustomSplashScreen")
class HomeActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setClicks()
    }

    private fun setClicks() {
        binding.txtPlayGame.setOnClickListener(this)
        binding.txtSettings.setOnClickListener(this)
        binding.txtScores.setOnClickListener(this)
    }

    private fun initView() {

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.txtPlayGame -> {
                startActivity(Intent(this, QuizActivity::class.java))
            }

            binding.txtSettings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            binding.txtScores -> {
                startActivity(Intent(this, ScoreActivity::class.java))
            }
        }
    }
}
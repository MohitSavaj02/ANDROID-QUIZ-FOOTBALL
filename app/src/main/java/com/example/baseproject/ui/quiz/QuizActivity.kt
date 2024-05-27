package com.example.baseproject.ui.quiz

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityQuizBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class QuizActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizBinding
    lateinit var adaptor: QuestionAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setClicks()
    }

    private fun setClicks() {
    }

    private fun initViews() {
        adaptor = QuestionAdaptor()
        binding.viewPager.adapter = adaptor
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                resetTimer()
            }
        })
    }

    private fun resetTimer() {

    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }
}
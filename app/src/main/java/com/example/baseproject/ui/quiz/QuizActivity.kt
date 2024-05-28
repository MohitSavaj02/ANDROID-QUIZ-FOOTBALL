package com.example.baseproject.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isVisible
import com.example.baseproject.app.AppConstant
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityQuizBinding
import com.example.baseproject.ui.complete.CompleteActivity
import com.example.baseproject.utils.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class QuizActivity : BaseActivity(), View.OnClickListener {
    var selectedPosition = 0
    private lateinit var binding: ActivityQuizBinding
    lateinit var adaptor: OptionsAdaptor
    val item get() = AppConstant.QUIZ_25_SET[selectedPosition]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setWindowInsets(view = binding.llContainer)
        setContentView(binding.root)
        initViews()
        setClicks()
    }

    private fun setClicks() {
        binding.txtNext.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
    }

    private fun initViews() {
        printData("item", item)
        adaptor = OptionsAdaptor()
        adaptor.setAnswer(item.answer)
        adaptor.setDataList(item.options)
        binding.rvOptions.adapter = adaptor
        binding.txtQuestion.text = item.question
        binding.imgQuestionImage.isVisible = item.hasImage
        if (item.hasImage) {
            binding.imgQuestionImage.load(item.imageURL)
            binding.llQuestion.gravity = Gravity.TOP
        } else {
            binding.llQuestion.gravity = Gravity.CENTER
        }
        binding.txtTitle.text = selectedPosition.plus(1).toString().plus(" of ").plus(AppConstant.QUIZ_25_SET.size)
    }

    private fun nextQuestion() {
        if (AppConstant.QUIZ_25_SET.lastIndex == selectedPosition) {
            startActivity(Intent(this, CompleteActivity::class.java))
            finish()
        } else {
            selectedPosition++
            initViews()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.txtNext -> {
                nextQuestion()
            }

            binding.imgBack -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}
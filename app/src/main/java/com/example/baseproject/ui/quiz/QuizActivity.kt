package com.example.baseproject.ui.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import androidx.core.view.isVisible
import com.example.baseproject.R
import com.example.baseproject.app.AppConstant
import com.example.baseproject.app.BWApp
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.data.OptionsModel
import com.example.baseproject.databinding.ActivityQuizBinding
import com.example.baseproject.ui.complete.CompleteActivity
import com.example.baseproject.utils.asString
import com.example.baseproject.utils.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class QuizActivity : BaseActivity(), View.OnClickListener, OptionsAdaptor.OptionsAdaptorListener {
    private var selectedPosition = 0
    private lateinit var binding: ActivityQuizBinding
    private lateinit var adaptor: OptionsAdaptor
    private var countDownTimer: CountDownTimer? = null
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
        adaptor = OptionsAdaptor()
        adaptor.setListener(this)
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
        binding.llProgress.max = AppConstant.MAX_TIME_PER_QUESTION
        binding.llProgress.progress = 0
        startTimer()
    }

    @SuppressLint("SetTextI18n")
    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(AppConstant.MAX_TIME_PER_QUESTION.times(1000L), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                binding.txtTime.text = timeFormatted
                binding.llProgress.progress = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                binding.txtTime.text = "00:00"
                binding.llProgress.progress = 0
                adaptor.showRightAnswer()
                BWApp.getAppInstance().playCorrectAnswerSound()
            }
        }
        countDownTimer?.start()
    }

    private val isRight: Boolean get() = if (binding.llProgress.progress == 0) false else adaptor.getSubmittedAnswer()?.options == item.answer
    private fun nextQuestion() {
        if (adaptor.getSubmittedAnswer() == null) {
            showToast(R.string.please_select_answer.asString())
            return
        }

        item.isRightAnswer = isRight
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
                clickSound()
                nextQuestion()
            }

            binding.imgBack -> {
                clickSound()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onSubmitAnswer(item: OptionsModel) {
        countDownTimer?.cancel()
        if (isRight) {
            BWApp.getAppInstance().playCorrectAnswerSound()
        } else {
            BWApp.getAppInstance().playWrongAnswerSound()
        }
    }
}
package com.example.baseproject.ui.complete

import android.os.Bundle
import android.text.Html
import android.view.View
import com.example.baseproject.R
import com.example.baseproject.app.AppConstant
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.data.ScoreModel
import com.example.baseproject.databinding.ActivityCompleteBinding
import com.example.baseproject.utils.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class CompleteActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowInsets(view = binding.llContainer)
        initViews()
        setClicks()
    }

    private fun setClicks() {
        binding.layoutHeader.imgBack.setOnClickListener(this)
    }

    private fun initViews() {
        val total = AppConstant.QUIZ_25_SET.size
        val right = AppConstant.QUIZ_25_SET.filter { it.isRightAnswer == true }.size
        val wrong = total.minus(right)
        val points = right.times(AppConstant.POINT_PER_RIGHT)
        binding.layoutHeader.txtTitle.text = R.string.result.asString()
        binding.txtQuestionCount.text = total.toString()
        binding.txtCorrectCount.text = right.toString()
        binding.txtWrongCount.text = wrong.toString()
        binding.txtPointsScore.text = Html.fromHtml(R.string.you_ve_scored_80_points.asString(arg = points.toString()), Html.FROM_HTML_MODE_LEGACY)
        val model = ScoreModel()
        model.date = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date())
        model.rightAnswer = right.toString()
        model.wrongAnswer = wrong.toString()
        model.points = points.toString().plus("pt")
        prefUtils.addScore(model)
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.layoutHeader.imgBack -> {
                clickSound()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}
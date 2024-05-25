package com.example.baseproject.ui.score

import android.os.Bundle
import android.view.View
import com.example.baseproject.R
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityScoreBinding
import com.example.baseproject.utils.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class ScoreActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityScoreBinding
    lateinit var adaptor: ScoreAdaptor

    //ViewAnimator
    private val dataChild = 0
    private val noDataChild = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setClicks()
    }

    private fun setClicks() {
        binding.layoutHeader.imgBack.setOnClickListener(this)
    }

    private fun initViews() {
        binding.layoutHeader.txtTitle.text = R.string.score.asString()
        adaptor = ScoreAdaptor()
        binding.rvScores.adapter = adaptor
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.layoutHeader.imgBack -> onBackPressedDispatcher.onBackPressed()
        }
    }
}
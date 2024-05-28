package com.example.baseproject.ui.complete

import android.os.Bundle
import android.view.View
import com.example.baseproject.R
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityCompleteBinding
import com.example.baseproject.utils.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
        binding.layoutHeader.txtTitle.text = R.string.result.asString()
    }

    private fun nextQuestion() {

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.layoutHeader.imgBack -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}
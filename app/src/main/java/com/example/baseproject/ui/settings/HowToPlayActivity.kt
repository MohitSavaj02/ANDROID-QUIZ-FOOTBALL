package com.example.baseproject.ui.settings

import android.os.Bundle
import android.view.View
import com.example.baseproject.R
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivityHowToPlayBinding
import com.example.baseproject.utils.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class HowToPlayActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHowToPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowInsets(view = binding.llContainer)
        initViews()
        setClicks()
    }

    private fun setClicks() {
        binding.layoutHeader.imgBack.setOnClickListener(this)
    }

    private fun initViews() {
        binding.layoutHeader.txtTitle.text = R.string.how_to_play.asString()
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
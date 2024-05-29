package com.example.baseproject.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.baseproject.R
import com.example.baseproject.app.AppConstant
import com.example.baseproject.app.BWApp
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivitySettingsBinding
import com.example.baseproject.utils.asString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class SettingsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowInsets(view = binding.llContainer)
        initViews()
        setClicks()
    }

    private fun setClicks() {
        binding.layoutHeader.imgBack.setOnClickListener(this)
        binding.llHowToPlay.setOnClickListener(this)
        binding.llInvite.setOnClickListener(this)
        binding.switchSound.isChecked = prefUtils.isSoundEnable()
    }

    private fun initViews() {
        binding.layoutHeader.txtTitle.text = R.string.settings_caps.asString()
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            prefUtils.setSoundEnable(isChecked)
            if (isChecked) {
                BWApp.getAppInstance().playBgMusic()
            } else {
                BWApp.getAppInstance().stopBgMusic()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.layoutHeader.imgBack -> {
                clickSound()
                onBackPressedDispatcher.onBackPressed()
            }

            binding.llHowToPlay -> {
                clickSound()
                startActivity(Intent(this, HowToPlayActivity::class.java))
            }

            binding.llInvite -> {
                clickSound()
                shareApp()
            }
        }
    }

    private fun shareApp() {
        val text = "Please check out this app: ".plus(R.string.app_name.asString())
        val url = AppConstant.PLAY_STORE_LINK.plus(packageName)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text.plus("\n\n").plus(url))
            type = "text/plain"
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share via")
        startActivity(chooserIntent)
    }
}
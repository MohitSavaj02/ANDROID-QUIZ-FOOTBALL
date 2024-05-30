package com.example.baseproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.baseproject.R
import com.example.baseproject.app.AppConstant.QUESTIONS_LIST
import com.example.baseproject.app.AppConstant.QUIZ_25_SET
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.data.QuizModel
import com.example.baseproject.data.resource.Status
import com.example.baseproject.databinding.ActivityHomeBinding
import com.example.baseproject.ui.quiz.QuizActivity
import com.example.baseproject.ui.score.ScoreActivity
import com.example.baseproject.ui.settings.SettingsActivity
import com.example.baseproject.utils.asString
import com.example.baseproject.viewmodel.APIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class HomeActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: APIViewModel by viewModels()
    private var isCompleteProcess = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setClicks()
        setObservers()
    }

    private fun setClicks() {
        binding.txtPlayGame.setOnClickListener(this)
        binding.txtSettings.setOnClickListener(this)
        binding.txtScores.setOnClickListener(this)
    }

    private fun callAPI() {
        if (QUESTIONS_LIST.isEmpty()) {
            isCompleteProcess = false
            QUESTIONS_LIST.clear()
            viewModel.getTeamData(teamId = 24, season = 2023)
        }
    }

    private fun setObservers() {
        viewModel.getTeamDataResponse.observe(this) { state ->
            when (state.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    viewModel.getSquad(teamId = 33)
                    val response = state.data?.response?.filterNotNull() ?: ArrayList()
                    if (response.isNotEmpty()) {
                        val allPlayerName = response.mapNotNull { it.player?.name }.distinct()
                        val allBirthPlace = response.mapNotNull { it.player?.birth?.place }.distinct()
                        val allNationality = response.mapNotNull { it.player?.nationality }.distinct()
                        for (item in response) {
                            if (!item.player?.photo.isNullOrEmpty() && !item.player?.name.isNullOrEmpty() && allPlayerName.size >= 4)
                                QUESTIONS_LIST.add(
                                    QuizModel(
                                        question = R.string.player_image_question.asString(),
                                        options = ArrayList(allPlayerName.shuffled().take(4)).apply {
                                            if (!this.contains(item.player?.name)) {
                                                removeFirst()
                                                add(item.player?.name)
                                                shuffle()
                                            }
                                        },
                                        answer = item.player?.name,
                                        hasImage = true,
                                        imageURL = item.player?.photo
                                    )
                                )
                            if (!item.player?.photo.isNullOrEmpty() && !item.player?.birth?.place.isNullOrEmpty() && allBirthPlace.size >= 4)
                                QUESTIONS_LIST.add(
                                    QuizModel(
                                        question = R.string.player_birthplace_question.asString(),
                                        options = ArrayList(allBirthPlace.shuffled().take(4)).apply {
                                            if (!this.contains(item.player?.birth?.place)) {
                                                removeFirst()
                                                add(item.player?.birth?.place)
                                                shuffle()
                                            }
                                        },
                                        answer = item.player?.birth?.place,
                                        hasImage = true,
                                        imageURL = item.player?.photo
                                    )
                                )
                            if (!item.player?.nationality.isNullOrEmpty() && !item.player?.name.isNullOrEmpty() && allNationality.size >= 4)
                                QUESTIONS_LIST.add(
                                    QuizModel(
                                        question = R.string.player_nationality_question.asString(arg = item.player?.name),
                                        options = ArrayList(allNationality.shuffled().take(4)).apply {
                                            if (!this.contains(item.player?.nationality)) {
                                                removeFirst()
                                                add(item.player?.nationality)
                                                shuffle()
                                            }
                                        },
                                        answer = item.player?.nationality,
                                    )
                                )
                        }
                    }
                }

                Status.ERROR -> {
                    isCompleteProcess = true
                    val error = state?.error
                }
            }
        }
        viewModel.getSquadResponse.observe(this) { state ->
            when (state.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    isCompleteProcess = true
                    val playerList = state?.data?.response?.firstOrNull()?.players ?: ArrayList()
                    if (playerList.isNotEmpty()) {
                        val allPlayerName = playerList.mapNotNull { it?.name }.distinct()
                        val allPositions = playerList.mapNotNull { it?.position }.distinct()
                        for (item in playerList) {
                            if (!item?.name.isNullOrEmpty() && !item?.photo.isNullOrEmpty() && allPlayerName.size >= 4)
                                QUESTIONS_LIST.add(
                                    QuizModel(
                                        question = R.string.player_image_question.asString(),
                                        options = ArrayList(allPlayerName.shuffled().take(4)).apply {
                                            if (!this.contains(item?.name)) {
                                                removeFirst()
                                                add(item?.name)
                                                shuffle()
                                            }
                                        },
                                        answer = item?.name,
                                        hasImage = true,
                                        imageURL = item?.photo
                                    )
                                )
                            if (!item?.name.isNullOrEmpty() && !item?.position.isNullOrEmpty() && allPositions.size >= 4)
                                QUESTIONS_LIST.add(
                                    QuizModel(
                                        question = R.string.player_position_question.asString(arg = item?.name),
                                        options = ArrayList(allPositions.shuffled().take(4)).apply {
                                            if (!this.contains(item?.position)) {
                                                removeFirst()
                                                add(item?.position)
                                                shuffle()
                                            }
                                        },
                                        answer = item?.position,
                                    )
                                )
                        }
                        QUESTIONS_LIST.shuffle()
                    }
                }

                Status.ERROR -> {
                    isCompleteProcess = true
                    val error = state?.error
                }
            }
        }
    }

    private fun initView() {

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.txtPlayGame -> {
                clickSound()
                if (!isCompleteProcess) {
                    showToast(R.string.preparing_quiz.asString())
                    return
                }
                if (QUESTIONS_LIST.isEmpty()) {
                    showToast(R.string.no_quiz_available.asString())
                    return
                }
                QUIZ_25_SET = ArrayList(QUESTIONS_LIST.take(25))
                QUESTIONS_LIST.removeAll(QUIZ_25_SET.toSet())
                if (QUESTIONS_LIST.size < 25) QUESTIONS_LIST.clear()
                startActivity(Intent(this, QuizActivity::class.java))
            }

            binding.txtSettings -> {
                clickSound()
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            binding.txtScores -> {
                clickSound()
                startActivity(Intent(this, ScoreActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        callAPI()
    }
}
package com.example.baseproject.ui.quiz

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.baseproject.R
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.data.QuizModel
import com.example.baseproject.data.resource.Status
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.utils.asString
import com.example.baseproject.viewmodel.APIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: APIViewModel by viewModels()
    private val questionList: ArrayList<QuizModel> = ArrayList()
    lateinit var adaptor: QuestionAdaptor

    //ViewAnimator
    private val dataChild = 0
    private val loaderChild = 1
    private val noDataChild = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setClicks()
        setObservers()
    }

    private fun setClicks() {
    }

    private fun initViews() {
        adaptor = QuestionAdaptor()
        binding.rvQuestions.adapter = adaptor
        callAPI()
    }

    private fun callAPI() {
        viewModel.getTeamData(teamId = 24, season = 2023)
    }

    private fun setObservers() {
        viewModel.getTeamDataResponse.observe(this) { state ->
            when (state.status) {
                Status.LOADING -> {
                    binding.viewAnimator.displayedChild = loaderChild
                }

                Status.SUCCESS -> {
                    viewModel.getSquad(teamId = 33)
                    val response = state.data?.response?.filterNotNull() ?: ArrayList()
                    if (response.isNotEmpty()) {
                        val allPlayerName = response.mapNotNull { it.player?.name }.distinct()
                        val allBirthPlace = response.mapNotNull { it.player?.birth?.place }.distinct()
                        val allNationality = response.mapNotNull { it.player?.birth?.place }.distinct()
                        for (item in response) {
                            if (!item.player?.photo.isNullOrEmpty() && !item.player?.name.isNullOrEmpty() && allPlayerName.size >= 4)
                                questionList.add(
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
                                questionList.add(
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
                                questionList.add(
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
                        binding.viewAnimator.displayedChild = dataChild
                    } else {
                        binding.viewAnimator.displayedChild = noDataChild
                        binding.txtNoData.text = R.string.no_data.asString()
                    }
                }

                Status.ERROR -> {
                    val error = state?.error
                    binding.viewAnimator.displayedChild = noDataChild
                    binding.txtNoData.text = error?.errorMessage
                }
            }
        }
        viewModel.getSquadResponse.observe(this) { state ->
            when (state.status) {
                Status.LOADING -> {
                    binding.viewAnimator.displayedChild = loaderChild
                }

                Status.SUCCESS -> {
                    val playerList = state?.data?.response?.firstOrNull()?.players ?: ArrayList()
                    if (playerList.isNotEmpty()) {
                        val allPlayerName = playerList.mapNotNull { it?.name }.distinct()
                        val allPositions = playerList.mapNotNull { it?.position }.distinct()
                        for (item in playerList) {
                            if (!item?.name.isNullOrEmpty() && !item?.photo.isNullOrEmpty() && allPlayerName.size >= 4)
                                questionList.add(
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
                                questionList.add(
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
                        questionList.shuffle()
                        adaptor.setDataList(questionList)
                        printData("questionList", questionList.size)
                        binding.viewAnimator.displayedChild = dataChild
                    } else {
                        binding.viewAnimator.displayedChild = noDataChild
                        binding.txtNoData.text = R.string.no_data.asString()
                    }
                }

                Status.ERROR -> {
                    val error = state?.error
                    binding.viewAnimator.displayedChild = noDataChild
                    binding.txtNoData.text = error?.errorMessage
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }
}
package com.example.baseproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.TeamDataResponse
import com.example.baseproject.data.resource.Resource
import com.example.baseproject.reposotory.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class APIViewModel @Inject constructor(
    private val repository: APIRepository,
) : ViewModel() {

    val getTeamDataResponse: MutableLiveData<Resource<TeamDataResponse>> = MutableLiveData()
    fun getTeamData(teamId: Int, season: Int, page: Int = 1) {
        viewModelScope.launch {
            repository.getTeamData(teamId = teamId, season = season, page = page).onEach { state ->
                getTeamDataResponse.value = state
            }.launchIn(viewModelScope)
        }
    }

}
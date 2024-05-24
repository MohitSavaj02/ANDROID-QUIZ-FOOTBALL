package com.example.baseproject.reposotory

import com.example.baseproject.api.AppApi
import com.example.baseproject.data.SquadResponse
import com.example.baseproject.data.TeamDataResponse
import com.example.baseproject.data.error.ValidationException
import com.example.baseproject.data.resource.Resource
import com.example.baseproject.data.resource.ResponseHandler
import com.example.baseproject.utils.PrefUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class APIRepository @Inject constructor(
    private val api: AppApi,
    private val responseHandler: ResponseHandler,
    private val mPrefUtils: PrefUtils,
) {
    suspend fun getTeamData(teamId: Int, season: Int, page: Int): Flow<Resource<TeamDataResponse>> = flow {
        emit(Resource.loading())
        try {
            val response = responseHandler.handleResponse(api.getTeamData(teamId = teamId, season = season, page = page))
            emit(response)
        } catch (e: Exception) {
            emit(responseHandler.handleException(e))
        }
    }.catch {
        emit(responseHandler.handleException(ValidationException(it.message)))
    }

    suspend fun getSquad(teamId: Int): Flow<Resource<SquadResponse>> = flow {
        emit(Resource.loading())
        try {
            val response = responseHandler.handleResponse(api.getSquad(teamId = teamId))
            emit(response)
        } catch (e: Exception) {
            emit(responseHandler.handleException(e))
        }
    }.catch {
        emit(responseHandler.handleException(ValidationException(it.message)))
    }
}
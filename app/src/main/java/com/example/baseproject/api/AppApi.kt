package com.example.baseproject.api

import com.example.baseproject.data.SquadResponse
import com.example.baseproject.data.TeamDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {

    @GET("players")
    suspend fun getTeamData(
        @Query("team") teamId: Int,
        @Query("season") season: Int,
        @Query("page") page: Int
    ): Response<TeamDataResponse>

    @GET("players/squads")
    suspend fun getSquad(
        @Query("team") teamId: Int,
    ): Response<SquadResponse>


}
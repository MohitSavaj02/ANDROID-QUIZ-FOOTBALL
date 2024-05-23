package com.example.baseproject.data


import com.google.gson.annotations.SerializedName

data class TeamDataResponse(
    @SerializedName("errors")
    var errors: List<Any?>? = null,
    @SerializedName("get")
    var `get`: String? = null,
    @SerializedName("paging")
    var paging: Paging? = null,
    @SerializedName("parameters")
    var parameters: Parameters? = null,
    @SerializedName("response")
    var response: List<Response?>? = null,
    @SerializedName("results")
    var results: Int? = null
) {
    data class Paging(
        @SerializedName("current")
        var current: Int? = null,
        @SerializedName("total")
        var total: Int? = null
    )

    data class Parameters(
        @SerializedName("season")
        var season: String? = null,
        @SerializedName("team")
        var team: String? = null
    )

    data class Response(
        @SerializedName("player")
        var player: Player? = null,
    ) {
        data class Player(
            @SerializedName("age")
            var age: Int? = null,
            @SerializedName("birth")
            var birth: Birth? = null,
            @SerializedName("firstname")
            var firstname: String? = null,
            @SerializedName("height")
            var height: String? = null,
            @SerializedName("id")
            var id: Int? = null,
            @SerializedName("injured")
            var injured: Boolean? = null,
            @SerializedName("lastname")
            var lastname: String? = null,
            @SerializedName("name")
            var name: String? = null,
            @SerializedName("nationality")
            var nationality: String? = null,
            @SerializedName("photo")
            var photo: String? = null,
            @SerializedName("weight")
            var weight: String? = null
        ) {
            data class Birth(
                @SerializedName("country")
                var country: String? = null,
                @SerializedName("date")
                var date: String? = null,
                @SerializedName("place")
                var place: String? = null
            )
        }
    }
}
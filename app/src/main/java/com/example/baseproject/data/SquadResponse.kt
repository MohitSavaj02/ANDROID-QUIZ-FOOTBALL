package com.example.baseproject.data


import com.google.gson.annotations.SerializedName

data class SquadResponse(
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
        @SerializedName("team")
        var team: String? = null
    )

    data class Response(
        @SerializedName("players")
        var players: List<Player?>? = null,
        @SerializedName("team")
        var team: Team? = null
    ) {
        data class Player(
            @SerializedName("age")
            var age: Int? = null,
            @SerializedName("id")
            var id: Int? = null,
            @SerializedName("name")
            var name: String? = null,
            @SerializedName("number")
            var number: Int? = null,
            @SerializedName("photo")
            var photo: String? = null,
            @SerializedName("position")
            var position: String? = null
        )

        data class Team(
            @SerializedName("id")
            var id: Int? = null,
            @SerializedName("logo")
            var logo: String? = null,
            @SerializedName("name")
            var name: String? = null
        )
    }
}
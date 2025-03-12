package com.example.cricketradio.scorecard

import kotlinx.serialization.Serializable

@Serializable
data class MiniScorecardResponse(
    val statusCode: Int,
    val responseData: ResponseData,
    val requestParams: Map<String, String>?,
    val time: String
)

@Serializable
data class ResponseData(
    val message: String,
    val result: ScorecardResult
)

@Serializable
data class ScorecardResult(
    val powerplay: String?,
    val powerplayOver: Int,
    val key: String,
    val status: String,
    val format: String,
    val announcement1: String?,
    val teams: Teams,
    val now: Now,
    val currentBattingOrder: Int,
    val settingObj: SettingObj,
    val lastCommentary: LastCommentary?,
    val announcement2: String?
)

@Serializable
data class Teams(
    val a: Team,
    val b: Team
)

@Serializable
data class Team(
    val name: String,
    val shortName: String,
    val logo: String,
    val a_1_score: Score? = null,
    val a_2_score: Score? = null,
    val b_1_score: Score? = null,
    val b_2_score: Score? = null
)

@Serializable
data class Score(
    val runs: Int,
    val overs: String?,
    val wickets: Int,
    val declare: Boolean
)

@Serializable
data class Now(
    val run_rate: String?,
    val req_run_rate: String?,
    val sessionLeft: String?
)

@Serializable
data class SettingObj(
    val currentTeam: String,
    val currentInning: Int
)

@Serializable
data class LastCommentary(
    val primaryText: String?,
    val secondaryText: String?,
    val tertiaryText: String?,
    val isDone: Boolean?
)

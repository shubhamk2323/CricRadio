package com.example.cricketradio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cricketradio.scorecard.MiniScorecardResponse
import com.example.cricketradio.scorecard.MiniScorecardViewModel
import com.example.cricketradio.scorecard.ODIScoreCardSection
import com.example.cricketradio.scorecard.Score
import com.example.cricketradio.scorecard.ScoreData
import com.example.cricketradio.scorecard.Team
import com.example.cricketradio.scorecard.TestMatchScoreCard

import com.example.cricketradio.venue.VenueInfoViewModel
import com.example.cricketradio.ui.theme.bk
import com.example.cricketradio.venue.VenueInfox

@Composable
fun CricketRadioScreen(
    scorecardViewModel: MiniScorecardViewModel = hiltViewModel(),
    venueViewModel: VenueInfoViewModel = hiltViewModel()
) {
    val miniScorecard by scorecardViewModel.miniScorecard.collectAsState()
    val venueInfo by venueViewModel.venueInfo.collectAsState()

    LaunchedEffect(Unit) {
        scorecardViewModel.fetchMiniScorecard()
        venueViewModel.fetchVenueInfo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize().background(bk),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        // Show Scorecard
        miniScorecard?.let { data ->
            Spacer(modifier = Modifier.height(16.dp))

            if (data.responseData.result.format == "test") {
                MiniScorecardTestUI(miniScorecard = data) // Test match UI render
            } else {
                MiniScorecardODIUI(miniScorecard = data) // ODI UI render
            }
        } ?: Text(text = "Loading Scorecard...", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        venueInfo?.let { data ->
            VenueInfox(data)
        } ?: Text(text = "Loading Venue Info...", style = MaterialTheme.typography.bodyLarge)
    }

}



@Composable
fun MiniScorecardODIUI(miniScorecard: MiniScorecardResponse?) {
    miniScorecard?.let { data ->
        val teams = data.responseData.result.teams
        val now = data.responseData.result.now
        val settings = data.responseData.result.settingObj
        val lastCommentary = data.responseData.result.lastCommentary?.primaryText

        val team1 = teams.a
        val team2 = teams.b
        val currentBattingTeam = settings.currentTeam
        val firstBattingTeam = determineFirstBattingTeam(team1, team2)

        // Selecting correct scores based on innings
        val team1Score = team1.a_1_score ?: team1.a_2_score
        val team2Score = team2.b_1_score ?: team2.b_2_score

        ODIScoreCardSection(
            team1 = team1.name,
            team1ShortName = team1.shortName,
            team1Logo = team1.logo,
            team2 = team2.name,
            team2ShortName = team2.shortName,
            team2Logo = team2.logo,
            firstBattingTeam = firstBattingTeam,
            currentBattingTeam = currentBattingTeam,
            team1Score = team1Score?.toScoreData(),
            team2Score = team2Score?.toScoreData(),
            crr = now.run_rate ?: "--",
            rrr = now.req_run_rate,
            announcement = data.responseData.result.announcement1 ?: data.responseData.result.announcement2,
            lastCommentary = lastCommentary
        )
    } ?: Text(text = "Loading...")
}

fun determineFirstBattingTeam(team1: Team, team2: Team): String {
    return if (team1.a_1_score != null) team1.name else team2.name
}

fun Score.toScoreData(): ScoreData {
    return ScoreData(
        runs = this.runs,
        overs = this.overs ?: "",
        wickets = this.wickets,
        declare = this.declare
    )
}


@Composable
fun MiniScorecardTestUI(miniScorecard: MiniScorecardResponse?) {
    miniScorecard?.let { data ->
        val teams = data.responseData.result.teams
        val now = data.responseData.result.now
        val settings = data.responseData.result.settingObj
        val lastCommentary = data.responseData.result.lastCommentary?.primaryText
        val result = data.responseData.result

        // Extracting Team Info
        val team1 = teams.a.name
        val team1Logo = teams.a.logo
        val team2 = teams.b.name
        val team2Logo = teams.b.logo
        val team1s= teams.a.shortName
        val team2s= teams.b.shortName

        // Extracting Score Information
        val team1Innings1 = result.teams.a.a_1_score?.let { "${it.runs}/${it.wickets}" to it.overs } ?: (null to null)
        val team1Innings2 = result.teams.a.a_2_score?.let { "${it.runs}/${it.wickets}" to it.overs } ?: (null to null)
        val team2Innings1 = result.teams.b.b_1_score?.let { "${it.runs}/${it.wickets}" to it.overs } ?: (null to null)
        val team2Innings2 = result.teams.b.b_2_score?.let { "${it.runs}/${it.wickets}" to it.overs } ?: (null to null)

        // Batting Team and Inning
        val currentBattingTeam = if (settings.currentTeam == "a") team1 else team2
        val currentInnings = settings.currentInning

        // Match Over Status
        val isMatchOver = result.status.contains("completed", ignoreCase = true)

        // Result and Announcement
        val resultText = result.announcement1 ?: result.announcement2
        val announcement = result.announcement1 ?: result.announcement2

        // ✅ Target Calculation (Since API doesn't provide target)
        val team1TotalRuns = (result.teams.a.a_1_score?.runs ?: 0) + (result.teams.a.a_2_score?.runs ?: 0)
        val team2Innings1Runs = result.teams.b.b_1_score?.runs ?: 0
        val target = if (currentInnings == 4) team1TotalRuns - team2Innings1Runs + 1 else null

        // ✅ Remaining Runs Calculation
        val team2Innings2Runs = team2Innings2.first?.split("/")?.get(0)?.toIntOrNull() ?: 0
        val remainingRuns = target?.let { it - team2Innings2Runs }

        // Current Run Rate
        val crr = now.run_rate

        TestMatchScoreCard(
            team1 = team1,
            team1s= team1s,
            team1Logo = team1Logo,
            team2 = team2,
            team2s= team2s,
            team2Logo = team2Logo,
            team1Innings1 = team1Innings1,
            team1Innings2 = team1Innings2,
            team2Innings1 = team2Innings1,
            team2Innings2 = team2Innings2,
            currentBattingTeam = currentBattingTeam,
            currentInnings = currentInnings,
            target = target,
            isMatchOver = isMatchOver,
            result = resultText,
            announcement = announcement,
            lastCommentary = lastCommentary,
            crr = crr
        )
    } ?: Text(text = "Loading...")
}

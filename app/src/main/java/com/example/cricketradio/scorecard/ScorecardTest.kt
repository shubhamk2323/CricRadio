package com.example.cricketradio.scorecard


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.cricketradio.ui.theme.bl
import com.example.cricketradio.ui.theme.bn
import com.example.cricketradio.ui.theme.yel

@Composable
fun TestMatchScoreCard(
    team1: String,
    team1s: String,
    team1Logo: String,
    team2: String,
    team2s: String,
    team2Logo: String,
    team1Innings1: Pair<String?, String?>,
    team1Innings2: Pair<String?, String?>,
    team2Innings1: Pair<String?, String?>,
    team2Innings2: Pair<String?, String?>,
    currentBattingTeam: String,
    currentInnings: Int,
    target: Int?,
    isMatchOver: Boolean,
    result: String?,
    announcement: String?,
    lastCommentary: String?,
    crr: String?
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)
        .background(color= bn).clip(RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp))
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamInfo(team1s, team1Logo, currentBattingTeam == team1)
            TeamInfo(team2s, team2Logo, currentBattingTeam == team2)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = lastCommentary ?: "",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (!(team1Innings1.first == "0/0" && team1Innings1.second == "0") ||
                !(team1Innings2.first == "0/0" && team1Innings2.second == "0")) {
                ScoreColumn(team1Innings1, team1Innings2)
            }

            if (!(team2Innings1.first == "0/0" && team2Innings1.second == "0") ||
                !(team2Innings2.first == "0/0" && team2Innings2.second == "0")) {
                ScoreColumn(team2Innings1, team2Innings2)
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            crr?.let { Text(text = "CRR: $it", style = MaterialTheme.typography.bodyMedium, color = Color.White) }
        }

        Spacer(modifier = Modifier.height(8.dp))

        announcement?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color= Color.White,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        if (isMatchOver) {
            Text(
                text = result ?: "Match Over",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )
        } else if (currentInnings == 4 && target != null) {
            val remainingRuns = target - (team2Innings2.first?.split("/")?.get(0)?.toIntOrNull() ?: 0)
            Text(
                text = "$team2 needs $remainingRuns runs to win",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun ScoreColumn(innings1: Pair<String?, String?>?, innings2: Pair<String?, String?>?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        innings1?.let { (runs, overs) ->
            if (!runs.isNullOrEmpty() && runs != "0/0" && overs != "0") {
                Text(text = runs, style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold, color = yel)
                Text(text = overs.orEmpty(), style = MaterialTheme.typography.bodySmall)
            }
        }

        innings2?.let { (runs, overs) ->
            if (!runs.isNullOrEmpty() && runs != "0/0" && overs != "0") {
                Text(text = runs, style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp, fontWeight = FontWeight.Bold, color = yel)
                Text(text = overs.orEmpty(), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TestMatchScorePreview() {
    TestMatchScoreCard(
        team1 = "India",
        team1s= "Ind",
        team1Logo = "https://www.espncricinfo.com/db/PICTURES/CMS/313100/313125.logo.png",
        team2 = "Australia",
        team2s= "Aus",
        team2Logo = "https://www.espncricinfo.com/db/PICTURES/CMS/340000/340047.png",
        team1Innings1 = Pair("250", "80.3"),
        team1Innings2 = Pair("350", "95.0"),
        team2Innings1 = Pair("280", "82.2"),
        team2Innings2 = Pair("24/0", "10.2"),
        currentBattingTeam = "Australia",
        currentInnings = 4,
        target = 321,
        isMatchOver = false,
        result = null,
        announcement = "India leads by 297 runs",
        lastCommentary = "4 Runs! Beautiful shot.",

        crr = "3.64"
    )
}


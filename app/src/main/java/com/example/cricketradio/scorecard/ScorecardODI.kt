package com.example.cricketradio.scorecard


import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.cricketradio.ui.theme.yel

@Composable
fun ODIScoreCardSection(
    team1: String,
    team1ShortName: String,
    team1Logo: String,
    team2: String,
    team2ShortName: String,
    team2Logo: String,
    firstBattingTeam: String,
    currentBattingTeam: String,
    team1Score: ScoreData?,
    team2Score: ScoreData?,
    crr: String,
    rrr: String?,
    announcement: String?,
    lastCommentary: String?
){
    Column(modifier= Modifier.fillMaxWidth().padding(16.dp)
        .clip(RoundedCornerShape(16.dp))){
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (team1 == firstBattingTeam) {
                TeamInfo(team1ShortName, team1Logo, currentBattingTeam == team1)
                TeamInfo(team2ShortName, team2Logo, currentBattingTeam == team2)
            } else {
                TeamInfo(team2ShortName, team2Logo, currentBattingTeam == team2)
                TeamInfo(team1ShortName, team1Logo, currentBattingTeam == team1)
            }
        }

        Spacer(modifier= Modifier.height(8.dp))

        Text(
            text = lastCommentary ?: "",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier= Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (team1 == firstBattingTeam) {
                ScoreColumn(team1Score)
                ScoreColumn(team2Score)
            } else {
                ScoreColumn(team2Score)
                ScoreColumn(team1Score)
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "CRR: $crr", style = MaterialTheme.typography.bodyMedium)
            rrr?.let { Text(text = "RRR: $it", style = MaterialTheme.typography.bodyMedium) }
            announcement?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color= Color.White,
                    fontSize= 32.sp,
                    modifier = Modifier
                        .scale(LocalConfiguration.current.screenWidthDp / 400f)
                        .padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
fun TeamInfo(shortName: String, logoUrl: String, isBatting: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(logoUrl),
            contentDescription = "$shortName Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = shortName, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        if (isBatting) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "🏏", style = MaterialTheme.typography.bodyLarge) // Bat icon
        }
    }
}

@Composable
fun ScoreColumn(score: ScoreData?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val declareText = if (score?.declare == true) "d" else ""
        Text(text = "${score?.runs ?: ""}/${score?.wickets ?: ""} $declareText",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight= FontWeight.Bold,
            color = yel, fontSize = 24.sp)
        Text(text = score?.overs ?: "", style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun ODIScoreCardPreview() {
    ODIScoreCardSection(
        team1 = "South Africa",
        team1ShortName = "SA",
        team1Logo = "https://www.espncricinfo.com/db/PICTURES/CMS/313100/313125.logo.png",
        team2 = "Sri Lanka",
        team2ShortName = "SL",
        team2Logo = "https://www.espncricinfo.com/db/PICTURES/CMS/340000/340047.png",
        firstBattingTeam = "South Africa",
        currentBattingTeam = "Sri Lanka",
        team1Score = ScoreData(261, "50.0", 7, false),
        team2Score = ScoreData(59, "10.1", 4, false),
        crr = "3.47",
        rrr = "5.5",
        announcement = "SL needs 223 runs in 122 balls",
        lastCommentary = "4 Runs"
    )
}

data class ScoreData(
    val runs: Int?,
    val overs: String?,
    val wickets: Int?,
    val declare: Boolean?
)

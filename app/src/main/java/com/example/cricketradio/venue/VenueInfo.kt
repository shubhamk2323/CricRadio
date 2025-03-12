package com.example.cricketradio.venue

import VenueInfoResponse
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cricketradio.ui.theme.bk
import com.example.cricketradio.ui.theme.gr
import com.example.cricketradio.ui.theme.lgk

@Composable
fun VenueInfox(x: VenueInfoResponse) {
    Box(
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
    ) {
        LazyColumn(modifier= Modifier
            .background(color= bk)
            .padding(16.dp)
        ){
            item{
            AsyncImage(
                model= x.responseData.result.venueDetails.photo,
                contentDescription= "Venue Photo",
                modifier= Modifier.fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
            )}

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text (text =
                    "${x.responseData.result.venueDetails.knownAs} , " + x.responseData.result.venueDetails.venueLocation,
                color = Color.White)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item{
            Row(){
                Text(text= "${x.responseData.result.season.name}, ",
                    fontWeight = FontWeight.Medium,
                    color= Color.White)
            }}
            item{Text(text= x.responseData.result.start_date.str, color= Color.White)}
            item{Spacer(modifier = Modifier.height(8.dp))}
            item{
                Column(modifier= Modifier.fillMaxWidth()
                    .padding(4.dp)
                    .background(gr, RoundedCornerShape(8.dp))
                ){
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text= x.responseData.result.toss.str,
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        modifier = Modifier.padding(8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item{
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Text(text= "Umpires",
                    fontWeight = FontWeight.Medium,
                    color= Color.White)
            }

            item{Spacer(modifier = Modifier.height(8.dp))}

            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .background(gr, RoundedCornerShape(8.dp)),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Umpire",
                            fontWeight = FontWeight.Normal,
                            color = lgk,
                            modifier = Modifier
                                .absolutePadding(4.dp, 4.dp)
                        )
                        Text(
                            text = "Umpire",
                            fontWeight = FontWeight.Normal,
                            color = lgk,
                            modifier = Modifier
                                .absolutePadding(4.dp, 4.dp, 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = x.responseData.result.firstUmpire,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = x.responseData.result.secondUmpire,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth().padding(8.dp),
                        thickness = 0.5.dp,
                        color = lgk
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Third/TV Umpire",
                            fontWeight = FontWeight.Normal,
                            color = lgk,
                            modifier = Modifier
                                .absolutePadding(4.dp, 4.dp)
                        )
                        Text(
                            text = "Referee",
                            fontWeight = FontWeight.Normal,
                            color = lgk,
                            modifier = Modifier
                                .absolutePadding(4.dp, 4.dp, 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = x.responseData.result.thirdUmpire,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = x.responseData.result.matchReferee,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            item{
            Spacer(modifier = Modifier.height(16.dp))}

            item{
            Text(text= "Weather",
                fontWeight = FontWeight.Medium,
                color= Color.White
            )}

            item{
            Row(modifier= Modifier.fillMaxWidth().padding(4.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp))
            {
                AsyncImage(
                    model= x.responseData.result.weather.condition.icon,
                    contentDescription= "Weather Icon",
                    modifier= Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)){
                    Text(text= x.responseData.result.weather.location)
                    Text(text= x.responseData.result.weather.temp_c.toString() + "°C", fontWeight = FontWeight.Bold,
                        fontSize= 20.sp)
                    Text(text= x.responseData.result.weather.condition.text)
                }
                Column(modifier = Modifier.weight(1f)){
                    Text("Last Updated",
                        fontWeight = FontWeight.Normal)
                    Text(text= x.responseData.result.weather.last_updated,
                        fontWeight = FontWeight.Medium)

                }
            }
            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Venue Stats",
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            item{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            )
            {
                @Composable
                fun tableRow(title: String, value1: String, value2: String? = null) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            color = lgk,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = value1,
                            color = Color.White,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                        if (value2 != null) {
                            Text(
                                text = value2,
                                color = Color.White,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        thickness = 0.5.dp,
                        color = lgk
                    )
                }

                tableRow("Matches Played", x.responseData.result.venueStats.matchesPlayed.toString())
                tableRow("Lowest Defended", x.responseData.result.venueStats.lowestDefended.toString())
                tableRow("Highest Chased", x.responseData.result.venueStats.highestChased.toString())
                tableRow("Win Bat First", x.responseData.result.venueStats.batFirstWins.toString())
                tableRow("Win Ball First", x.responseData.result.venueStats.ballFirstWins.toString())

                Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Text(text = " ", modifier = Modifier.weight(1f))
                    Text(text = "1st Inn", color = lgk, textAlign = TextAlign.End, modifier = Modifier.weight(1f))
                    Text(text = "2nd Inn", color = lgk, textAlign = TextAlign.End, modifier = Modifier.weight(1f))
                }

                tableRow("Avg Score", x.responseData.result.venueStats.battingFirst.averageScore.toString(), x.responseData.result.venueStats.battingSecond.averageScore.toString())
                tableRow("Highest Score",
                    x.responseData.result.venueStats.battingFirst.highestScore, x.responseData.result.venueStats.battingSecond.highestScore
                )
                tableRow("Lowest Score",
                    x.responseData.result.venueStats.battingFirst.lowestScore, x.responseData.result.venueStats.battingSecond.lowestScore
                )
                tableRow("Pace Wickets", x.responseData.result.venueStats.battingFirst.paceWickets.toString(), x.responseData.result.venueStats.battingSecond.paceWickets.toString())
                tableRow("Spin Wickets", x.responseData.result.venueStats.battingFirst.spinWickets.toString(), x.responseData.result.venueStats.battingSecond.spinWickets.toString())
            }
        }
    }
}}
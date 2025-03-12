import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class VenueInfoResponse(
    val statusCode: Int=0,
    val responseData: ResponseData
)

@Serializable
data class ResponseData(
    val message: String="",
    val result: Result
)

@Serializable
data class Result(
    val _id: String = "",
    val firstUmpire: String = "",
    val format: String = "",
    val key: String?=null,
    val matchReferee: String = "",
    val related_name: String = "",
    val season: Season = Season(),
    val secondUmpire: String = "",
    val start_date: StartDate = StartDate(),
    val status: String = "",
    val teams: Teams = Teams(Team(), Team()),
    val thirdUmpire: String = "",
    val toss: Toss = Toss("", "", ""),
    val venue: String = "",
    val venueDetails: VenueDetails = VenueDetails(),
    val weather: Weather = Weather(),
    val venueStats: VenueStats = VenueStats()
)


@Serializable
data class Season(
    val key: String="",
    val name: String=""
)

@Serializable
data class StartDate(
    val timestamp: Long=0,
    val iso: String="",
    val str: String="",
    val sky_check_ts: Long=0
)

@Serializable
data class Teams(
    val a: Team = Team(),
    val b: Team = Team()
)

@Serializable
data class Team(
    val name: String = "",
    val shortName: String = "",
    val logo: String = ""
)


@Serializable
data class Toss(
    val won: String = "",
    val decision: String = "",
    val str: String = ""
)


@Serializable
data class VenueDetails(
    val _id: String = "",
    val knownAs: String = "",
    val capacity: Int = 0,
    val createdAt: String = "",
    val cricinfoId: Int = 0,
    val description: String = "",
    val ends1: String = "",
    val ends2: String = "",
    val floodLights: Int = 0,
    val homeTo: String = "",
    val isDeleted: String = "",
    val opened: String?= null,
    val photo: String = "",
    val status: String = "",
    val timezone: String = "",
    val updatedAt: String = "",
    val venueLocation: String = "",
    val venueScraptitle: String = "",
    val venue_info: VenueInfo = VenueInfo()
)

@Serializable
data class VenueInfo(
    val name: String="",
    val smallName: String="",
    val longName: String="",
    val location: String="",
    val town: String=""
)


@Serializable
data class Weather(
    val location: String = "",
    val condition: WeatherCondition = WeatherCondition(),
    val chance_of_rain: Int = 0,
    val temp_c: Double = 0.0,
    val last_updated: String = ""
)

@Serializable
data class WeatherCondition(
    val code: Int = 0,
    val icon: String = "",
    val text: String = ""
)
@Serializable
data class VenueStats(
    val matchesPlayed: Int=0,
    @Serializable(with = NumericStringSerializer::class) val lowestDefended: String?="",
    @Serializable(with = NumericStringSerializer::class) val highestChased: String?="",
    val batFirstWins: Int=0,
    val ballFirstWins: Int=0,
    val battingFirst: BattingStats= BattingStats(),
    val battingSecond: BattingStats= BattingStats()
)

@Serializable
data class BattingStats(
    val averageScore: Int=0,
    val highestScore: String="",
    val lowestScore: String="",
    val paceWickets: Int=0,
    val spinWickets: Int=0
)



@Serializer(forClass = String::class)
object NumericStringSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NumericString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String? {
        val rawValue = decoder.decodeString()
        return rawValue.split(" ")[0] // Extract only the first part (e.g., "153" from "153/10 (19.4 Ov)")
    }

    override fun serialize(encoder: Encoder, value: String?) {
        encoder.encodeString(value ?: "")
    }
}




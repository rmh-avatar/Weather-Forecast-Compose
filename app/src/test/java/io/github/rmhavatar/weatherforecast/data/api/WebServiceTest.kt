package io.github.rmhavatar.weatherforecast.data.api

import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class WebServiceTest {
    private val server = MockWebServer()

    private lateinit var apiService: ServiceClient

    @Before
    fun setUp() {
        server.start(8080)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val moshiBuilder = Moshi.Builder()

        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
            .build()
            .create(ServiceClient::class.java)

        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/data/2.5/weather?lon=10.99&lat=44.34&appid=211a182a81e0424386f215c039d74026&units=metric" -> return MockResponse().setResponseCode(
                        200
                    ).setBody(
                        "                             \n" +
                                "\n" +
                                "{\n" +
                                "  \"coord\": {\n" +
                                "    \"lon\": 10.99,\n" +
                                "    \"lat\": 44.34\n" +
                                "  },\n" +
                                "  \"weather\": [\n" +
                                "    {\n" +
                                "      \"id\": 501,\n" +
                                "      \"main\": \"Rain\",\n" +
                                "      \"description\": \"moderate rain\",\n" +
                                "      \"icon\": \"10d\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"base\": \"stations\",\n" +
                                "  \"main\": {\n" +
                                "    \"temp\": 298.48,\n" +
                                "    \"feels_like\": 298.74,\n" +
                                "    \"temp_min\": 297.56,\n" +
                                "    \"temp_max\": 300.05,\n" +
                                "    \"pressure\": 1015,\n" +
                                "    \"humidity\": 64,\n" +
                                "    \"sea_level\": 1015,\n" +
                                "    \"grnd_level\": 933\n" +
                                "  },\n" +
                                "  \"visibility\": 10000,\n" +
                                "  \"wind\": {\n" +
                                "    \"speed\": 0.62,\n" +
                                "    \"deg\": 349,\n" +
                                "    \"gust\": 1.18\n" +
                                "  },\n" +
                                "  \"rain\": {\n" +
                                "    \"1h\": 3.16\n" +
                                "  },\n" +
                                "  \"clouds\": {\n" +
                                "    \"all\": 100\n" +
                                "  },\n" +
                                "  \"dt\": 1661870592,\n" +
                                "  \"sys\": {\n" +
                                "    \"type\": 2,\n" +
                                "    \"id\": 2075663,\n" +
                                "    \"country\": \"IT\",\n" +
                                "    \"sunrise\": 1661834187,\n" +
                                "    \"sunset\": 1661882248\n" +
                                "  },\n" +
                                "  \"timezone\": 7200,\n" +
                                "  \"id\": 3163858,\n" +
                                "  \"name\": \"Zocca\",\n" +
                                "  \"cod\": 200\n" +
                                "}\n" +
                                "                             \n" +
                                "\n" +
                                "                           "
                    )

                    "/geo/1.0/direct?q=London&appid=211a182a81e0424386f215c039d74026" -> return MockResponse().setResponseCode(
                        200
                    )
                        .setBody(
                            "                \n" +
                                    "\n" +
                                    "[\n" +
                                    "   {\n" +
                                    "      \"name\":\"London\",\n" +
                                    "      \"local_names\":{\n" +
                                    "         \"ms\":\"London\",\n" +
                                    "         \"gu\":\"લંડન\",\n" +
                                    "         \"is\":\"London\",\n" +
                                    "         \"wa\":\"Londe\",\n" +
                                    "         \"mg\":\"Lôndôna\",\n" +
                                    "         \"gl\":\"Londres\",\n" +
                                    "         \"om\":\"Landan\",\n" +
                                    "         \"ku\":\"London\",\n" +
                                    "         \"tw\":\"London\",\n" +
                                    "         \"mk\":\"Лондон\",\n" +
                                    "         \"ee\":\"London\",\n" +
                                    "         \"fj\":\"Lodoni\",\n" +
                                    "         \"gd\":\"Lunnainn\",\n" +
                                    "         \"ky\":\"Лондон\",\n" +
                                    "         \"yo\":\"Lọndọnu\",\n" +
                                    "         \"zu\":\"ILondon\",\n" +
                                    "         \"bg\":\"Лондон\",\n" +
                                    "         \"tk\":\"London\",\n" +
                                    "         \"co\":\"Londra\",\n" +
                                    "         \"sh\":\"London\",\n" +
                                    "         \"de\":\"London\",\n" +
                                    "         \"kl\":\"London\",\n" +
                                    "         \"bi\":\"London\",\n" +
                                    "         \"km\":\"ឡុងដ៍\",\n" +
                                    "         \"lt\":\"Londonas\",\n" +
                                    "         \"fi\":\"Lontoo\",\n" +
                                    "         \"fy\":\"Londen\",\n" +
                                    "         \"ba\":\"Лондон\",\n" +
                                    "         \"sc\":\"Londra\",\n" +
                                    "         \"feature_name\":\"London\",\n" +
                                    "         \"ja\":\"ロンドン\",\n" +
                                    "         \"am\":\"ለንደን\",\n" +
                                    "         \"sk\":\"Londýn\",\n" +
                                    "         \"mr\":\"लंडन\",\n" +
                                    "         \"es\":\"Londres\",\n" +
                                    "         \"sq\":\"Londra\",\n" +
                                    "         \"te\":\"లండన్\",\n" +
                                    "         \"br\":\"Londrez\",\n" +
                                    "         \"uz\":\"London\",\n" +
                                    "         \"da\":\"London\",\n" +
                                    "         \"sw\":\"London\",\n" +
                                    "         \"fa\":\"لندن\",\n" +
                                    "         \"sr\":\"Лондон\",\n" +
                                    "         \"cu\":\"Лондонъ\",\n" +
                                    "         \"ln\":\"Lóndɛlɛ\",\n" +
                                    "         \"na\":\"London\",\n" +
                                    "         \"wo\":\"Londar\",\n" +
                                    "         \"ig\":\"London\",\n" +
                                    "         \"to\":\"Lonitoni\",\n" +
                                    "         \"ta\":\"இலண்டன்\",\n" +
                                    "         \"mt\":\"Londra\",\n" +
                                    "         \"ar\":\"لندن\",\n" +
                                    "         \"su\":\"London\",\n" +
                                    "         \"ab\":\"Лондон\",\n" +
                                    "         \"ps\":\"لندن\",\n" +
                                    "         \"bm\":\"London\",\n" +
                                    "         \"mi\":\"Rānana\",\n" +
                                    "         \"kn\":\"ಲಂಡನ್\",\n" +
                                    "         \"kv\":\"Лондон\",\n" +
                                    "         \"os\":\"Лондон\",\n" +
                                    "         \"bn\":\"লন্ডন\",\n" +
                                    "         \"li\":\"Londe\",\n" +
                                    "         \"vi\":\"Luân Đôn\",\n" +
                                    "         \"zh\":\"伦敦\",\n" +
                                    "         \"eo\":\"Londono\",\n" +
                                    "         \"ha\":\"Landan\",\n" +
                                    "         \"tt\":\"Лондон\",\n" +
                                    "         \"lb\":\"London\",\n" +
                                    "         \"ce\":\"Лондон\",\n" +
                                    "         \"hu\":\"London\",\n" +
                                    "         \"it\":\"Londra\",\n" +
                                    "         \"tl\":\"Londres\",\n" +
                                    "         \"pl\":\"Londyn\",\n" +
                                    "         \"sm\":\"Lonetona\",\n" +
                                    "         \"en\":\"London\",\n" +
                                    "         \"vo\":\"London\",\n" +
                                    "         \"el\":\"Λονδίνο\",\n" +
                                    "         \"sn\":\"London\",\n" +
                                    "         \"fr\":\"Londres\",\n" +
                                    "         \"cs\":\"Londýn\",\n" +
                                    "         \"io\":\"London\",\n" +
                                    "         \"hi\":\"लंदन\",\n" +
                                    "         \"et\":\"London\",\n" +
                                    "         \"pa\":\"ਲੰਡਨ\",\n" +
                                    "         \"av\":\"Лондон\",\n" +
                                    "         \"ko\":\"런던\",\n" +
                                    "         \"bh\":\"लंदन\",\n" +
                                    "         \"yi\":\"לאנדאן\",\n" +
                                    "         \"sa\":\"लन्डन्\",\n" +
                                    "         \"sl\":\"London\",\n" +
                                    "         \"hr\":\"London\",\n" +
                                    "         \"si\":\"ලන්ඩන්\",\n" +
                                    "         \"so\":\"London\",\n" +
                                    "         \"gn\":\"Lóndyre\",\n" +
                                    "         \"ay\":\"London\",\n" +
                                    "         \"se\":\"London\",\n" +
                                    "         \"sd\":\"لنڊن\",\n" +
                                    "         \"af\":\"Londen\",\n" +
                                    "         \"ga\":\"Londain\",\n" +
                                    "         \"or\":\"ଲଣ୍ଡନ\",\n" +
                                    "         \"ia\":\"London\",\n" +
                                    "         \"ie\":\"London\",\n" +
                                    "         \"ug\":\"لوندۇن\",\n" +
                                    "         \"nl\":\"Londen\",\n" +
                                    "         \"gv\":\"Lunnin\",\n" +
                                    "         \"qu\":\"London\",\n" +
                                    "         \"be\":\"Лондан\",\n" +
                                    "         \"an\":\"Londres\",\n" +
                                    "         \"fo\":\"London\",\n" +
                                    "         \"hy\":\"Լոնդոն\",\n" +
                                    "         \"nv\":\"Tooh Dineʼé Bikin Haalʼá\",\n" +
                                    "         \"bo\":\"ལོན་ཊོན།\",\n" +
                                    "         \"ascii\":\"London\",\n" +
                                    "         \"id\":\"London\",\n" +
                                    "         \"lv\":\"Londona\",\n" +
                                    "         \"ca\":\"Londres\",\n" +
                                    "         \"no\":\"London\",\n" +
                                    "         \"nn\":\"London\",\n" +
                                    "         \"ml\":\"ലണ്ടൻ\",\n" +
                                    "         \"my\":\"လန်ဒန်မြို့\",\n" +
                                    "         \"ne\":\"लन्डन\",\n" +
                                    "         \"he\":\"לונדון\",\n" +
                                    "         \"cy\":\"Llundain\",\n" +
                                    "         \"lo\":\"ລອນດອນ\",\n" +
                                    "         \"jv\":\"London\",\n" +
                                    "         \"sv\":\"London\",\n" +
                                    "         \"mn\":\"Лондон\",\n" +
                                    "         \"tg\":\"Лондон\",\n" +
                                    "         \"kw\":\"Loundres\",\n" +
                                    "         \"cv\":\"Лондон\",\n" +
                                    "         \"az\":\"London\",\n" +
                                    "         \"oc\":\"Londres\",\n" +
                                    "         \"th\":\"ลอนดอน\",\n" +
                                    "         \"ru\":\"Лондон\",\n" +
                                    "         \"ny\":\"London\",\n" +
                                    "         \"bs\":\"London\",\n" +
                                    "         \"st\":\"London\",\n" +
                                    "         \"ro\":\"Londra\",\n" +
                                    "         \"rm\":\"Londra\",\n" +
                                    "         \"ff\":\"London\",\n" +
                                    "         \"kk\":\"Лондон\",\n" +
                                    "         \"uk\":\"Лондон\",\n" +
                                    "         \"pt\":\"Londres\",\n" +
                                    "         \"tr\":\"Londra\",\n" +
                                    "         \"eu\":\"Londres\",\n" +
                                    "         \"ht\":\"Lonn\",\n" +
                                    "         \"ka\":\"ლონდონი\",\n" +
                                    "         \"ur\":\"علاقہ لندن\"\n" +
                                    "      },\n" +
                                    "      \"lat\":51.5073219,\n" +
                                    "      \"lon\":-0.1276474,\n" +
                                    "      \"country\":\"GB\",\n" +
                                    "      \"state\":\"England\"\n" +
                                    "   },\n" +
                                    "   {\n" +
                                    "      \"name\":\"City of London\",\n" +
                                    "      \"local_names\":{\n" +
                                    "         \"es\":\"City de Londres\",\n" +
                                    "         \"ru\":\"Сити\",\n" +
                                    "         \"ur\":\"لندن شہر\",\n" +
                                    "         \"zh\":\"倫敦市\",\n" +
                                    "         \"en\":\"City of London\",\n" +
                                    "         \"pt\":\"Cidade de Londres\",\n" +
                                    "         \"fr\":\"Cité de Londres\",\n" +
                                    "         \"uk\":\"Лондонське Сіті\",\n" +
                                    "         \"he\":\"הסיטי של לונדון\",\n" +
                                    "         \"hi\":\"सिटी ऑफ़ लंदन\",\n" +
                                    "         \"ko\":\"시티 오브 런던\",\n" +
                                    "         \"lt\":\"Londono Sitis\"\n" +
                                    "      },\n" +
                                    "      \"lat\":51.5156177,\n" +
                                    "      \"lon\":-0.0919983,\n" +
                                    "      \"country\":\"GB\",\n" +
                                    "      \"state\":\"England\"\n" +
                                    "   },\n" +
                                    "   {\n" +
                                    "      \"name\":\"London\",\n" +
                                    "      \"local_names\":{\n" +
                                    "         \"el\":\"Λόντον\",\n" +
                                    "         \"fr\":\"London\",\n" +
                                    "         \"oj\":\"Baketigweyaang\",\n" +
                                    "         \"en\":\"London\",\n" +
                                    "         \"bn\":\"লন্ডন\",\n" +
                                    "         \"be\":\"Лондан\",\n" +
                                    "         \"ko\":\"런던\",\n" +
                                    "         \"he\":\"לונדון\",\n" +
                                    "         \"ru\":\"Лондон\",\n" +
                                    "         \"lt\":\"Londonas\",\n" +
                                    "         \"hy\":\"Լոնտոն\",\n" +
                                    "         \"ga\":\"Londain\",\n" +
                                    "         \"ja\":\"ロンドン\",\n" +
                                    "         \"yi\":\"לאנדאן\",\n" +
                                    "         \"cr\":\"ᓬᐊᐣᑕᐣ\",\n" +
                                    "         \"iu\":\"ᓚᓐᑕᓐ\",\n" +
                                    "         \"ar\":\"لندن\",\n" +
                                    "         \"lv\":\"Landona\",\n" +
                                    "         \"fa\":\"لندن\",\n" +
                                    "         \"ug\":\"لوندۇن\",\n" +
                                    "         \"th\":\"ลอนดอน\",\n" +
                                    "         \"ka\":\"ლონდონი\"\n" +
                                    "      },\n" +
                                    "      \"lat\":42.9832406,\n" +
                                    "      \"lon\":-81.243372,\n" +
                                    "      \"country\":\"CA\",\n" +
                                    "      \"state\":\"Ontario\"\n" +
                                    "   },\n" +
                                    "   {\n" +
                                    "      \"name\":\"Chelsea\",\n" +
                                    "      \"local_names\":{\n" +
                                    "         \"id\":\"Chelsea, London\",\n" +
                                    "         \"uk\":\"Челсі\",\n" +
                                    "         \"hi\":\"चेल्सी, लंदन\",\n" +
                                    "         \"ga\":\"Chelsea\",\n" +
                                    "         \"es\":\"Chelsea\",\n" +
                                    "         \"de\":\"Chelsea\",\n" +
                                    "         \"af\":\"Chelsea, Londen\",\n" +
                                    "         \"vi\":\"Chelsea, Luân Đôn\",\n" +
                                    "         \"pl\":\"Chelsea\",\n" +
                                    "         \"pt\":\"Chelsea\",\n" +
                                    "         \"da\":\"Chelsea\",\n" +
                                    "         \"ko\":\"첼시\",\n" +
                                    "         \"sv\":\"Chelsea, London\",\n" +
                                    "         \"nl\":\"Chelsea\",\n" +
                                    "         \"az\":\"Çelsi\",\n" +
                                    "         \"it\":\"Chelsea\",\n" +
                                    "         \"hu\":\"Chelsea\",\n" +
                                    "         \"no\":\"Chelsea\",\n" +
                                    "         \"fr\":\"Chelsea\",\n" +
                                    "         \"he\":\"צ'לסי\",\n" +
                                    "         \"eu\":\"Chelsea\",\n" +
                                    "         \"ru\":\"Челси\",\n" +
                                    "         \"ar\":\"تشيلسي\",\n" +
                                    "         \"en\":\"Chelsea\",\n" +
                                    "         \"el\":\"Τσέλσι\",\n" +
                                    "         \"tr\":\"Chelsea, Londra\",\n" +
                                    "         \"zh\":\"車路士\",\n" +
                                    "         \"sh\":\"Chelsea, London\",\n" +
                                    "         \"ja\":\"チェルシー\",\n" +
                                    "         \"ur\":\"چیلسی، لندن\",\n" +
                                    "         \"sk\":\"Chelsea\",\n" +
                                    "         \"fa\":\"چلسی\",\n" +
                                    "         \"et\":\"Chelsea\"\n" +
                                    "      },\n" +
                                    "      \"lat\":51.4875167,\n" +
                                    "      \"lon\":-0.1687007,\n" +
                                    "      \"country\":\"GB\",\n" +
                                    "      \"state\":\"England\"\n" +
                                    "   },\n" +
                                    "   {\n" +
                                    "      \"name\":\"London\",\n" +
                                    "      \"lat\":37.1289771,\n" +
                                    "      \"lon\":-84.0832646,\n" +
                                    "      \"country\":\"US\",\n" +
                                    "      \"state\":\"Kentucky\"\n" +
                                    "   }\n" +
                                    "]\n" +
                                    "                \n" +
                                    "\n" +
                                    "              "
                        )
                }
                return MockResponse().setResponseCode(404)
            }
        }

        server.dispatcher = dispatcher
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `Geocoding API response parses correctly`() {
        val call = apiService.fetchGeocodingDataByCityName("London")
        val geocodingData = call.execute().body()

        val request = server.takeRequest()
        //Check created url
        assertEquals(
            request.requestUrl?.toUrl().toString(),
            "http://localhost:8080/geo/1.0/direct?q=London&appid=211a182a81e0424386f215c039d74026"
        )

        //Check if is not null the response
        assertNotNull(geocodingData)

        //Check data size is not zero
        assertEquals(geocodingData?.size, 5)

        //Check first item is not null
        assertNotNull(geocodingData!![0])

        //Check coordinates the first item are equals London's coordinates
        //lat:51.5073219
        //lon:-0.1276474
        assertEquals(geocodingData[0].lat, 51.5073219)
        assertEquals(geocodingData[0].lon, -0.1276474)

        //Check city name
        assertEquals(geocodingData[0].cityName, "London")
    }

    @Test
    fun `Get weather data by coordinates API response parses correctly`() {
        val call = apiService.fetchWeatherDataByCoordinates(10.99, 44.34)
        val weatherData = call.execute().body()
        val request = server.takeRequest()
        //Check created url
        assertEquals(
            request.requestUrl?.toUrl().toString(),
            "http://localhost:8080/data/2.5/weather?lon=10.99&lat=44.34&appid=211a182a81e0424386f215c039d74026&units=metric"
        )

        //Check if is not null the response
        assertNotNull(weatherData)

        //Check coordinates are equals
        assertEquals(weatherData?.coordinates?.lon, 10.99)
        assertEquals(weatherData?.coordinates?.lat, 44.34)

        //Check city name
        assertEquals(weatherData?.cityName, "Zocca")

        //Check weather condition size is not zero
        assertEquals(weatherData?.weatherCondition?.size, 1)

        //Check temperature data is not null
        assertNotNull(weatherData?.temperature)

        //Check wind speed data is not null
        assertNotNull(weatherData?.wind)

        //Check sun behavior data is not null
        assertNotNull(weatherData?.sunBehavior)
    }
}
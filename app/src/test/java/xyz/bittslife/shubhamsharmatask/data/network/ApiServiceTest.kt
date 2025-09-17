package xyz.bittslife.shubhamsharmatask.data.network

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getHoldings returns expected response`() = runBlocking {
        val json = """
			{"data":{"userHolding":[{"avgPrice":100.0,"close":110.0,"ltp":120.0,"quantity":2,"symbol":"ABC","pnl":0.0}]}}
		""".trimIndent()
        server.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = api.getHoldings()
        assertEquals(1, response.data.userHolding.size)
        val holding = response.data.userHolding[0]
        assertEquals(100.0, holding.avgPrice, 0.0)
        assertEquals("ABC", holding.symbol)
    }

    @Test
    fun `getHoldings throws on error response`() = runBlocking {
        server.enqueue(MockResponse().setResponseCode(500))
        try {
            api.getHoldings()
            fail("Expected exception not thrown")
        } catch (e: Exception) {
            assertTrue(e is HttpException || e is IOException)
        }
    }
}

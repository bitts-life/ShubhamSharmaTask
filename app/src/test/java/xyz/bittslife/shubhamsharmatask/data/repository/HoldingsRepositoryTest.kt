package xyz.bittslife.shubhamsharmatask.data.repository

import io.mockk.mockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import xyz.bittslife.shubhamsharmatask.data.db.HoldingsDao
import xyz.bittslife.shubhamsharmatask.data.model.Data
import xyz.bittslife.shubhamsharmatask.data.model.PortfolioSummary
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding
import xyz.bittslife.shubhamsharmatask.data.model.UserHoldingResponse
import xyz.bittslife.shubhamsharmatask.data.network.ApiService
import xyz.bittslife.shubhamsharmatask.data.repositories.HoldingsRepository

@ExperimentalCoroutinesApi
class HoldingsRepositoryTest {

    private lateinit var repository: HoldingsRepository
    private val api: ApiService = mockk()
    private val dao: HoldingsDao = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = HoldingsRepository(api, dao)
    }
    
    @Test
    fun `fetchHoldings returns userHoldings and inserts when not empty`() = runBlocking {
        val holding = UserHolding(
            avgPrice = 100.0,
            close = 110.0,
            ltp = 120.0,
            quantity = 2,
            symbol = "ABC",
            pnl = 0.0
        )
        val response = UserHoldingResponse(Data(listOf(holding)))
        coEvery { api.getHoldings() } returns response
        coEvery { dao.insertAll(any()) } returns Unit

        val result = repository.fetchHoldings()
        // pnl = (close - ltp) * quantity = (110-120)*2 = -20.0
        val expected = listOf(holding.copy(pnl = -20.0))
        assertEquals(expected, result)
        coVerify { dao.insertAll(expected) }
    }

    @Test
    fun `fetchHoldings returns emptyList when userHolding is empty`() = runBlocking {
        val response = UserHoldingResponse(Data(emptyList()))
        coEvery { api.getHoldings() } returns response

        val result = repository.fetchHoldings()
        assertEquals(emptyList<UserHolding>(), result)
        // dao.insertAll should not be called
        coVerify(exactly = 0) { dao.insertAll(any()) }
    }

    @Test
    fun `holdingsFromDb delegates to dao`() {
        val holdings = listOf(UserHolding(1.0, 2.0, 3.0, 4, "SYM", 5.0))
        every { dao.getAll() } returns holdings
        val result = repository.holdingsFromDb()
        assertEquals(holdings, result)
        verify { dao.getAll() }
    }

    @Test
    fun `calculatePortfolioSummary computes correct values`() {
        val holdings = listOf(
            UserHolding(
                avgPrice = 100.0,
                close = 110.0,
                ltp = 120.0,
                quantity = 2,
                symbol = "A",
                pnl = 0.0
            ),
            UserHolding(
                avgPrice = 200.0,
                close = 210.0,
                ltp = 220.0,
                quantity = 1,
                symbol = "B",
                pnl = 0.0
            )
        )
        val summary = repository.calculatePortfolioSummary(holdings)
        // currentValue = 120*2 + 220*1 = 460
        // totalInvestment = 100*2 + 200*1 = 400
        // totalPNL = 460-400 = 60
        // todaysPNL = (110-120)*2 + (210-220)*1 = -20 + -10 = -30
        val expected = PortfolioSummary(460.0, 400.0, 60.0, -30.0)
        assertEquals(expected, summary)
    }

    @Test
    fun `calculatePortfolioSummary with empty list returns zeros`() {
        val summary = repository.calculatePortfolioSummary(emptyList())
        val expected = PortfolioSummary(0.0, 0.0, 0.0, 0.0)
        assertEquals(expected, summary)
    }

    @Test
    fun `calculatePortfolioSummary with negative values`() {
        val holdings = listOf(
            UserHolding(
                avgPrice = -100.0,
                close = -110.0,
                ltp = -120.0,
                quantity = 2,
                symbol = "A",
                pnl = 0.0
            )
        )
        val summary = repository.calculatePortfolioSummary(holdings)
        // currentValue = -120*2 = -240
        // totalInvestment = -100*2 = -200
        // totalPNL = -240-(-200) = -40
        // todaysPNL = (-110-(-120))*2 = (10)*2 = 20
        val expected = PortfolioSummary(-240.0, -200.0, -40.0, 20.0)
        assertEquals(expected, summary)
    }
}

package xyz.bittslife.shubhamsharmatask.data.repositories

import xyz.bittslife.shubhamsharmatask.data.db.HoldingsDao
import xyz.bittslife.shubhamsharmatask.data.model.PortfolioSummary
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding
import xyz.bittslife.shubhamsharmatask.data.network.ApiService
import javax.inject.Inject

class HoldingsRepository @Inject constructor(private val api: ApiService, private val dao: HoldingsDao) {

    suspend fun fetchHoldings(): List<UserHolding> {
        api.getHoldings().let { response ->
            if (response.data.userHolding.isNotEmpty()) {
                val userHoldings =  response.data.userHolding.map {
                    it.copy(
                        pnl = (it.close - it.ltp) * it.quantity
                    )
                }
                dao.insertAll(userHoldings)
                return userHoldings
            }
        }
        return emptyList()
    }

    fun holdingsFromDb() = dao.getAll()

    fun calculatePortfolioSummary(holdings: List<UserHolding>): PortfolioSummary {
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        val totalPNL = currentValue - totalInvestment
        val todaysPNL = holdings.sumOf { (it.close - it.ltp) * it.quantity }
        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = totalPNL,
            todaysPNL = todaysPNL
        )
    }
}
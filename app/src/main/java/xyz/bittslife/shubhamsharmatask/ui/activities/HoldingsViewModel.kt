package xyz.bittslife.shubhamsharmatask.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.bittslife.shubhamsharmatask.data.model.PortfolioSummary
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding
import xyz.bittslife.shubhamsharmatask.data.repositories.HoldingsRepository
import javax.inject.Inject

data class HoldingsUiState(
    val holdings: List<UserHolding> = emptyList(),
    val expanded: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val portfolioSummary: PortfolioSummary = PortfolioSummary(0.0, 0.0, 0.0, 0.0)
)

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repo: HoldingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoldingsUiState())
    val uiState: StateFlow<HoldingsUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            try {
                var holdings = repo.holdingsFromDb()
                if (holdings.isEmpty()) {
                    holdings = repo.fetchHoldings()
                }
                val summary = repo.calculatePortfolioSummary(holdings)
                _uiState.value = _uiState.value.copy(
                    holdings = holdings,
                    loading = false,
                    error = null,
                    portfolioSummary = summary
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(loading = false, error = e.localizedMessage ?: "Unknown")
            }
        }
    }

    fun toggleExpand() {
        _uiState.value = _uiState.value.copy(expanded = !_uiState.value.expanded)
    }
}
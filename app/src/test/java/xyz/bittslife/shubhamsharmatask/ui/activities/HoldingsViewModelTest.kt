package xyz.bittslife.shubhamsharmatask.ui.activities

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding
import xyz.bittslife.shubhamsharmatask.data.repositories.HoldingsRepository

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private lateinit var repo: HoldingsRepository
    private lateinit var viewModel: HoldingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = mock(HoldingsRepository::class.java)
        viewModel = HoldingsViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggleExpand() toggles expanded`() {
        // Initial state: expanded = false
        assertEquals(false, viewModel.uiState.value.expanded)
        viewModel.toggleExpand()
        assertEquals(true, viewModel.uiState.value.expanded)
        viewModel.toggleExpand()
        assertEquals(false, viewModel.uiState.value.expanded)
    }

    @Test
    fun `load() sets error on exception`() = runBlocking {
        val errorMsg = "Something went wrong"
        `when`(repo.holdingsFromDb()).thenThrow(kotlin.RuntimeException(errorMsg))

        viewModel.load()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(false, state.loading)
        assertEquals(errorMsg, state.error)
        assertEquals(emptyList<UserHolding>(), state.holdings)
    }
}

package xyz.bittslife.shubhamsharmatask.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import xyz.bittslife.shubhamsharmatask.ui.activities.componenets.HoldingsScreen
import xyz.bittslife.shubhamsharmatask.ui.activities.componenets.PortfolioTopBar

@AndroidEntryPoint
class HoldingsActivity : ComponentActivity() {

    private val viewModel: HoldingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    val uiState by viewModel.uiState.collectAsState()
                    Scaffold(
                        topBar = {
                            PortfolioTopBar()
                        }
                    ) { paddingValues ->
                        HoldingsScreen(
                            uiState = uiState,
                            onToggleExpand = { viewModel.toggleExpand() },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
        viewModel.load()
    }
}
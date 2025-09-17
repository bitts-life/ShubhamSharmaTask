package xyz.bittslife.shubhamsharmatask.ui.activities.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.bittslife.shubhamsharmatask.data.model.UserHolding
import xyz.bittslife.shubhamsharmatask.ui.activities.HoldingsUiState
import xyz.bittslife.shubhamsharmatask.ui.theme.ColorTextGreen
import xyz.bittslife.shubhamsharmatask.ui.theme.ColorTextRed
import xyz.bittslife.shubhamsharmatask.utils.Utils

@Composable
fun HoldingsScreen(
    uiState: HoldingsUiState,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Text(
                text = "Error: ${uiState.error}",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp)
                    .background(Color.White)
            ) {
                items(uiState.holdings) { holding ->
                    HoldingItem(holding = holding)
                    HorizontalDivider(
                        Modifier.padding(vertical = 12.dp),
                        DividerDefaults.Thickness,
                        DividerDefaults.color
                    )
                }
            }
        }

        // Summary at bottom
        SummaryCard(
            portfolioSummary = uiState.portfolioSummary,
            expanded = uiState.expanded,
            onToggle = onToggleExpand,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Composable
fun HoldingItem(holding: UserHolding) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left side
        Column {
            Text(
                text = holding.symbol,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "NET QTY:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${holding.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }

        // Right side
        Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "LTP:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "₹ ${Utils.formatNumberWithCommas(holding.ltp)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "P&L:",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (holding.pnl >= 0) ColorTextGreen else ColorTextRed,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "₹ ${Utils.formatNumberWithCommas(holding.pnl)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (holding.pnl >= 0) ColorTextGreen else ColorTextRed,
                )
            }
        }
    }
}

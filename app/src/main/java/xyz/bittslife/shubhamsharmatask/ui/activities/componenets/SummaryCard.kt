package xyz.bittslife.shubhamsharmatask.ui.activities.componenets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.bittslife.shubhamsharmatask.data.model.PortfolioSummary
import xyz.bittslife.shubhamsharmatask.ui.theme.ColorTextGreen
import xyz.bittslife.shubhamsharmatask.ui.theme.ColorTextRed
import xyz.bittslife.shubhamsharmatask.utils.Utils

@Composable
fun SummaryCard(
    portfolioSummary: PortfolioSummary,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 16.dp),
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            // Top row
            AnimatedVisibility(visible = expanded) {
                Column(Modifier.padding(top = 12.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Current value*", color = Color.Gray)
                        Text(
                            "₹ ${Utils.formatNumberWithCommas(portfolioSummary.currentValue)}",
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total investment*", color = Color.Gray)
                        Text(
                            "₹ ${Utils.formatNumberWithCommas(portfolioSummary.totalInvestment)}",
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Today's Profit & Loss*", color = Color.Gray)
                        Text(
                            "₹ ${Utils.formatNumberWithCommas(portfolioSummary.todaysPNL)}",
                            color = if (portfolioSummary.todaysPNL > 0) ColorTextGreen else ColorTextRed
                        )
                    }
                    HorizontalDivider(
                        Modifier.padding(vertical = 12.dp),
                        DividerDefaults.Thickness,
                        DividerDefaults.color
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Profit & Loss*", color = Color.Gray, fontWeight = FontWeight.Medium)
                    IconButton(onClick = onToggle) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    }
                }
                Text(
                    text = "₹ ${Utils.formatNumberWithCommas(portfolioSummary.totalPNL)}",
                    color = if (portfolioSummary.totalPNL > 0) ColorTextGreen else ColorTextRed
                )
            }
        }
    }
}
package xyz.bittslife.shubhamsharmatask.ui.activities.componenets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.bittslife.shubhamsharmatask.R
import xyz.bittslife.shubhamsharmatask.ui.theme.ColorDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioTopBar() {
    TopAppBar(
        title = {
            Row {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Portfolio",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.sort),
                    modifier = Modifier.size(20.dp),
                    contentDescription = "Sort",
                    tint = Color.White
                )
            }
            VerticalDivider(
                color = Color(0xFF1F4C78),
                thickness = 1.dp,
                modifier = Modifier.height(24.dp)
            )
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorDarkBlue // Dark Blue
        ),
        modifier = Modifier.padding(horizontal = 0.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun PortfolioTopBarPreview() {
    PortfolioTopBar()
}
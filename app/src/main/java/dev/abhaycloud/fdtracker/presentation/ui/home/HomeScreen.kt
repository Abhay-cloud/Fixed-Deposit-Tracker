package dev.abhaycloud.fdtracker.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.abhaycloud.fdtracker.R
import dev.abhaycloud.fdtracker.presentation.navigation.FixedDepositNavigationScreens
import dev.abhaycloud.fdtracker.presentation.theme.primaryLight
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositItem
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositSortDropDown
import dev.abhaycloud.fdtracker.utils.Utils.toJson

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val fixedDepositList by viewModel.getAllFixedDepositList.collectAsState()
    val totalInvestedAmount by viewModel.getTotalInvestedAmount.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()
    var isSortOptionExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "FD Tracker", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
        }
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Investment",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xff787878)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹$totalInvestedAmount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Investments", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Box {
                    Card(
                        modifier = Modifier.clip(RoundedCornerShape(30.dp)).clickable {
                            isSortOptionExpanded = true
                        }, colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_sort_24),
                                contentDescription = "sort",
                                modifier = Modifier
                                    .size(width = 18.dp, height = 24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Sort", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    FixedDepositSortDropDown(
                        expanded = isSortOptionExpanded,
                        selectedOption = sortOption,
                        onDismiss = { isSortOptionExpanded = false }) {
                        isSortOptionExpanded = false
                        when (it) {
                            SortingOptions.CLEAR -> viewModel.updateSortOrder(it)
                            SortingOptions.START_DATE_ASC -> viewModel.updateSortOrder(it)
                            SortingOptions.MATURITY_DATE_DESC -> viewModel.updateSortOrder(it)
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(fixedDepositList, key = {
            it.id
        }) {
            FixedDepositItem(
                modifier = Modifier.animateItemPlacement(),
                fixedDeposit = it,
                animatedVisibilityScope = animatedVisibilityScope
            ) {
                navController.navigate("${FixedDepositNavigationScreens.ViewFixedDeposit.route}/${it.toJson()}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

//@Preview(
//    showSystemUi = true, device = "spec:width=1080px,height=2340px,dpi=440",
//    uiMode = UI_MODE_NIGHT_YES
//)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}
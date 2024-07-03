package dev.abhaycloud.fdtracker.presentation.ui.home

import androidx.compose.animation.AnimatedVisibilityScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.rememberCalendarState
import dev.abhaycloud.fdtracker.R
import dev.abhaycloud.fdtracker.presentation.navigation.FixedDepositNavigationScreens
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositItem
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositMaturityDateDialog
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositSortDropDown
import dev.abhaycloud.fdtracker.utils.DateUtils.toLocalDate
import dev.abhaycloud.fdtracker.utils.Utils.toIndianFormat
import dev.abhaycloud.fdtracker.utils.Utils.toJson
import kotlinx.coroutines.launch
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val fixedDepositList by viewModel.getAllFixedDepositList.collectAsState()
    val totalInvestedAmount by viewModel.getTotalInvestedAmount.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()
    val maturityDates by viewModel.maturityDates.collectAsState()
    var isSortOptionExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var showMaturityDateDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val listState = rememberLazyListState()
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(200) }
    val endMonth = remember { currentMonth.plusMonths(200) }
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth
    )
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "FD Tracker", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = {
                    showMaturityDateDialog = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_calendar_month_24),
                        contentDescription = "calendar"
                    )
                }
            }
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
                    text = "₹${totalInvestedAmount.toIndianFormat(includeDecimal = true)}",
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
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .clickable {
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

    if (showMaturityDateDialog) {
        FixedDepositMaturityDateDialog(
            state = calendarState,
            maturityDates = maturityDates,
            onDismiss = {
                showMaturityDateDialog = false
            }
        ) { date ->
            val index = fixedDepositList.indexOfFirst { it.maturityDate.toLocalDate() == date }
            if (index != -1) {
                scope.launch {
                    listState.animateScrollToItem(index)
                }
            }
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
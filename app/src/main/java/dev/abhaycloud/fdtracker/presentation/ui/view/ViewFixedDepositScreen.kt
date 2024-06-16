package dev.abhaycloud.fdtracker.presentation.ui.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ViewFixedDepositScreen(
    navController: NavController,
    fixedDeposit: FixedDeposit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        navController.popBackStack()
        onBackPressed()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .sharedElement(state = rememberSharedContentState(key = "card_${fixedDeposit.id}"), animatedVisibilityScope = animatedVisibilityScope)
            .background(Color(0xff001F3F))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
//            modifier = Modifier.sharedBounds(
//                sharedContentState = rememberSharedContentState(key = "bank_name_${fixedDeposit.id}"),
//                animatedVisibilityScope = animatedVisibilityScope)
             text = "SBI", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Principle Amount",
            value = "₹${fixedDeposit.principalAmount}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Maturity Amount",
            value = "₹${fixedDeposit.maturityAmount}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Annual interest rate",
            value = "${fixedDeposit.interestRate}%"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Start Date",
            value = "${fixedDeposit.startDate}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Maturity Date",
            value = "${fixedDeposit.maturityDate}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountItem(
            title = "Tenure",
            value = "${fixedDeposit.tenure} days"
        )
    }
}

@Composable
fun AmountItem(title: String, value: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xff787878)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

//@Preview
//@Composable
//fun ViewFixedDepositScreenPreview() {
//    ViewFixedDepositScreen()
//}

package dev.abhaycloud.fdtracker.presentation.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.abhaycloud.fdtracker.R
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.utils.DateUtils.getDifferenceBetweenDays
import dev.abhaycloud.fdtracker.utils.DateUtils.toDateString
import dev.abhaycloud.fdtracker.utils.Utils.getFraction
import java.util.Date

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FixedDepositItem(
    modifier: Modifier,
    fixedDeposit: FixedDeposit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (FixedDeposit) -> Unit
) {
    var completedDays by rememberSaveable {
        mutableIntStateOf(0)
    }
    var remainingDays by rememberSaveable {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = fixedDeposit) {
        completedDays = System.currentTimeMillis().getDifferenceBetweenDays(fixedDeposit.startDate.time)
        remainingDays = fixedDeposit.maturityDate.time.getDifferenceBetweenDays(System.currentTimeMillis())
        Log.d("myapp", "maturity: ${fixedDeposit.maturityDate.time} current: ${System.currentTimeMillis()}")
    }

    val progress = animateFloatAsState(
        targetValue = fixedDeposit.tenure.toDouble().getFraction(completedDays.toDouble()),
        animationSpec = tween(1000),
        label = "progressAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
//            .sharedElement(state = rememberSharedContentState(key = "card_${fixedDeposit.id}"), animatedVisibilityScope = animatedVisibilityScope)
            .clickable {
                onClick.invoke(fixedDeposit)
            }
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xff001F3F),
                        Color(0xe5001F3F)
                    ),
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_fd_card),
            contentDescription = null,
            modifier = Modifier
                .height(84.dp)
                .align(Alignment.TopEnd)
                .offset(y = (-24).dp)
        )
//        Image(
//            painter = painterResource(id = R.drawable.ellipse),
//            contentDescription = null,
//            modifier = Modifier
//                .padding(end = 10.dp)
//                .size(100.dp)
//                .offset(y = 20.dp)
//                .align(Alignment.BottomEnd)
//        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
//                modifier = Modifier.sharedBounds(
//                    sharedContentState = rememberSharedContentState(key = "bank_name_${fixedDeposit.id}"),
//                    animatedVisibilityScope = animatedVisibilityScope
//                ),
                text = fixedDeposit.bankName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    AmountItem(
                        title = "Principle Amount",
                        value = "₹${fixedDeposit.principalAmount}"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AmountItem(
                        title = "Annual Interest Rate",
                        value = "${fixedDeposit.interestRate}%"
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Column {
                    AmountItem(title = "Maturity Amount", value = "₹${fixedDeposit.maturityAmount}")
                    Spacer(modifier = Modifier.height(16.dp))
                    AmountItem(
                        title = "Maturity Date",
                        value = fixedDeposit.maturityDate.time.toDateString()
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                progress = progress.value,
//                progress = fixedDeposit.tenure.toDouble().getFraction(completedDays.toDouble()),
                color = Color(0xff596420),
                trackColor = Color.White,
                strokeCap = StrokeCap.Round
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Completed: $completedDays Days",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "Remaining: $remainingDays Days",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AmountItem(title: String, value: String) {
    Text(
        text = title,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xff787878)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = value,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

//@Preview
//@Composable
//fun FixedDepositItemPreview() {
//    FixedDepositItem()
//}
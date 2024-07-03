package dev.abhaycloud.fdtracker.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dev.abhaycloud.fdtracker.utils.DateUtils.toLocalDate
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun FixedDepositMaturityDateDialog(state: CalendarState, maturityDates: List<Date>, onDismiss: () -> Unit, onDateClick: (LocalDate) -> Unit) {
    val scope = rememberCoroutineScope()
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(10.dp),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Maturity Dates", fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            HorizontalCalendar(
                state = state,
                modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp),
                dayContent = {
                    Day(day = it, isMaturityDay = it.date in maturityDates.map { it.toLocalDate() }){
                        onDateClick.invoke(it)
                        onDismiss()
                    }
                },
                monthHeader = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                state.animateScrollToMonth(it.yearMonth.previousMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "previous"
                            )
                        }
                        Text(text = "${it.yearMonth.month.name}, ${it.yearMonth.year}")
                        IconButton(onClick = {
                            scope.launch {
                                state.animateScrollToMonth(it.yearMonth.nextMonth)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowForward,
                                contentDescription = "next"
                            )
                        }
                    }
                    val daysOfWeek = it.weekDays.first().map { it.date.dayOfWeek }
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                }
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, isMaturityDay: Boolean, onDateClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isMaturityDay) MaterialTheme.colorScheme.onSurface else Color.Transparent)
            .clickable {
                onDateClick.invoke(day.date)
            },
        contentAlignment = Alignment.Center
    ) {
        if (isMaturityDay){
            Text(text = day.date.dayOfMonth.toString(), color = MaterialTheme.colorScheme.onTertiary)
        } else {
            Text(text = day.date.dayOfMonth.toString())
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}
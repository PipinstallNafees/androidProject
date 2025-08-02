package com.example.cinemaxApp.feature.user.movieBooking.view

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.model.Seat
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel


@Preview(showBackground = true)
@Composable
fun SeatBookingScreenPreview() {
    SeatBookingScreen(nav = rememberNavController(), viewModel = viewModel())
}


@Composable
fun SeatBookingScreen(nav: NavHostController, viewModel: UserBookingViewModel) {
    viewModel.getAllSeatData()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B1E25)),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // State for zoom and pan
            var scale = remember { mutableFloatStateOf(1f) }
            var offsetX = remember { mutableFloatStateOf(0f) }
            var offsetY = remember { mutableFloatStateOf(0f) }

            // State for selected seats
            var selectedSeats = remember { mutableStateOf(setOf<String>()) }
            var seatMatrixSize = remember { mutableStateOf(IntSize.Zero) }

            SelectSeatsTopBar({nav.popBackStack()})
            Spacer(modifier = Modifier.height(100.dp))

            Slider(
                value = scale.value,
                onValueChange = {
                    scale.value = it
                    clampOffset(scale.value, seatMatrixSize, offsetX, offsetY)
                },
                valueRange = 1f..4f,
//            steps = 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFEB5757),
                    activeTrackColor = Color(0xFFEB5757),
                    inactiveTrackColor = Color(0xFFE6E0E9),
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                )

            )
            ZoomableSeatSelection(scale, offsetX, offsetY, selectedSeats, seatMatrixSize, viewModel)
            Spacer(modifier = Modifier.height(8.dp))
            SeatStatusLegend()
            Spacer(modifier = Modifier.height(16.dp))
            ScaleControler(scale, offsetX, offsetY, seatMatrixSize)
            Spacer(modifier = Modifier.height(16.dp))
            SelectedSeatCard(selectedSeats)

            Spacer(modifier = Modifier.weight(1f))
            Button(
                {
                    viewModel.bookTicket(selectedSeats)

                    nav.navigate(Screen.UserDashboard.route) {
                        popUpTo(Screen.UserDashboard.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEB5757), // background color
                    contentColor = Color.White          // text/icon color
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    "Book Ticket",
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

        }
    }

}


@Composable
fun SelectSeatsTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 40.dp)
            .background(Color(0xFF1B1E25)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Select Seats",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        // Left-aligned Back Icon
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun ZoomableSeatSelection(
    scale: MutableState<Float>,
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    selectedSeats: MutableState<Set<String>>,
    seatMatrixSize: MutableState<IntSize>,
    viewModel: UserBookingViewModel

) {
    // Vars for Seat Matrix
    val currentContext = LocalContext.current
    var rowChar = '@'


    val animatedScale by animateFloatAsState(
        targetValue = scale.value,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "Zoom Animation"
    )


    // Min and max zoom levels
    val minScale = 1f
    val maxScale = 4f

    Box(
        modifier = Modifier
//            .background(Color.Black)
            .clipToBounds()
            .clip(RoundedCornerShape(8.dp))
            .onGloballyPositioned { coordinates ->
                seatMatrixSize.value = coordinates.size // Gives width and height in px
            }
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF636882))
                .padding(10.dp)
                .graphicsLayer(
//                    scaleX = scale.value,
//                    scaleY = scale.value,
                    scaleX = animatedScale,
                    scaleY = animatedScale,
                    translationX = offsetX.value,
                    translationY = offsetY.value
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // Handle zoom
                        val newScale = (scale.value * zoom).coerceIn(minScale, maxScale)
                        scale.value = newScale

                        // Handle pan
                        offsetX.value += (pan.x * newScale)
                        offsetY.value += (pan.y * newScale)

                        // Constrain panning to keep content visible
                        val maxOffsetX = (size.width * (scale.value - 1)) / 2
                        val maxOffsetY = (size.height * (scale.value - 1)) / 2

                        offsetX.value = offsetX.value.coerceIn(-maxOffsetX, maxOffsetX)
                        offsetY.value = offsetY.value.coerceIn(-maxOffsetY, maxOffsetY)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Screen indicator
            Text(
                text = "All eyes this way please",
                color = Color.White,
                fontSize = 7.sp, // ((7 * scale.value).coerceAtMost(20f)).sp,
                modifier = Modifier.padding(1.dp)
            )
//            Spacer(modifier = Modifier.height(1.dp))

            // Screen box
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(100.dp)
                    .padding(1.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(30.dp))

            var seatMap = viewModel.seatMap
            // Hardcoded Seat matrix
            repeat(13) { row ->
                if (row != 5) {
                    rowChar++
                }
                var colNum = 0

                Row {
                    repeat(30) { col ->
                        if (row == 5 || col == 7 || col == 22) {
                            // Empty space
                            Box(modifier = Modifier.size(9.dp))
                        } else {
                            colNum++
                            val seatLabel = "$rowChar$colNum"
                            val isSelected = seatLabel in selectedSeats.value
                            val isBooked = seatMap.value[seatLabel]?.isBooked == true
                            val seatBookableCount = viewModel.attendeeList.size
                            var seatSelectionAllowed = seatBookableCount > selectedSeats.value.size

                            // Seat
                            Box(
                                modifier = Modifier
                                    .size(11.dp)
                                    .padding(1.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        when {
                                            isBooked -> Color(0xFFEB5757)        // Booked - Red
                                            isSelected -> Color(0xFF6FCF97)      // Selected - Green => Color.Green
                                            else -> Color.LightGray              // Available
                                        }
                                    )
                                    .clickable {
                                        if (seatMap.value[seatLabel]?.isBooked == true) {
                                            Toast.makeText(
                                                currentContext,
                                                "Seat Already Booked, Select Another",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            if (isSelected) {
                                                selectedSeats.value = selectedSeats.value - seatLabel
                                            } else if (!isSelected && seatSelectionAllowed){
                                                selectedSeats.value = selectedSeats.value + seatLabel
                                            } else if (!isSelected && !seatSelectionAllowed) {
                                                Toast.makeText(
                                                    currentContext,
                                                    "Already Selected ${seatBookableCount} Seat",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = seatLabel,
                                    fontSize = 3.sp,
                                    fontWeight = FontWeight.W900,
                                    fontFamily = FontFamily.Default,
                                    color = if (isSelected) Color.White else Color.Black,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 3.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip,
                                    softWrap = false,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center) //
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}




@Composable
fun ScaleControler(
    scale: MutableState<Float>,
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
    seatMatrixSize: MutableState<IntSize>
) {
    val minScale = 1f
    val maxScale = 4f
    Row(
        modifier = Modifier
//            .align(Alignment.BottomEnd)
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                scale.value = (scale.value * 1.2f).coerceAtMost(maxScale)
                clampOffset(scale.value, seatMatrixSize, offsetX, offsetY)
            },
            modifier = Modifier.size(48.dp),
            containerColor = Color(0xFFE6E0E9)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Zoom In")
        }

        Spacer(modifier = Modifier.width(200.dp))

        FloatingActionButton(
            onClick = {
                scale.value = (scale.value / 1.2f).coerceAtLeast(minScale)
                clampOffset(scale.value, seatMatrixSize, offsetX, offsetY)
                if (scale.value == minScale) {
                    offsetX.value = 0f
                    offsetY.value = 0f
                }
            },
            modifier = Modifier.size(48.dp),
            containerColor = Color(0xFFE6E0E9)
        ) {
            Icon(Icons.Default.Clear, contentDescription = "Zoom Out")
        }
    }

}


@Composable
fun SelectedSeatCard(selectedSeats: MutableState<Set<String>>) {
        val emptyStr = ""
//    if (selectedSeats.value.isNotEmpty()) {
        Card(
            modifier = Modifier
//                .align(Alignment.TopStart)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE6E0E9)
            )

        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    "Selected Seats: ${if (selectedSeats.value.isNotEmpty()) selectedSeats.value.size else emptyStr}",
                    fontWeight = FontWeight.Bold
                )
                if (selectedSeats.value.isNotEmpty()) {
                    Text(
                        selectedSeats.value.joinToString(", "),
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
//    }
}

fun clampOffset(
    scale: Float,
    seatMatrixSize: MutableState<IntSize>,
    offsetX: MutableState<Float>,
    offsetY: MutableState<Float>,
) {
    val contentWidth = seatMatrixSize.value.width * scale
    val contentHeight = seatMatrixSize.value.height * scale

    val maxX = (contentWidth - seatMatrixSize.value.width) / 2f
    val maxY = (contentHeight - seatMatrixSize.value.height) / 2f

    offsetX.value = offsetX.value.coerceIn(-maxX, maxX)
    offsetY.value = offsetY.value.coerceIn(-maxY, maxY)
}


@Composable
fun SeatStatusLegend() {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(color = Color(0xFF6FCF97), label = "Selected")
        Spacer(modifier = Modifier.width(16.dp))
        LegendItem(color = Color(0xFFEB5757), label = "Reserved")
        Spacer(modifier = Modifier.width(16.dp))
        LegendItem(
            color = Color.LightGray,
            borderColor = Color(0xFFBDBDBD),
            label = "Available"
        )
    }
}

@Composable
fun LegendItem(
    color: Color,
    borderColor: Color = Color.Transparent,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFFE0E0E0)
        )
    }
}

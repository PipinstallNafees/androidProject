package com.example.cinemaxApp.feature.admin.addMovie.view

import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import com.example.cinemaxApp.feature.user.auth.view.capitalizeWords
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun EditMovieScreen(nav: NavHostController, viewModel: MovieAdminViewModel, id: String) {
    val movie = viewModel.getMovieById(id) ?: return

    var title by remember { mutableStateOf(movie.title) }
    var desc by remember { mutableStateOf(movie.description) }
    var totalSeats by remember { mutableStateOf(movie.totalSeats.toString()) }
    var date by remember { mutableStateOf(movie.date) }
    var time by remember { mutableStateOf(movie.time) }
//    var posterUri by remember { mutableStateOf<Uri?>(null) }
    var posterUrl by remember { mutableStateOf(movie.posterUrl) }
    var rating by remember { mutableStateOf(movie.rating.toString()) }
    var director by remember { mutableStateOf(movie.director) }
    var genre by remember { mutableStateOf(movie.genre!!.joinToString(", ")) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val pickedDate = LocalDate.of(year, month + 1, dayOfMonth)
                date = pickedDate.toString()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                val pickedTime = LocalTime.of(hour, minute)
                time = pickedTime.toString()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
    }

//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri -> posterUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF23232F))
                )
            )
//            .padding(24.dp),
            .padding(horizontal = 25.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "✏️ Edit Movie",
//            style = MaterialTheme.typography.headlineMedium.copy(
//                fontWeight = FontWeight.Bold,
//                fontSize = 26.sp,
//                fontFamily = FontFamily.Serif,
//                color = Color.White
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 24.dp)
//        )

        EditMovieTopBar( { nav.popBackStack() } )

        Spacer(Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { newValue ->
                        title = capitalizeWords(newValue)
                    },
                    label = { Text("🎬 Movie Title") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = {
                        desc = it.replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase() else char.toString()
                        }
                    },
                    label = { Text("📝 Description") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = totalSeats,
                    onValueChange = { newText ->
                        totalSeats = newText.filter { it.isDigit() }
                    },
                    label = { Text("🪑 Total Seats") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = director,
                    onValueChange = { newValue ->
                        director = capitalizeWords(newValue)
                    },
                    label = { Text("🎬 Director") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = rating,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            rating = newValue
                        }
                    },
                    label = { Text("⭐ Rating") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = genre,
                    onValueChange = { newValue ->
                        genre = newValue
                            .split(Regex("(?<=,|\\s)")) // split after comma or space but keep the delimiter
                            .joinToString("") { part ->
                                if (part.isNotBlank()) {
                                    // Trim leading spaces before capitalizing
                                    val trimmed = part.trimStart()
                                    val capitalized = trimmed.replaceFirstChar { it.uppercase() }
                                    // Add back any leading spaces
                                    part.take(part.length - trimmed.length) + capitalized
                                } else part
                            }
//                        genre = it
                    },
                    label = { Text("🎭 Genre") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = posterUrl,
                    onValueChange = { posterUrl = it },
                    label = { Text("🔗 Poster Url") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                if (posterUrl.isNotBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(posterUrl),
                        contentDescription = "Poster Preview",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

//                Button(
//                    onClick = { imagePickerLauncher.launch("image/*") },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A148C))
//                ) {
//                    Text("📁 Upload Poster", color = Color.White)
//                }

//                posterUri?.let {
//                    Text(
//                        text = "✅ Selected Image: ${it.lastPathSegment ?: "Unknown"}",
//                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF388E3C))
//                    )
//                } ?: Text(
//                    text = "❌ No image selected",
//                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
//                )

                OutlinedTextField(
                    value = date?.format(dateFormatter) ?: "",
                    onValueChange = {},
                    label = { Text("📅 Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    readOnly = true,
                    enabled = false
                )

                OutlinedTextField(
                    value = time?.format(timeFormatter) ?: "",
                    onValueChange = {},
                    label = { Text("⏰ Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { timePickerDialog.show() },
                    readOnly = true,
                    enabled = false
                )

                Button(
                    onClick = {
                        if (totalSeats.toIntOrNull() != null) {
                            viewModel.updateMovie(
                                movie.copy(
                                    title = title,
                                    description = desc,
                                    totalSeats = totalSeats.toInt(),
                                    posterUrl = posterUrl,
                                    director = director,
                                    rating = rating.toFloat(),
                                    genre = genre.split(",").map { it.trim()},
                                    date = date,
                                    time = time
                                )
                            )
                            nav.popBackStack()
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Update",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun EditMovieTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(Color.Transparent),
//            .background(Color(0xFF121212)), // optional background
        contentAlignment = Alignment.TopStart
    ) {
        // Centered Title
        Text(
            text = "Edit Movie",
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
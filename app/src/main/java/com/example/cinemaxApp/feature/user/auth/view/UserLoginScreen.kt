package com.example.cinemaxApp.feature.user.auth.view


// 🔽 Compose & AndroidX Imports
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserLoginViewModel
import kotlinx.coroutines.launch

@Composable
fun UserLoginScreen(nav: NavHostController, viewModel: UserLoginViewModel) {
    val carbonBlack = Color(0xFF1A1919)
    val mahroon = Color(0xFF710C0C)
    val white = Color.White
    val context = LocalContext.current
    val userId = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val domain = "@silicon.ac.in"
    var coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
            )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
//        Text(
//            text = "🎬 Welcome to Cinemax User",
//            style = MaterialTheme.typography.headlineMedium.copy(
//                color = mahroon,
//                fontFamily = FontFamily.Monospace,
//                fontWeight = FontWeight.Bold
//            ),
//            modifier = Modifier
//                .padding(bottom = 24.dp)
//                .fillMaxWidth()
//        )

        Box(
            modifier = Modifier
                .padding(12.dp)
                .shadow(10.dp, RoundedCornerShape(12.dp))
                .background(white, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
//                    text = "👋 Hello User",
                    text = "User Login",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = carbonBlack
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                HorizontalDivider( thickness = 1.dp, color = mahroon)

//                Text(
//                    text = "Enter your Email ID",
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        fontFamily = FontFamily.Monospace,
//                        fontWeight = FontWeight.SemiBold,
//                        color = carbonBlack
//                    )
//                )

                OutlinedTextField(
                    value = userId.value,
                    onValueChange = { newValue ->
                        // Block "@" and spaces
                        if (!newValue.contains("@") && !newValue.contains(" ")) {
                            userId.value = newValue
                        }
//                        userId.value = it
                    },
                    label = {
                        Text("Email ID", fontFamily = FontFamily.Monospace)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    ),
                    trailingIcon = {
                        Row {
                            Text(domain, fontFamily = FontFamily.Monospace)
                            Spacer(modifier = Modifier.width(15.dp))
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

//                Text(
//                    text = "Enter your Password",
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        fontFamily = FontFamily.Monospace,
//                        fontWeight = FontWeight.SemiBold,
//                        color = carbonBlack
//                    )
//                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { newValue ->
                        if (!newValue.contains(" ")) {
                            password.value = newValue
                        }
//                        password.value = it
                    },
                    label = { Text("Password", fontFamily = FontFamily.Monospace) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility

                        IconButton(onClick = {passwordVisible = !passwordVisible}) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
                TextButton(
                    onClick = {
                        // Navigate to Forgot Password screen
//                        nav.navigate("forgotPassword")
                    },
                    modifier = Modifier.align(Alignment.End)
                        .defaultMinSize(minHeight = 0.dp) // remove default min height
                        .padding(0.dp),                   // remove extra modifier padding
                    contentPadding = PaddingValues(0.dp)  // remove internal content padding
                ) {
                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(
                            color = mahroon,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Button(
                    onClick = {
                        // Handle login logic here
//                        if (userId.value=="cinemax" && password.value=="cinemax") {
//                            // Navigate to admin dashboard or perform login action
//                            nav.navigate("dashboard")
//                        } else {
//                            Toast.makeText(context, "Invalid Admin ID or Password", Toast.LENGTH_SHORT).show()
//                        }

                        coroutineScope.launch {
                            var result = viewModel.login(userId.value + domain, password.value)
                            if (result.isSuccess) {
                                Log.d("UserLoginView", "Login Successful")
                                // Navigate to admin dashboard or perform login action
                                nav.navigate(Screen.UserDashboard.route) {
                                    popUpTo(Screen.UserTypeSelection.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            } else {
                                Log.d("UserLoginView", "Login Unsuccessful")
                                val error = result.exceptionOrNull()?.message ?: "Login failed, Invalid User ID or Password"
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mahroon
                    )
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Monospace,
                            color = white
                        )
                    )
                }

                TextButton(
                    onClick = {
                        // Handle user registration logic here
                        nav.navigate(Screen.UserSignup.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Don't have an account? Register Now",
                        style = TextStyle(
                            color = mahroon,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserPagePreview() {
    UserLoginScreen(nav = rememberNavController(), viewModel = viewModel())
}


package com.example.cinemaxApp.feature.user.auth.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserSignupViewModel
import kotlinx.coroutines.launch

@Composable
fun UserSignupScreen(nav: NavHostController, viewModel: UserSignupViewModel) {
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val sic = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF710C0C), Color(0xFF1A1919))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📝 New User Registration",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = sic.value,
            onValueChange = { sic.value = it },
            label = { Text("SIC") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email ID") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    val result = viewModel.signUp(email.value, password.value, name.value, sic.value)
                    if (result.isSuccess) {
                        Log.d("UserSignupView", "Signup Sucess")
                        Toast.makeText(context, "Account created for ${name.value}", Toast.LENGTH_SHORT).show()
                        nav.navigate(Screen.UserDashboard.route) {
                            popUpTo(Screen.UserTypeSelection.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else {
                        Log.d("UserSignupView", "Signup Failed")
                        val error = result.exceptionOrNull()?.message ?: "Signup failed"
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C))
        ) {
            Text("Register", color = Color.White)
        }

        TextButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("← Back to Login", color = Color.LightGray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewUserPreview() {
    //UserSignupScreen(nav = rememberNavController())
    UserSignupScreen(nav = rememberNavController(), viewModel = viewModel())
}
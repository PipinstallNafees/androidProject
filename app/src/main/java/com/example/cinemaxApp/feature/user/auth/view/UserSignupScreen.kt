package com.example.cinemaxApp.feature.user.auth.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    val carbonBlack = Color(0xFF1A1919)
    val mahroon = Color(0xFF710C0C)
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val sic = remember { mutableStateOf("") }
    val sicPattern = Regex("^[0-9]{2}[BM][A-Z]{2}[A-Z][0-9]{2}$")
    val email = remember { mutableStateOf("") }
    val domain = "@silicon.ac.in"
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF710C0C), Color(0xFF1A1919))
                )
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
//        Text(
//            text = "📝 New User Registration",
//            style = MaterialTheme.typography.headlineMedium.copy(
//                color = Color.White,
//                fontFamily = FontFamily.Monospace,
//                fontWeight = FontWeight.Bold
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//        )

        Box(
            modifier = Modifier
                .padding(12.dp)
                .shadow(10.dp, RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp))
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
                    text = "User Sign Up",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = carbonBlack
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Divider(thickness = 1.dp, color = mahroon)

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { newValue ->
                        name.value = capitalizeWords(newValue)
                        // Can I use it here rather than newValue (parameter of lambda fun)
                        // or both are same and if name is not mention it can be used by calling 'it'
                    },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = sic.value,
                    onValueChange = { newValue ->
                        val formatted = newValue.uppercase()
                        if (formatted.length <= 8) { // limit length
                            sic.value = formatted
                        }
//                        sic.value = it
                    },
                    label = { Text("SIC") },
                    singleLine = true,
                    isError = sic.value.isNotEmpty() && !sicPattern.matches(sic.value),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { newValue ->
                        // Block "@" and spaces
                        if (!newValue.contains("@") && !newValue.contains(" ")) {
                            email.value = newValue
                        }
                    },
                    label = { Text("Email ID") },
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

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { newValue ->
                        if (!newValue.contains(" ")) {
                            password.value = newValue
                        }
//                        password.value = it },
                    },
                    label = { Text("Password") },
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

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val result = viewModel.signUp(email.value + domain, password.value, name.value, sic.value)
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
    }
}

fun capitalizeWords(input: String): String {
    return input.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}


@Preview(showBackground = true)
@Composable
fun NewUserPreview() {
    //UserSignupScreen(nav = rememberNavController())
    UserSignupScreen(nav = rememberNavController(), viewModel = viewModel())
}
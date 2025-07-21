package com.example.cinemaxApp.feature.user.auth.view


// 🔽 Compose & AndroidX Imports
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun UserPage(navController: NavController) {
    val carbonBlack = Color(0xFF1A1919)
    val mahroon = Color(0xFF710C0C)
    val white = Color.White
    val context = LocalContext.current
    val userId = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
            )
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎬 Welcome to Cinemax User",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = mahroon,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        )

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
                    text = "👋 Hello User",
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

                Text(
                    text = "Enter your Email ID",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = carbonBlack
                    )
                )

                OutlinedTextField(
                    value = userId.value,
                    onValueChange = { userId.value = it },
                    label = {
                        Text("email ID", fontFamily = FontFamily.Monospace)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Text(
                    text = "Enter your Password",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        color = carbonBlack
                    )
                )
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password", fontFamily = FontFamily.Monospace) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = mahroon,
                        unfocusedBorderColor = Color.Gray
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {
                        // Handle login logic here
                        if (userId.value=="cinemax" && password.value=="cinemax") {
                            // Navigate to admin dashboard or perform login action
                            navController.navigate("dashboard")
                        } else {
                            Toast.makeText(context, "Invalid Admin ID or Password", Toast.LENGTH_SHORT).show()
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
                        // Navigate to Forgot Password screen
                        navController.navigate("forgotPassword")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                TextButton(
                    onClick = {
                        // Handle user registration logic here
                        navController.navigate("Register")},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "New Register ?",
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

@Composable
fun RegistrationScreen(navController: NavController) {
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
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
                Toast.makeText(context, "Account created for ${username.value}", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C))
        ) {
            Text("Register", color = Color.White)
        }

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("← Back to Login", color = Color.LightGray)
        }
    }
}

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF710C0C))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔐 Forgot Password",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter registered Email ID") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        Button(
            onClick = {
                Toast.makeText(context, "Recovery link sent to ${email.value}", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF710C0C))
        ) {
            Text("Send Recovery Link", color = Color.White)
        }

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("← Back to Login", color = Color.LightGray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewUserPreview() {
    RegistrationScreen(navController = rememberNavController())
}
@Preview(showBackground = true)
@Composable
fun UserPagePreview() {
    UserPage(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun ForgetpasswordPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}
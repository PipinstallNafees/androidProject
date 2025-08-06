
package com.example.cinemaxApp.feature.user.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.R
import com.example.cinemaxApp.core.navigation.Screen
import com.example.cinemaxApp.feature.user.dashboard.viewmodel.UserDashboardViewModel

@Composable
fun UserDashboardScreen(nav: NavHostController, viewModel: UserDashboardViewModel) {
    var userName by remember { mutableStateOf("") }

    val cinemaQuotes = listOf(
        // Quotes about cinema
        "Cinema is a matter of what's in the frame and what's out. — Martin Scorsese",
        "Film is truth 24 times a second. — Jean-Luc Godard",
        "Cinema can fill in the empty spaces of your life and your loneliness. — Pedro Almodóvar",
        "Movies touch our hearts and awaken our vision. — Martin Scorsese",
        "The power of cinema is that it gives you empathy. — Roger Ebert",
        "Cinema is the most beautiful fraud in the world. — Jean-Luc Godard",
        "Movies are like an expensive form of therapy for me. — Tim Burton",
        "Cinema should make you forget you are sitting in a theater. — Roman Polanski",
        "A film is – or should be – more like music than like fiction. — Stanley Kubrick",
        "Cinema is an empathy machine. — Roger Ebert",

        // Popular film dialogues
        "I'm going to make him an offer he can't refuse. — The Godfather (1972)",
        "May the Force be with you. — Star Wars (1977)",
        "Why so serious? — The Dark Knight (2008)",
        "Just keep swimming. — Finding Nemo (2003)",
        "With great power comes great responsibility. — Spider-Man (2002)",
        "You can't handle the truth! — A Few Good Men (1992)",
        "I'll be back. — The Terminator (1984)",
        "Bade bade deshon mein aisi chhoti chhoti baatein hoti rehti hain, Senorita. — Dilwale Dulhania Le Jayenge (1995)",
        "Mogambo khush hua. — Mr. India (1987)",
        "Kitne aadmi the? — Sholay (1975)"
    )

    userName = viewModel.userName

    LaunchedEffect(Unit) {
        viewModel.getUserName()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
                )
            )
            .padding(bottom = 30.dp)
    ) {
        //  Header Image
        Spacer(modifier = Modifier.padding(15.dp) )

        // Welcome Texts
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
            Spacer(modifier = Modifier.padding(top =16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome to",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    ),
                    color = Color.White
                )
                IconButton(
                    onClick = { nav.navigate(Screen.UserProfile.route) }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cinemax App",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                ),
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = userName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.padding(40.dp) )

        Image(
//            painter = painterResource(id = R.drawable.logo),
            painter = painterResource(id = R.drawable.cinemax_logo_final),
            contentDescription = "Cinemax Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        )
        Spacer(modifier = Modifier.height(30.dp))
        val randomQuote = remember { cinemaQuotes.random() }

        QuoteDisplay(quote = randomQuote)
        // Center the dashboard cards vertically
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .padding(16.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DashboardCard("Booking", R.drawable.book, Modifier.weight(1f),nav, Screen.BookMovie.route)
                            DashboardCard("E-Ticket", R.drawable.ticket, Modifier.weight(1f),nav, Screen.Ticket.createRoute(false))
                            DashboardCard("AboutUs", R.drawable.aboutus, Modifier.weight(1f),nav,Screen.AboutUS.route)
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DashboardCard("Instagram", R.drawable.inst, Modifier.weight(1f),nav, Screen.SocialHandle.route)
                            DashboardCard("Rules", R.drawable.rule, Modifier.weight(1f),nav,Screen.Rules.route)
                            DashboardCard("Developer", R.drawable.developer, Modifier.weight(1f),nav, Screen.Developer.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, imageId: Int, modifier: Modifier = Modifier,nav: NavHostController, screen: String = Screen.UserProfile.route) {
    Card(
        onClick = {
            nav.navigate(screen)
                  },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = title,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun QuoteDisplay(quote: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\"$quote\"",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    UserDashboardScreen(nav = rememberNavController(), viewModel = viewModel())
}
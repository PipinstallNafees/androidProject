package com.example.cinemaxApp.feature.user.developers

import android.content.Intent
import android.net.Uri
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.androidproject.R

@Composable
fun DevelopersScreen(nav: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF8D2D2D))
                )
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // 🔙 Back Button + Centered Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Meet the Developers",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp)) // balances the back button space
        }

        // 👤 Developer Cards
        DeveloperCard(
            name = "Nafees Ur Rahman",
            role = "Android Developer",
            imageRes = R.drawable.nafees,
            github = "https://github.com/PipinstallNafees",
            linkedin = "https://www.linkedin.com/in/nafees-ur-rahman-khan-3a89782a6",
            twitter = "https://www.instagram.com/naaafeees_khaaan_?igsh=MWJnMHVoeXJrenp0NQ==",
            context = context
        )

        DeveloperCard(
            name = "Mritunjay Sahoo",
            role = "Android Developer & Backend",
            imageRes = R.drawable.mritunjay,
            github = "https://github.com/BigLavaStone",
            linkedin = "https://www.linkedin.com/in/mrityunjoy-sahoo/",
            twitter = "https://www.instagram.com/__mrityunjoy_sahoo__?igsh=M21rbHdrbTRwa2h2",
            context = context
        )

        DeveloperCard(
            name = "Sonali Bera",
            role = "Android Developer",
            imageRes = R.drawable.sonali,
            github = "https://github.com/SONALI619",
            linkedin = "https://www.linkedin.com/in/sonali-bera-389485284?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app",
            twitter = "https://www.instagram.com/sonali_bera.78?igsh=bHQ1dnlzeGhjYWp6",
            context = context
        )
    }
}

@Composable
fun DeveloperCard(
    name: String,
    role: String,
    imageRes: Int,
    github: String?,
    linkedin: String?,
    twitter: String?,
    context: Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .height(IntrinsicSize.Min)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$name's photo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = role,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    github?.let {
                        Icon(
                            painter = painterResource(id = R.drawable.github),
                            contentDescription = "GitHub",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1B1B1B))
                                .clickable {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                                }
                                .padding(6.dp)
                        )
                    }
                    linkedin?.let {
                        Icon(
                            painter = painterResource(id = R.drawable.linkedin),
                            contentDescription = "LinkedIn",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1B1B1B))
                                .clickable {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                                }
                                .padding(6.dp)
                        )
                    }
                    twitter?.let {
                        Icon(
                            painter = painterResource(id = R.drawable.inst),
                            contentDescription = "Twitter",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .background(Color(0xFF1B1B1B))
                                .clickable {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                                }
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    }
}

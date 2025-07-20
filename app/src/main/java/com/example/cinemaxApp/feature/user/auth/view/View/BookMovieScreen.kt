import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidproject.R
import com.example.cinemaxApp.feature.user.auth.view.ViewModel.UserBookingViewModel

@Composable
fun BookMovieScreen(nav: NavController, viewModel: UserBookingViewModel) {
    val movie = viewModel.selectedMovie ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🎟 Movie Ticket Booking", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.bloodlines),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("🎬 Movie: ${movie.title}")
                Text("📅 Date: ${movie.date}")
                Text("⏰ Time: ${movie.time}")
                Text("🪑 Seats Left: ${movie.totalSeats}")
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { nav.navigate("attendeeForm") },
                    enabled = movie.totalSeats > 0
                ) {
                    Text("Book Ticket")
                }
            }
        }
    }
}


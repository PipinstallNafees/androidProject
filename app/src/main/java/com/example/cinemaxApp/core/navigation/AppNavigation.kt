package com.example.cinemaxApp.core.navigation


import com.example.cinemaxApp.feature.user.movieBooking.view.BookMovieScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemaxApp.core.firebase.AuthService
import com.example.cinemaxApp.core.firebase.FirestoreService
import com.example.cinemaxApp.feature.admin.dashboard.view.AdminDashboardScreen
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModel
import com.example.cinemaxApp.feature.admin.addMovie.view.AllocateSeatScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.CreateMovieScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.EditMovieScreen
import com.example.cinemaxApp.feature.admin.addMovie.view.MovieListScreen
import com.example.cinemaxApp.feature.admin.addMovie.viewmodel.MovieAdminViewModelFactory
import com.example.cinemaxApp.feature.user.auth.view.UserTypeSelectionScreen
import com.example.cinemaxApp.feature.admin.auth.view.AdminLoginScreen
import com.example.cinemaxApp.feature.admin.auth.viewmodel.AdminLoginViewModel
import com.example.cinemaxApp.feature.admin.auth.viewmodel.AdminLoginViewModelFactory
import com.example.cinemaxApp.feature.admin.dashboard.viewmodel.AdminDashboardViewModel
import com.example.cinemaxApp.feature.admin.dashboard.viewmodel.AdminDashboardViewModelFactory
import com.example.cinemaxApp.feature.admin.profile.view.AdminProfileScreen
import com.example.cinemaxApp.feature.admin.profile.viewmodel.AdminProfileViewModel
import com.example.cinemaxApp.feature.admin.profile.viewmodel.AdminProfileViewModelFactory
import com.example.cinemaxApp.feature.admin.verifyTicket.view.VerifyTicketScreen
import com.example.cinemaxApp.feature.user.About.AboutView
import com.example.cinemaxApp.feature.user.auth.view.UserSignupScreen
import com.example.cinemaxApp.feature.user.auth.view.UserLoginScreen
import com.example.cinemaxApp.feature.user.movieBooking.view.AddAttendeeScreen
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModel
import com.example.cinemaxApp.feature.user.movieBooking.viewmodel.UserBookingViewModelFactory
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserLoginViewModel
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserLoginViewModelFactory
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserSignupViewModel
import com.example.cinemaxApp.feature.user.auth.viewmodel.UserSignupViewModelFactory
import com.example.cinemaxApp.feature.user.profile.view.UserProfileScreen
import com.example.cinemaxApp.feature.user.profile.viewmodel.UserProfileViewModel
import com.example.cinemaxApp.feature.user.profile.viewmodel.UserProfileViewModelFactory
import com.example.cinemaxApp.feature.user.social.view.Instagram
import com.example.cinemaxApp.feature.user.dashboard.view.UserDashboardScreen
import com.example.cinemaxApp.feature.user.dashboard.viewmodel.UserDashboardViewModel
import com.example.cinemaxApp.feature.user.dashboard.viewmodel.UserDashboardViewModelFactory
import com.example.cinemaxApp.feature.user.movieBooking.view.SeatBookingScreen
import com.example.cinemaxApp.feature.user.ticket.view.TicketScreen
import com.example.cinemaxApp.feature.user.ticket.viewmodel.TicketViewModel
import com.example.cinemaxApp.feature.user.ticket.viewmodel.TicketViewModelFactory

@Composable
fun AppNavigation(navHostController: NavHostController, startScreen: String, authService: AuthService, firestoreService: FirestoreService) {
    // ViewModel object creation -- Needed for passing in composable views
    val adminDashboardViewModel: AdminDashboardViewModel = viewModel(
        factory = AdminDashboardViewModelFactory(authService, firestoreService)
    )
    val movieAdminViewModel: MovieAdminViewModel = viewModel(
        factory = MovieAdminViewModelFactory(firestoreService)
    )
    val adminLoginViewModel: AdminLoginViewModel = viewModel(
        factory = AdminLoginViewModelFactory(authService, firestoreService)
    )
    val adminProfileViewModel: AdminProfileViewModel = viewModel(
        factory = AdminProfileViewModelFactory(authService, firestoreService)
    )
    val userDashboardViewModel: UserDashboardViewModel = viewModel(
        factory = UserDashboardViewModelFactory(authService, firestoreService)
    )
    val userBookingViewModel: UserBookingViewModel = viewModel(
        factory = UserBookingViewModelFactory(authService, firestoreService)
    )
    val ticketViewModel: TicketViewModel = viewModel(
        factory = TicketViewModelFactory(authService, firestoreService)
    )
    val userLoginViewModel: UserLoginViewModel = viewModel(
        factory = UserLoginViewModelFactory(authService, firestoreService)
    )
    val userSignupViewModel: UserSignupViewModel = viewModel(
        factory = UserSignupViewModelFactory(authService, firestoreService)
    )
    val userProfileViewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(authService, firestoreService)
    )


    // Nav Graph
    // For passing screen name (String) in composable() use Screen Class
    // All the Screen names are defined in ./Screen.kt
    NavHost(navHostController, startDestination = startScreen) {
        // Splash Screen -- Currently handled in MainActivity.kt
//        composable(Screen.Splash.route) {
//            SplashScreen()
//        }
        composable(Screen.UserTypeSelection.route) {
            UserTypeSelectionScreen(navHostController)
        }

        // Admin
        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(navHostController, adminLoginViewModel)
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(navHostController, adminDashboardViewModel)
        }
        composable(Screen.AdminProfile.route) {
            AdminProfileScreen(navHostController, adminProfileViewModel)
        }
        composable (Screen.AdminMovieList.route){
            MovieListScreen(navHostController, movieAdminViewModel)
        }
        composable(Screen.CreateMovie.route) {
            CreateMovieScreen(navHostController, movieAdminViewModel)
        }
        composable(
            route = Screen.EditMovie.route,
            arguments = listOf(navArgument("MovieId") {type = NavType.StringType})
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("MovieId")
            EditMovieScreen(navHostController, movieAdminViewModel, movieId ?: "")
        }
        composable(
            route = Screen.AllocateSeat.route,
            arguments = listOf(navArgument("MovieId") {type = NavType.StringType})
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("MovieId")
            AllocateSeatScreen(navHostController, movieAdminViewModel, movieId ?: "")
        }
        composable(Screen.VerifyTicket.route) {
            VerifyTicketScreen(navHostController)
        }
        composable(Screen.AboutUS.route) {
            AboutView(navHostController)
        }


        // User
        composable(Screen.UserSignup.route) {
            UserSignupScreen(navHostController, userSignupViewModel)
        }
        composable(Screen.UserLogin.route) {
            UserLoginScreen(navHostController, userLoginViewModel)
        }
        composable(Screen.UserDashboard.route){
            UserDashboardScreen(navHostController, userDashboardViewModel)
        }
        composable(Screen.UserProfile.route) {
            UserProfileScreen(navHostController, userProfileViewModel)
        }
        composable (Screen.BookMovie.route){
            BookMovieScreen(navHostController, userBookingViewModel)
        }
        composable (Screen.AddAttendee.route){
            AddAttendeeScreen(navHostController, userBookingViewModel)
        }
        composable (Screen.SeatBooking.route){
            SeatBookingScreen(navHostController, userBookingViewModel)
        }
        composable (
            route = Screen.Ticket.route,
            arguments = listOf(navArgument("isNavFromSeatBooking") {type = NavType.BoolType})
        ){ backStackEntry ->
            val isNavFromSeatBooking = backStackEntry.arguments?.getBoolean("isNavFromSeatBooking")
            TicketScreen(navHostController, userBookingViewModel, ticketViewModel, isNavFromSeatBooking!!)
        }
        composable (Screen.SocialHandle.route){
            Instagram(navHostController)
        }
        composable(Screen.Rules.route) {
            com.example.cinemaxApp.feature.user.Guidelines.RulesView(navHostController)
        }
    }
}


package com.example.cinemaxApp.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("Splash")
    object UserTypeSelection : Screen("UserTypeSelection")

    // Admin
    object AdminLogin : Screen("AdminLogin")
    object AdminDashboard : Screen("AdminDashboard")
    object AdminProfile : Screen("AdminProfile")
    object AdminMovieList : Screen("AdminMovieList")
    object CreateMovie : Screen("CreateMovie")
    object EditMovie : Screen("EditMovie/{MovieId}") {
        fun createRoute(movieId: String) = "EditMovie/$movieId"
    }
    object AllocateSeat : Screen("AllocateSeat/{MovieId}") {
        fun createRoute(movieId: String) = "AllocateSeat/$movieId"
    }
    object VerifyTicket : Screen("VerifyTicket")

    // User
    object UserSignup : Screen("UserSignup")
    object UserLogin : Screen("UserLogin")
    object UserDashboard : Screen("UserDashboard")
    object UserProfile : Screen("UserProfile")
    object BookMovie : Screen("BookMovie")
    object AddAttendee : Screen("AddAttendee")
    object SocialHandle : Screen("SocialHandle")
    object AboutUS : Screen("AboutUs")
    object Rules : Screen("Rules")
    // TODO: Find About '/' using for sub category, wouldn't it affect the {data}
}
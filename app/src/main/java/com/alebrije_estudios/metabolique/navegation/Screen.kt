package com.alebrije_estudios.metabolique.navegation

sealed class Screen(val route:String) {
    object Login : Screen("/login")
    object Dashboard : Screen("/dashboard")
    object CreateUser: Screen("/createUser")
    object RecoverUser: Screen("/recoverUser")
    object MyProfile: Screen("/myProfile")

}
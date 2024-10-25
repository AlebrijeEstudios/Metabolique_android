package com.alebrije_estudios.metabolique.navegation

sealed class Screen(val route:String) {
    object Login : Screen("/login")
    object CreateUser: Screen("/createUser")
    object RecoverUser: Screen("/recoverUser")
    object MyProfile: Screen("/myProfile/{email}/{name}")
    object Dashboard : Screen("/dashboard")
    object Feed: Screen("/feed")
    object Exercises: Screen("/exercises")
    object Medication: Screen("/medication")
    object Habits: Screen("/habits")
    object CapturedFood: Screen("/capturedFood")
    object MonthlyMonitoringExercises: Screen("/monthlyMonitoringExercises")
    object MonthlyMonitoringHabits: Screen("/monthlyMonitoringHabits")
    object MonthlyMonitoringMedication: Screen("/monthlyMonitoringMedication")
    object MonthlyMonitoringFood: Screen("/monthlyMonitoringFoods")
}
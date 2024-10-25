package com.alebrije_estudios.metabolique.dashboard.ui

data class DashboardModel(
    val calories: Int,
    val maxCalories: Int = 2000,
    val exerciseTime: Int,
    val maxTime: Int = 1 * 60,
    val sleepTime: Int,
    val maxSleepTime: Int = 8 * 60,
    val countMedication: Int,
    val maxCountMedication: Int = 3
)
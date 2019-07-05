package by.pavluchkov.fuel_balance.utilites

import by.pavluchkov.fuel_balance.enums.TimeOfYear

data class MainData(
    val previous: Int,
    val current: Int,
    val frequentTechnological: Int,
    val trassa: Int,
    val timeOfYear: TimeOfYear
)
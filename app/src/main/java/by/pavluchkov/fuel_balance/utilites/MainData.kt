package by.pavluchkov.fuel_balance.utilites

import by.pavluchkov.fuel_balance.enums.TimeOfYear

data class MainData(
    var previous: Int,
    var current: Int,
    var frequentTechnological: Int,
    var timeOfYear: TimeOfYear
)
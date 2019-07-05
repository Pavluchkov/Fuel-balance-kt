package by.pavluchkov.fuel_balance.interfaces

import by.pavluchkov.fuel_balance.enums.TimeOfYear

interface MainScreenView {
    fun showResult(result: Double) {}
    fun getUserData()
    fun showMessage(resId: Int)
    fun getTimeOfYear(): TimeOfYear {
        return TimeOfYear.SUMMER
    }
}
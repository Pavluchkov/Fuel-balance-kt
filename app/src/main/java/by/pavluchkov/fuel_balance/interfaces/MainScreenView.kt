package by.pavluchkov.fuel_balance.interfaces

import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.utilites.MainData

interface MainScreenView {
    fun getUserData(): MainData
    fun showMessage(resId: Int)
    fun setResult(kmPassed: Int, result: Float)
    fun setLoadUserData(previous: Int, timeOfYear: TimeOfYear)
}
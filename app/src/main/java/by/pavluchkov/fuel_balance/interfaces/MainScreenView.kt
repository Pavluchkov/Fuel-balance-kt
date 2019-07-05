package by.pavluchkov.fuel_balance.interfaces

import android.content.SharedPreferences
import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.utilites.MainData

interface MainScreenView {
    fun getUserData(): MainData
    fun getSharedPref(): SharedPreferences
    fun showMessage(resId: Int)
    fun setResult(kmPassed: Int, result: Double)
    fun setLoadUserData(previous: Int, timeOfYear: TimeOfYear)
}
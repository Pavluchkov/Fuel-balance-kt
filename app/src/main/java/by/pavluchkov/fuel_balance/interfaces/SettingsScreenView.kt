package by.pavluchkov.fuel_balance.interfaces

import android.content.SharedPreferences
import by.pavluchkov.fuel_balance.utilites.SettingsData

interface SettingsScreenView {
    fun getUserData(): SettingsData
    fun showMessage(resId: Int)
    fun getSharedPref(): SharedPreferences
    fun setLoadData(data: SettingsData)
}
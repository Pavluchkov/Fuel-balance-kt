package by.pavluchkov.fuel_balance.interfaces

import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.SettingsData

interface Imodel {
    fun saveData(data: MainData)
    fun saveData(data: SettingsData)
    fun loadMainData(): MainData
    fun loadSettingsData(): SettingsData
}
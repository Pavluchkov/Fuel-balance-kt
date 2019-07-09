package by.pavluchkov.fuel_balance.interfaces

import by.pavluchkov.fuel_balance.utilites.SettingsData

interface SettingsScreenView {
    fun getUserData(): SettingsData
    fun setLoadData(data: SettingsData)
}
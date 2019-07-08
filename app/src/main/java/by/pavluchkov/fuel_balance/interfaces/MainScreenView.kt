package by.pavluchkov.fuel_balance.interfaces

import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.Result

interface MainScreenView {
    fun getUserData(): MainData
    fun showMessage(resId: Int)
    fun setResult(result: Result)
    fun setLoadUserData(data: MainData)
}
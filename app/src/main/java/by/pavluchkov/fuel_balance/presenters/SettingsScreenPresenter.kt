package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.interfaces.SettingsScreenView
import by.pavluchkov.fuel_balance.model.ModelFactory
import by.pavluchkov.fuel_balance.utilites.SettingsData

class SettingsScreenPresenter {
    private var mSettingsView: SettingsScreenView? = null
    private val mModel = ModelFactory.getModel()

    fun attachView(view: SettingsScreenView) {
        mSettingsView = view
    }

    fun detachView() {
        mSettingsView = null
    }

    fun saveUserData(userData: SettingsData) {
        mModel.saveData(userData)
    }

    fun loadUserData(): SettingsData {
        return mModel.loadSettingsData()
    }

}
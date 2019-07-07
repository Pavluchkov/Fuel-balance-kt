package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.interfaces.SettingsScreenView
import by.pavluchkov.fuel_balance.model.ModelFactory

class SettingsScreenPresenter {
    private var mSettingsView: SettingsScreenView? = null
    private val mModel = ModelFactory.getModel()

    fun attachView(view: SettingsScreenView) {
        mSettingsView = view
    }

    fun detachView() {
        mSettingsView = null
    }

    fun saveUserData() {
        val userData = mSettingsView?.getUserData() ?: return
        mModel.saveData(userData)
    }

    fun loadUserData() {
        mSettingsView?.setLoadData(mModel.loadSettingsData())
    }

}
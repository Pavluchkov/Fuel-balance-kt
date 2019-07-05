package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.App
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.interfaces.SettingsScreenView
import by.pavluchkov.fuel_balance.utilites.SettingsData

class SettingsScreenPresenter {
    private var mSettingsView: SettingsScreenView? = null

    fun attachView(view: SettingsScreenView) {
        mSettingsView = view
    }

    fun detachView() {
        mSettingsView = null
    }

    fun saveUserData() {
        val userData = mSettingsView?.getUserData() ?: return

        val summerNorma = userData.summerNorma
        val winterNorma = userData.winterNorma
        val frequencyTechnology = userData.frequentTechnological
        val trassa = userData.trassa

        val sharedPref = mSettingsView?.getSharedPref() ?: return
        with(sharedPref.edit()) {
            putFloat(getStringFromRes(R.string.TAG_fuel_norma_summer), summerNorma)
            putFloat(getStringFromRes(R.string.TAG_fuel_norma_winter), winterNorma)
            putInt(getStringFromRes(R.string.TAG_frequent_technological), frequencyTechnology)
            putInt(getStringFromRes(R.string.TAG_trassa), trassa)
            apply()
        }
    }

    fun loadUserData() {
        val sharedPref = mSettingsView?.getSharedPref() ?: return

        val summerNorma = sharedPref.getFloat(getStringFromRes(R.string.TAG_fuel_norma_summer), 0f)
        val winterNorma = sharedPref.getFloat(getStringFromRes(R.string.TAG_fuel_norma_winter), 0f)
        val frequencyTechnology = sharedPref.getInt(getStringFromRes(R.string.TAG_frequent_technological), 0)
        val trassa = sharedPref.getInt(getStringFromRes(R.string.TAG_trassa), 0)

        mSettingsView?.setLoadData(SettingsData(summerNorma, winterNorma, frequencyTechnology, trassa))

    }

    private fun getStringFromRes(resId: Int): String? {
        return App.getRes()?.getString(resId)
    }
}
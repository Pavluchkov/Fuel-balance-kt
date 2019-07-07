package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.MainScreenView
import by.pavluchkov.fuel_balance.model.ModelFactory
import kotlin.math.roundToInt

class MainScreenPresenter {
    private var mMainScreenView: MainScreenView? = null
    private val mModel = ModelFactory.getModel()

    fun attachView(view: MainScreenView) {
        mMainScreenView = view
    }

    fun detachView() {
        mMainScreenView = null
    }

    fun saveUserData() {
        val userData = mMainScreenView?.getUserData() ?: return
        mModel.saveData(userData)
    }

    fun loadPreviousData() {
        val mainData = mModel.loadMainData()
        mMainScreenView?.setLoadUserData(mainData.current, mainData.timeOfYear)
    }

    fun getResult() {
        val userData = mMainScreenView?.getUserData() ?: return
        val settingsData = mModel.loadSettingsData()

        if (userData.current < userData.previous) {
            mMainScreenView?.setResult(0, 0f)
            return
        }

        val kmPassed = userData.current - userData.previous

        val currentNorma = when (userData.timeOfYear) {
            TimeOfYear.SUMMER -> settingsData.summerNorma
            else -> settingsData.winterNorma
        }

        val freqTechNorma = currentNorma + (settingsData.frequentTechnological * currentNorma / 100)
        val trassaNorma = currentNorma - (settingsData.trassa * currentNorma / 100)

        val spentFreqTech = userData.frequentTechnological * freqTechNorma / 100
        val spentTrassa = userData.trassa * trassaNorma / 100

        val result =
            ((kmPassed - userData.frequentTechnological - userData.trassa) * currentNorma / 100) + spentFreqTech + spentTrassa

        mMainScreenView?.setResult(kmPassed, (result * 1000).roundToInt() / 1000.toFloat())

    }

}
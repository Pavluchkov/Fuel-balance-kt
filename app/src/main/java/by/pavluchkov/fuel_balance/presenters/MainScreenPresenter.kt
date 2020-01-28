package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.MainScreenView
import by.pavluchkov.fuel_balance.model.ModelFactory
import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.Result
import by.pavluchkov.fuel_balance.utilites.SettingsData
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

    fun saveUserData(mainScreenData: MainData) {

        if (mainScreenData.current < mainScreenData.previous) mainScreenData.current =
            mainScreenData.previous

        mModel.saveData(mainScreenData)
    }

    fun loadMainScreenData(): MainData {
        return mModel.loadMainData()
    }

    fun loadSettingsData(): SettingsData {
        return mModel.loadSettingsData()
    }

    fun checkValue(mainScreenData: MainData): Boolean {
        val kmPassed = mainScreenData.current - mainScreenData.previous

        if ((mainScreenData.frequentTechnological > kmPassed) && (kmPassed > 0)) {
            return false
        }

        return true
    }

    fun getResult(mainScreenData: MainData, settingsData: SettingsData): Result {

        val kmPassed = mainScreenData.current - mainScreenData.previous

        if (mainScreenData.current < mainScreenData.previous) {
            return Result(0, 0f)
        }

        val currentNorma = when (mainScreenData.timeOfYear) {
            TimeOfYear.SUMMER -> settingsData.summerNorma
            else -> settingsData.winterNorma
        }

        val freqTechNorma = currentNorma + (settingsData.frequentTechnological * currentNorma / 100)
        val trassaNorma = currentNorma - (settingsData.trassa * currentNorma / 100)

        val spentFreqTech = mainScreenData.frequentTechnological * freqTechNorma / 100
        //val spentTrassa = mainScreenData.trassa * trassaNorma / 100

        val result =
            ((kmPassed - mainScreenData.frequentTechnological) * trassaNorma / 100) + spentFreqTech

        val roundResult = (result * 1000).roundToInt() / 1000.toFloat()

        return Result(kmPassed, roundResult)

    }

}
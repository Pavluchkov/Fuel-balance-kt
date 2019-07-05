package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.App
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.MainScreenView

class MainScreenPresenter {
    private var mMainScreenView: MainScreenView? = null

    fun attachView(view: MainScreenView) {
        mMainScreenView = view
    }

    fun detachView() {
        mMainScreenView = null
    }

    fun saveUserData() {
        val userData = mMainScreenView?.getUserData() ?: return

        val previous = userData.current
        val timeOfYear = userData.timeOfYear

        val sharedPref = mMainScreenView?.getSharedPref() ?: return
        with(sharedPref.edit()) {
            putInt(getStringFromRes(R.string.TAG_previous_main), previous)
            putInt(getStringFromRes(R.string.TAG_time_of_year), timeOfYear.ordinal)
            apply()
        }
    }

    fun loadPreviousData() {
        val sharedPref = mMainScreenView?.getSharedPref() ?: return

//        val summerNorma = sharedPref.getFloat(getStringFromRes(R.string.TAG_fuel_norma_summer), 0f)
//        val winterNorma = sharedPref.getFloat(getStringFromRes(R.string.TAG_fuel_norma_winter), 0f)
//        val frequencyTechnology = sharedPref.getInt(getStringFromRes(R.string.TAG_frequent_technological), 0)
//        val trassa = sharedPref.getInt(getStringFromRes(R.string.TAG_trassa), 0)

        val previous = sharedPref.getInt(getStringFromRes(R.string.TAG_previous_main), 0)
        val timeOfYearOrdinal = sharedPref.getInt(getStringFromRes(R.string.TAG_time_of_year), 0)
        val timeOfYear = when (timeOfYearOrdinal) {
            TimeOfYear.SUMMER.ordinal -> TimeOfYear.SUMMER
            else -> TimeOfYear.WINTER
        }

        mMainScreenView?.setLoadUserData(previous, timeOfYear)
    }

    fun getResult() {

    }

    fun raschet() {
        val userData = mMainScreenView?.getUserData()
//        println(
//            """
//            previous: ${userData?.previous}
//            current: ${userData?.current}
//            freq: ${userData?.frequentTechnological}
//            trassa: ${userData?.trassa}
//            timeOfYear: ${userData?.timeOfYear}
//        """.trimIndent()
//        )
    }

    private fun getStringFromRes(resId: Int): String? {
        return App.getRes()?.getString(resId)
    }
}
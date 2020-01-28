package by.pavluchkov.fuel_balance.model

import android.content.Context
import by.pavluchkov.fuel_balance.App
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.Imodel
import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.SettingsData

class Model : Imodel {
    private val sharedPreferences = App.getInstance()?.getSharedPreferences(
        App.getRes()?.getString(R.string.TAG_app_preference_file), Context.MODE_PRIVATE
    )

    override fun saveData(data: MainData) {
        if (sharedPreferences == null) return

        with(sharedPreferences.edit()) {
            putInt(getStringFromRes(R.string.TAG_previous_main), data.previous)
            putInt(getStringFromRes(R.string.TAG_current_main), data.current)
            putInt(
                getStringFromRes(R.string.TAG_frequent_technological_main),
                data.frequentTechnological
            )
            putInt(getStringFromRes(R.string.TAG_time_of_year), data.timeOfYear.ordinal)
            apply()
        }
    }

    override fun saveData(data: SettingsData) {
        if (sharedPreferences == null) return

        with(sharedPreferences.edit()) {
            putFloat(getStringFromRes(R.string.TAG_fuel_norma_summer), data.summerNorma)
            putFloat(getStringFromRes(R.string.TAG_fuel_norma_winter), data.winterNorma)
            putInt(
                getStringFromRes(R.string.TAG_frequent_technological_settings),
                data.frequentTechnological
            )
            putInt(getStringFromRes(R.string.TAG_trassa_settings), data.trassa)
            apply()
        }
    }

    override fun loadMainData(): MainData {
        if (sharedPreferences == null) return MainData(0, 0, 0, TimeOfYear.SUMMER)

        val previous = sharedPreferences.getInt(getStringFromRes(R.string.TAG_previous_main), 0)
        val current = sharedPreferences.getInt(getStringFromRes(R.string.TAG_current_main), 0)
        val frequentTechnology =
            sharedPreferences.getInt(getStringFromRes(R.string.TAG_frequent_technological_main), 0)
        val timeOfYearOrdinal =
            sharedPreferences.getInt(getStringFromRes(R.string.TAG_time_of_year), 0)
        val timeOfYear = when (timeOfYearOrdinal) {
            TimeOfYear.SUMMER.ordinal -> TimeOfYear.SUMMER
            else -> TimeOfYear.WINTER
        }

        return MainData(previous, current, frequentTechnology, timeOfYear)
    }

    override fun loadSettingsData(): SettingsData {
        if (sharedPreferences == null) return SettingsData(0f, 0f, 0, 0)

        val summerNorma =
            sharedPreferences.getFloat(getStringFromRes(R.string.TAG_fuel_norma_summer), 0f)
        val winterNorma =
            sharedPreferences.getFloat(getStringFromRes(R.string.TAG_fuel_norma_winter), 0f)
        val frequencyTechnology =
            sharedPreferences.getInt(
                getStringFromRes(R.string.TAG_frequent_technological_settings),
                0
            )
        val trassa = sharedPreferences.getInt(getStringFromRes(R.string.TAG_trassa_settings), 0)

        return SettingsData(summerNorma, winterNorma, frequencyTechnology, trassa)
    }

    private fun getStringFromRes(resId: Int): String? {
        return App.getRes()?.getString(resId)
    }
}
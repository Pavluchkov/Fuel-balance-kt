package by.pavluchkov.fuel_balance.model

import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.Imodel
import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.SettingsData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ModelTest {
    lateinit var model: Imodel
    lateinit var mainScreenData: MainData
    lateinit var settingsData: SettingsData

    @Before
    fun setUp() {
        model = ModelFactory.getModel()

        mainScreenData = MainData(
            previous = 2410,
            current = 2450,
            frequentTechnological = 20,
            trassa = 17,
            timeOfYear = TimeOfYear.SUMMER
        )

        settingsData = SettingsData(14.6f, 15.9f, 10, 3)
    }

    @Test
    fun saveData_settings() {
        model.saveData(settingsData)
        val settingsDataLoaded = model.loadSettingsData()
        assertEquals(settingsData, settingsDataLoaded)
    }

    @Test
    fun saveData_mainScreen() {
        model.saveData(mainScreenData)
        val mainScreenDataLoaded = model.loadMainData()
        assertEquals(mainScreenData, mainScreenDataLoaded)
    }

    @Test
    fun loadMainData() {
    }

    @Test
    fun loadSettingsData() {
    }
}
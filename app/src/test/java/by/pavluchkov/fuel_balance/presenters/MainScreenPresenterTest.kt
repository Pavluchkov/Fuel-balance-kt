package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.SettingsData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainScreenPresenterTest {
    val presenter = MainScreenPresenter()
    lateinit var mainScreenData: MainData
    lateinit var settingsData: SettingsData

    @Before
    fun init() {
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
    fun checkValue() {
        assertEquals(true, presenter.checkValue(mainScreenData))
        mainScreenData.trassa = 25
        assertEquals(false, presenter.checkValue(mainScreenData))
        mainScreenData.trassa = 16
        mainScreenData.current = 2405
        assertEquals(true, presenter.checkValue(mainScreenData))
    }

    @Test
    fun getResult() {
        assertEquals(6.058f, presenter.getResult(mainScreenData, settingsData).result)
        mainScreenData.timeOfYear = TimeOfYear.WINTER
        assertEquals(6.597f, presenter.getResult(mainScreenData, settingsData).result)
        mainScreenData.frequentTechnological = 22
        assertEquals(6.629f, presenter.getResult(mainScreenData, settingsData).result)
        mainScreenData.timeOfYear = TimeOfYear.SUMMER
        assertEquals(6.087f, presenter.getResult(mainScreenData, settingsData).result)
        settingsData.frequentTechnological = 15
        assertEquals(6.247f, presenter.getResult(mainScreenData, settingsData).result)
        settingsData.trassa = 5
        assertEquals(6.198f, presenter.getResult(mainScreenData, settingsData).result)
    }
}
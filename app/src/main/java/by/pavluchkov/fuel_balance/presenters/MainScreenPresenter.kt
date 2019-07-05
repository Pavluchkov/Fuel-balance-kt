package by.pavluchkov.fuel_balance.presenters

import by.pavluchkov.fuel_balance.interfaces.MainScreenView

class MainScreenPresenter {
    private var mainScreenView: MainScreenView? = null

    fun attachView(view: MainScreenView) {
        mainScreenView = view
    }

    fun detachView() {
        mainScreenView = null
    }

    fun getResult() {

    }
}
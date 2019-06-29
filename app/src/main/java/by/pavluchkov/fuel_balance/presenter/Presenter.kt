package by.pavluchkov.fuel_balance.presenter

import by.pavluchkov.fuel_balance.interfaces.MyView

class Presenter(val view: MyView) {
    val myView: MyView = view

}
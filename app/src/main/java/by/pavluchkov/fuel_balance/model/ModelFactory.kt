package by.pavluchkov.fuel_balance.model

import by.pavluchkov.fuel_balance.interfaces.Imodel

class ModelFactory {
    companion object {
        fun getModel(): Imodel {
            return Model()
        }
    }
}
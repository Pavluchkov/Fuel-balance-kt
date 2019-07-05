package by.pavluchkov.fuel_balance

import android.app.Application
import android.content.res.Resources

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        mResources = resources
    }

    companion object {
        private var mInstance: App? = null
        private var mResources: Resources? = null

        fun getInstance(): App? {
            return mInstance
        }

        fun getRes(): Resources? {
            return mResources
        }
    }

}
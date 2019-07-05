package by.pavluchkov.fuel_balance.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.interfaces.SettingsScreenView
import by.pavluchkov.fuel_balance.presenters.SettingsScreenPresenter
import by.pavluchkov.fuel_balance.utilites.SettingsData
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), SettingsScreenView {
    private val mPresenter = SettingsScreenPresenter()

    override fun setLoadData(data: SettingsData) {
        editText_summer_norma_settings.setText(
            when (data.summerNorma) {
                0f -> ""
                else -> data.summerNorma.toString()
            }
        )
        editText_winter_norma_settings.setText(
            when (data.winterNorma) {
                0f -> ""
                else -> data.winterNorma.toString()
            }
        )
        editText_frequent_technological_settings.setText(data.frequentTechnological.toString())
        editText_trassa_settings.setText(data.trassa.toString())
    }

    override fun getSharedPref(): SharedPreferences {
        return getSharedPreferences(
            getString(R.string.TAG_app_preference_file_settings),
            Context.MODE_PRIVATE
        )
    }

    override fun getUserData(): SettingsData {
        val summerNorma = when (editText_summer_norma_settings.text.toString()) {
            "" -> 0f
            else -> editText_summer_norma_settings.text.toString().toFloat()
        }

        val winterNorma = when (editText_winter_norma_settings.text.toString()) {
            "" -> 0f
            else -> editText_winter_norma_settings.text.toString().toFloat()
        }

        val frequentTechnological = when (editText_frequent_technological_settings.text.toString()) {
            "" -> 0
            else -> editText_frequent_technological_settings.text.toString().toInt()
        }

        val trassa = when (editText_trassa_settings.text.toString()) {
            "" -> 0
            else -> editText_trassa_settings.text.toString().toInt()
        }

        return SettingsData(summerNorma, winterNorma, frequentTechnological, trassa)
    }

    override fun showMessage(resId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPresenter.attachView(this)
        mPresenter.loadUserData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStop() {
        mPresenter.saveUserData()
        mPresenter.detachView()
        super.onStop()
    }

}
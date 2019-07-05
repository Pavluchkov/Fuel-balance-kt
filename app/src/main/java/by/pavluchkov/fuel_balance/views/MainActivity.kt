package by.pavluchkov.fuel_balance.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import by.pavluchkov.fuel_balance.BuildConfig
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.MainScreenView
import by.pavluchkov.fuel_balance.presenters.MainScreenPresenter
import by.pavluchkov.fuel_balance.utilites.MainData
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainScreenView {

    private val mPresenter = MainScreenPresenter()

    override fun setLoadUserData(previous: Int, timeOfYear: TimeOfYear) {
        editText_previous_main.setText(
            when (previous) {
                0 -> ""
                else -> previous.toString()
            }
        )

        when (timeOfYear) {
            TimeOfYear.SUMMER -> radioButton_summer_main.setChecked(true)
            else -> radioButton_winter_main.setChecked(true)
        }
    }

    override fun setResult(kmPassed: Int, result: Double) {
        textView_passedResult_main.setText(kmPassed.toString())
        textView_spentResult_main.setText(result.toString())
    }

    override fun getSharedPref(): SharedPreferences {
        return getSharedPreferences(
            getString(R.string.TAG_app_preference_file),
            Context.MODE_PRIVATE
        )
    }

    override fun getUserData(): MainData {
        val previous = when (editText_previous_main.text.toString()) {
            "" -> 0
            else -> editText_previous_main.text.toString().toInt()
        }

        val current = when (editText_current_main.text.toString()) {
            "" -> 0
            else -> editText_current_main.text.toString().toInt()
        }

        val frequentTechnological = when (editText_frequent_technological_main.text.toString()) {
            "" -> 0
            else -> editText_frequent_technological_main.text.toString().toInt()
        }

        val trassa = when (editText_trassa_main.text.toString()) {
            "" -> 0
            else -> editText_trassa_main.text.toString().toInt()
        }

        val timeOfYear = when (radioButton_summer_main.isChecked) {
            true -> TimeOfYear.SUMMER
            else -> TimeOfYear.WINTER
        }

        return MainData(previous, current, frequentTechnological, trassa, timeOfYear)
    }

    override fun showMessage(resId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        textView_currentVersion_main.text = BuildConfig.VERSION_NAME

        mPresenter.attachView(this)
        mPresenter.loadPreviousData()

        editText_previous_main.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mPresenter.raschet()
            }

        })

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStop() {
        mPresenter.saveUserData()
        super.onStop()
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}

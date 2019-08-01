package by.pavluchkov.fuel_balance.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import by.pavluchkov.fuel_balance.BuildConfig
import by.pavluchkov.fuel_balance.R
import by.pavluchkov.fuel_balance.enums.TimeOfYear
import by.pavluchkov.fuel_balance.interfaces.MainScreenView
import by.pavluchkov.fuel_balance.presenters.MainScreenPresenter
import by.pavluchkov.fuel_balance.utilites.MainData
import by.pavluchkov.fuel_balance.utilites.Result
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainScreenView {

    private val mPresenter = MainScreenPresenter()

    override fun setLoadUserData(data: MainData) {
        editText_previous_main.setText(
            when (data.current.toString()) {
                "0" -> ""
                else -> data.current.toString()
            }
        )

        when (data.timeOfYear) {
            TimeOfYear.SUMMER -> radioButton_summer_main.isChecked = true
            else -> radioButton_winter_main.isChecked = true
        }
    }

    override fun setResult(result: Result) {
        textView_passedResult_main.text = result.kmPass.toString()
        textView_spentResult_main.text = result.result.toString()
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
        Snackbar.make(main_layout, getString(resId), Snackbar.LENGTH_LONG).show()
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
        setLoadUserData(mPresenter.loadMainScreenData())

        setEditTextListener(editText_previous_main)
        setEditTextListener(editText_current_main)
        setEditTextListener(editText_frequent_technological_main)
        setEditTextListener(editText_trassa_main)

        radioGroup_main.setOnCheckedChangeListener { _, _ ->
            setResult(
                mPresenter.getResult(
                    getUserData(),
                    mPresenter.loadSettingsData()
                )
            )
        }

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
//            R.id.nav_home -> {
//                // Handle the camera action
//            }
//            R.id.nav_gallery -> {
//
//            }
//            R.id.nav_slideshow -> {
//
//            }
            R.id.nav_tools -> {
                val settingIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingIntent)
            }
            R.id.nav_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage = "\n" + getString(R.string.string_share_app_message) + "\n\n"
                shareMessage =
                    shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.string_share_app_chooser_title)))

            }
            R.id.nav_send -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                //intent.setType("message/rfc822");
                intent.data = Uri.parse("mailto:" + getString(R.string.email_app_address))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_app_subject))
                startActivity(Intent.createChooser(intent, getString(R.string.email_app_chooserTitle)))

            }
            R.id.nav_rate -> {
                launchMarket()
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStop() {
        mPresenter.saveUserData(getUserData())
        super.onStop()
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun onStart() {
        Timer().schedule(600) {
            setResult(mPresenter.getResult(getUserData(), mPresenter.loadSettingsData()))
        }
        super.onStart()
    }

    private fun launchMarket() {
        val uri = Uri.parse("market://details?id=$packageName")
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)

        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.string_app_not_found_in_market), Toast.LENGTH_LONG).show()
        }
    }

    private fun setEditTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!mPresenter.checkValue(getUserData())) {
                    showMessage(R.string.string_message_1)

                    val editTextValue = editText.text.toString().dropLast(1)
                    editText.setText(editTextValue)
                    editText.setSelection(editTextValue.length)
                    editText.setBackgroundColor(Color.RED)
                    Timer().schedule(200) {
                        editText.setBackgroundColor(ContextCompat.getColor(editText.context, R.color.colorBackground))
                    }
                }
                setResult(mPresenter.getResult(getUserData(), mPresenter.loadSettingsData()))
            }

        })
    }
}

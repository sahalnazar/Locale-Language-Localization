package io.github.sahalnazar.locale

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ShareActionProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.github.sahalnazar.locale.Constants.ARABIC
import io.github.sahalnazar.locale.Constants.ENGLISH
import io.github.sahalnazar.locale.Constants.MALAYALAM
import io.github.sahalnazar.locale.Constants.SELECTED_LANGUAGE
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var selectedItem: String
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedItem = sharedPref.getLanguage().toString()

        val languages = listOf(ENGLISH, ARABIC, MALAYALAM)

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languages
        )

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = spinnerAdapter

        spinner.setSelection(sharedPref.getPosition())

        btnLanguage.setOnClickListener {
            when (selectedItem) {

                ENGLISH -> {
                    sharedPref.saveLanguage(ENGLISH)
                    restartActivity()
                }

                ARABIC -> {
                    sharedPref.saveLanguage(ARABIC)
                    restartActivity()
                }

                MALAYALAM -> {
                    sharedPref.saveLanguage(MALAYALAM)
                    restartActivity()
                }
            }
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                selectedItem = spinner.selectedItem.toString()
                sharedPref.savePosition(position)
                Snackbar.make(rootLayout, "Selected: $selectedItem", Snackbar.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun restartActivity() {
        finish()
        startActivity(intent)
    }

    override fun attachBaseContext(newBase: Context?) {
        sharedPref = SharedPref(newBase!!)
        super.attachBaseContext(BaseContextWrapper.wrap(newBase, sharedPref.getLanguage()!!))
    }

}
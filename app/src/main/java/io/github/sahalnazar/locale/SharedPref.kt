package io.github.sahalnazar.locale

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import io.github.sahalnazar.locale.Constants.ENGLISH
import io.github.sahalnazar.locale.Constants.SELECTED_LANGUAGE
import io.github.sahalnazar.locale.Constants.SELECTED_POSITION
import java.io.PipedOutputStream
import java.text.FieldPosition

class SharedPref(context: Context) {

    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun savePosition(position: Int){
        pref.edit{
            putInt(SELECTED_POSITION, position)
        }
    }

    fun saveLanguage(language: String) {
        pref.edit{
            putString(SELECTED_LANGUAGE, language)
        }
    }

    fun getLanguage() = pref.getString(SELECTED_LANGUAGE, ENGLISH)

    fun getPosition() = pref.getInt(SELECTED_POSITION, 0)

}
package io.github.sahalnazar.locale

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class BaseContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {

        @SuppressLint("ObsoleteSdkInt")
        fun wrap(context: Context, language: String): ContextWrapper {

            var context = context
            val config = context.resources.configuration
            val sysLocale: Locale?
            sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getSystemLocale(config)
            } else {
                getSystemLocaleLegacy(config)
            }

            if (language != "" && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config)
            } else {
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
            }
            return BaseContextWrapper(context)
        }

        private fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        private fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }

        private fun getSystemLocaleLegacy(config: Configuration): Locale = config.locale

        @RequiresApi(Build.VERSION_CODES.N)
        private fun getSystemLocale(config: Configuration): Locale = config.locales[0]

    }

}
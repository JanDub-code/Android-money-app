package cz.mendelu.todost11.utils
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleManager {

    fun updateLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        context.createConfigurationContext(configuration)
        context.resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
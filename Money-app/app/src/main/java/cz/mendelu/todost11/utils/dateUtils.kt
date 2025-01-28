package cz.mendelu.todost11.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class dateUtils {
    companion object {

        private val DATE_FORMAT_CS = "dd. MM. yyyy"
        private val DATE_FORMAT_EN = "yyyy/MM/dd"

        fun getDateString(date: Long): String {
            var calendar = java.util.Calendar.getInstance()
            calendar.timeInMillis = date

            val simpleDateFormat: SimpleDateFormat

            if(languageUtils.isLanguageCloseToCzech()) {
                simpleDateFormat = SimpleDateFormat(DATE_FORMAT_CS, Locale.GERMAN)

            } else {
                simpleDateFormat = SimpleDateFormat(DATE_FORMAT_EN, Locale.ENGLISH)
            }

            return simpleDateFormat.format(calendar.time)
        }

        fun getStringDate(year: Long, month: Long, day: Long): Long {
            var dateStr = ""
            var dateDefaultFormat = ""
            var monthStr = if(month < 10) "0$month" else month.toString()
            var dayStr = if(day < 10) "0$day" else day.toString()

            if(languageUtils.isLanguageCloseToCzech()) {
                dateStr =
                    DATE_FORMAT_CS
                        .replace("yyyy", year.toString())
                        .replace("dd", dayStr)
                        .replace("MM", monthStr)

                dateDefaultFormat = DATE_FORMAT_CS

            } else {
                dateStr =
                    DATE_FORMAT_EN
                        .replace("yyyy", year.toString())
                        .replace("dd", dayStr)
                        .replace("MM", monthStr)

                dateDefaultFormat = DATE_FORMAT_EN
            }

            val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(dateDefaultFormat)
            val date: Date = simpleDateFormat.parse(dateStr)

            return date.time
        }

    }
}
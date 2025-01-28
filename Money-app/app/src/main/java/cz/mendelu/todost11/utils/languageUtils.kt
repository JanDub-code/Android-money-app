package cz.mendelu.todost11.utils

import java.util.Locale

object languageUtils {
    private val CZECH="cs"
    private val SLOVAK="sk"
    private val ENGLISH="en"

    fun isLanguageCloseToCzech():Boolean{
        val language = Locale.getDefault().language
        return  language.equals(CZECH) || language.equals(SLOVAK)
    }
}
package it.basheer.pme.util

import android.content.Context
import android.content.SharedPreferences

/**
 * App shared pref.
 *
 * create the shared preferences of the app
 *
 * @constructor Create [AppSharedPref]
 *
 * @param context
 */
class AppSharedPref(context: Context) {
    private var sharedPref: SharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    /**
     * Save data.
     *
     * @param key Key
     * @param value Value
     */
    fun saveData(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    /**
     * Get string.
     *
     * @param key Key
     * @return
     */
    fun getString(key: String) = sharedPref.getString(key, null)

    /**
     * Clear all data.
     */
    fun clearAllData() {
        sharedPref.edit()
            .clear()
            .apply()
    }
}
package it.basheer.pme.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*

fun Activity?.hideKeyboard(newFocusView: View? = null) {
    this?.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
        newFocusView?.requestFocus()
    }
}

fun Fragment.hideKeyboard() {
    activity.hideKeyboard()
}

fun Activity?.showKeyboard() {
    this?.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Fragment.showKeyboard() {
    activity.showKeyboard()
}

fun getStartWeekDay(mDate: Date): Date? {
    var date = convertToLocalDateViaInstant(mDate)
    date = date?.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
    return convertToDateViaSqlDate(date)
}

fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate? {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun convertToDateViaSqlDate(dateToConvert: LocalDate?): Date? {
    return java.sql.Date.valueOf(dateToConvert.toString())
}
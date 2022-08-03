package it.basheer.pme.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import it.basheer.pme.R
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

fun getYesterdayDate(mDate: Date): Date? {
    var date = convertToLocalDateViaInstant(mDate)
    date = date?.minusDays(1)
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

fun getDates(): List<String> {
    val date = Date()
    val todayDate = SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.ENGLISH).format(date)
    val startWeekDate = SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.ENGLISH).format(getStartWeekDay(date) ?: date)
    val startMonthDate = SimpleDateFormat("yyyy-MM-01 00:00:00", Locale.ENGLISH).format(date)
    val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(date)
    return listOf(todayDate, startWeekDate, startMonthDate, currentDate)
}

fun getAlterDate(context: Context, mDate: String): String {
    val currentDate = Date()
    val yesterdayDate = getYesterdayDate(currentDate) ?: currentDate

    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val date = convertToDateViaSqlDate(LocalDate.parse(mDate, pattern)) ?: currentDate


    if (isSameDay(date, currentDate)) {
        val returnDate = LocalDateTime.parse(mDate, pattern).format(DateTimeFormatter.ofPattern("hh:mm a"))
        return "${context.getString(R.string.today_at)} $returnDate"
    }
    if (isSameDay(date, yesterdayDate)) {
        val returnDate = LocalDateTime.parse(mDate, pattern).format(DateTimeFormatter.ofPattern("hh:mm a"))
        return "${context.getString(R.string.yesterday_at)} $returnDate"
    }

    return LocalDateTime.parse(mDate, pattern).format(DateTimeFormatter.ofPattern("MM-dd / hh:mm a"))
}

fun isSameDay(date1: Date, date2: Date): Boolean {
    val localDate1 = date1.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val localDate2 = date2.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return localDate1.isEqual(localDate2)
}
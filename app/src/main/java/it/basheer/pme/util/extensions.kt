package it.basheer.pme.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

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
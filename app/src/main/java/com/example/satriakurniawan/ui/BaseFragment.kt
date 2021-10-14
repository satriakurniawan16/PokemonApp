package com.example.satriakurniawan.ui

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    open fun getSnackBar(view: View?, msg: String?) {
        val snackBar = SpannableStringBuilder()
        snackBar.append(msg)
        val boldStart = snackBar.length
        snackBar.setSpan(StyleSpan(Typeface.BOLD), boldStart, snackBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        snackBar.append(".")
        Snackbar.make(requireView(), snackBar, Snackbar.LENGTH_LONG).show()
    }

}
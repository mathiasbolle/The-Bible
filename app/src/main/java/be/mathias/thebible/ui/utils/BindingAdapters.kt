package be.mathias.thebible.ui.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import be.mathias.thebible.ui.VerseApiStatus
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Binding adapter that changes the
 * visibility of the floating to (VISIBLE)
 * if the status = DONE.
 */
@BindingAdapter("verseApiStatus")
fun FloatingActionButton.bindStatus(status: VerseApiStatus?) {
    visibility = when (status) {
        VerseApiStatus.DONE -> {
            View.VISIBLE
        }
        VerseApiStatus.ERROR -> {
            View.INVISIBLE
        }
        else -> {
            View.INVISIBLE
        }
    }
}


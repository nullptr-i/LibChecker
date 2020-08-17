package com.absinthe.libchecker.view.dialogfragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.Constants
import com.absinthe.libchecker.constant.GlobalValues
import com.absinthe.libchecker.utils.SPUtils
import com.absinthe.libchecker.view.settings.LibReferenceThresholdView
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.analytics.EventProperties

class LibThresholdDialogFragment : DialogFragment() {

    private val dialogView by lazy {
        LibReferenceThresholdView(
            requireContext()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle(R.string.lib_ref_threshold)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val threshold = dialogView.slider.value.toInt()
                GlobalValues.libReferenceThreshold.value = threshold
                SPUtils.putInt(Constants.PREF_LIB_REF_THRESHOLD, threshold)
                Analytics.trackEvent(Constants.Event.SETTINGS, EventProperties().set("PREF_LIB_REF_THRESHOLD", threshold.toLong()))
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}
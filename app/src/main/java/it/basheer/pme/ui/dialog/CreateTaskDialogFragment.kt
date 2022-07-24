package it.basheer.pme.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import it.basheer.pme.R
import it.basheer.pme.databinding.FragmentCreateTaskDialogBinding

class CreateTaskDialogFragment : DialogFragment() {

    private lateinit var mBinding: FragmentCreateTaskDialogBinding

    /**
     * create a new instance of the dialog
     */
    companion object {
        private var mGuid: String? = null
        const val TAG = "AddItemDialogFragment_HIMS"
        fun newInstance(): CreateTaskDialogFragment {
            return CreateTaskDialogFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // Set transparent background and no title
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCreateTaskDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fill task period spinner
        val items = listOf("Daily", "Weekly", "Monthly")
        val adapter = ArrayAdapter(requireContext(), R.layout.layout_text_view_item, items)
        (mBinding.createTaskTxtTaskPeriod.editText as AutoCompleteTextView).setAdapter(adapter)
    }
}
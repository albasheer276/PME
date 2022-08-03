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
import android.widget.TextView
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.FragmentCreateNegativeTaskDialogBinding
import it.basheer.pme.util.*
import java.util.*

class CreateNegativeTaskDialogFragment(private val onClickListener: (task: Task) -> Unit) : DialogFragment() {

    private lateinit var mBinding: FragmentCreateNegativeTaskDialogBinding

    private var isTimingTask = false

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateTaskDialogFragment_PME"
        fun newInstance(onClickListener: (task: Task) -> Unit): CreateNegativeTaskDialogFragment {
            return CreateNegativeTaskDialogFragment(onClickListener)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
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
        mBinding = FragmentCreateNegativeTaskDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = NEGATIVE_PERIODS_EN
        if (Locale.getDefault().language == "ar") {
            list = NEGATIVE_PERIODS_AR
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.layout_text_view_item, list)
        (mBinding.createNegativeTaskTxtTaskPeriod.editText as AutoCompleteTextView).setAdapter(adapter)

        setOnTextChangeListener()

        mBinding.createNegativeTaskBtnCancel.setOnClickListener {
            hideKeyboard()
            this.dismiss()
        }

        (mBinding.createNegativeTaskTxtTaskPeriod.editText as AutoCompleteTextView).setOnItemClickListener { adapterView, _, i, _ ->
            isTimingTask = (adapterView[i] as TextView).text == list[1]
            if (isTimingTask) {
                mBinding.createNegativeTaskTxtTaskDuration.visibility = View.VISIBLE
            } else {
                mBinding.createNegativeTaskTxtTaskDuration.visibility = View.GONE
            }
        }

        mBinding.createNegativeTaskBtnSave.setOnClickListener {
            var isValid = true

            val name = mBinding.createNegativeTaskTxtTaskName.editText?.text.toString()
            var period = (mBinding.createNegativeTaskTxtTaskPeriod.editText as AutoCompleteTextView).text.toString()
            val duration = mBinding.createNegativeTaskTxtTaskDuration.editText?.text.toString()
            val points = mBinding.createNegativeTaskTxtTaskPoints.editText?.text.toString()

            if (name.isEmpty()) {
                isValid = false
                mBinding.createNegativeTaskTxtTaskName.isErrorEnabled = true
                mBinding.createNegativeTaskTxtTaskName.error = getString(R.string.required_field)
            }

            if (period.isEmpty()) {
                isValid = false
                mBinding.createNegativeTaskTxtTaskPeriod.isErrorEnabled = true
                mBinding.createNegativeTaskTxtTaskPeriod.error = getString(R.string.required_field)
            }

            if (duration.isEmpty() && isTimingTask) {
                isValid = false
                mBinding.createNegativeTaskTxtTaskDuration.isErrorEnabled = true
                mBinding.createNegativeTaskTxtTaskDuration.error = getString(R.string.required_field)
            }

            if (points.isEmpty()) {
                isValid = false
                mBinding.createNegativeTaskTxtTaskPoints.isErrorEnabled = true
                mBinding.createNegativeTaskTxtTaskPoints.error = getString(R.string.required_field)
            }

            if (list == NEGATIVE_PERIODS_AR) {
                period = NEGATIVE_PERIODS_EN[list.indexOf(period)]
            }

            if (isValid) {
                val task = Task(
                    name = name,
                    period = period,
                    count = 1,
                    duration = if (duration.isEmpty() || !isTimingTask) 0 else duration.toInt(),
                    points = points.toInt(),
                    type = NEGATIVE_TASKS_TYPE,
                    user_id = BaseApp.getInstance().getUser().value?.id ?: 0
                )
                onClickListener(task)
                this.dismiss()
                hideKeyboard()
            }
        }
    }

    private fun setOnTextChangeListener() {
        mBinding.apply {
            createNegativeTaskTxtTaskName.editText?.addTextChangedListener {
                createNegativeTaskTxtTaskName.isErrorEnabled = false
            }
            createNegativeTaskTxtTaskPeriod.editText?.addTextChangedListener {
                createNegativeTaskTxtTaskPeriod.isErrorEnabled = false
            }
            createNegativeTaskTxtTaskCount.editText?.addTextChangedListener {
                createNegativeTaskTxtTaskCount.isErrorEnabled = false
            }
            createNegativeTaskTxtTaskDuration.editText?.addTextChangedListener {
                createNegativeTaskTxtTaskDuration.isErrorEnabled = false
            }
            createNegativeTaskTxtTaskPoints.editText?.addTextChangedListener {
                createNegativeTaskTxtTaskPoints.isErrorEnabled = false
            }
        }
    }
}
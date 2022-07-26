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
import it.basheer.pme.databinding.FragmentCreatePositiveTaskDialogBinding
import it.basheer.pme.util.NEGATIVE_PERIODS
import it.basheer.pme.util.POSITIVE_PERIODS
import it.basheer.pme.util.POSITIVE_TASKS_TYPE

class CreatePositiveTaskDialogFragment(private val onClickListener: (task: Task) -> Unit) : DialogFragment() {

    private lateinit var mBinding: FragmentCreatePositiveTaskDialogBinding

    private var isTimingTask = false;

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateTaskDialogFragment_PME"
        fun newInstance(onClickListener: (task: Task) -> Unit): CreatePositiveTaskDialogFragment {
            return CreatePositiveTaskDialogFragment(onClickListener)
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
        mBinding = FragmentCreatePositiveTaskDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.layout_text_view_item, POSITIVE_PERIODS)
        (mBinding.createPositiveTaskTxtTaskPeriod.editText as AutoCompleteTextView).setAdapter(adapter)

        setOnTextChangeListener()

        mBinding.createPositiveTaskBtnCancel.setOnClickListener {
            this.dismiss()
        }

        (mBinding.createPositiveTaskTxtTaskPeriod.editText as AutoCompleteTextView).setOnItemClickListener { adapterView, _, i, _ ->
            if ((adapterView[i] as TextView).text == NEGATIVE_PERIODS[1])
                isTimingTask = true
        }

        mBinding.createPositiveTaskBtnSave.setOnClickListener {
            var isValid = true

            val name = mBinding.createPositiveTaskTxtTaskName.editText?.text.toString()
            val period = (mBinding.createPositiveTaskTxtTaskPeriod.editText as AutoCompleteTextView).text.toString()
            val count = mBinding.createPositiveTaskTxtTaskCount.editText?.text.toString()
            val duration = mBinding.createPositiveTaskTxtTaskDuration.editText?.text.toString()
            val points = mBinding.createPositiveTaskTxtTaskPoints.editText?.text.toString()

            if (name.isEmpty()) {
                isValid = false
                mBinding.createPositiveTaskTxtTaskName.isErrorEnabled = true
                mBinding.createPositiveTaskTxtTaskName.error = getString(R.string.required_field)
            }

            if (period.isEmpty()) {
                isValid = false
                mBinding.createPositiveTaskTxtTaskPeriod.isErrorEnabled = true
                mBinding.createPositiveTaskTxtTaskPeriod.error = getString(R.string.required_field)
            }

            if (count.isEmpty()) {
                isValid = false
                mBinding.createPositiveTaskTxtTaskCount.isErrorEnabled = true
                mBinding.createPositiveTaskTxtTaskCount.error = getString(R.string.required_field)
            }

            if (points.isEmpty()) {
                isValid = false
                mBinding.createPositiveTaskTxtTaskPoints.isErrorEnabled = true
                mBinding.createPositiveTaskTxtTaskPoints.error = getString(R.string.required_field)
            }

            if (isValid) {
                val task = Task(
                    name = name,
                    period = period,
                    count = count.toInt(),
                    duration = if (duration.isEmpty()) 0 else duration.toInt(),
                    points = points.toInt(),
                    type = POSITIVE_TASKS_TYPE,
                    user_id = BaseApp.getInstance().user?.id ?: 0
                )
                onClickListener(task)
                this.dismiss()
            }
        }
    }

    private fun setOnTextChangeListener() {
        mBinding.apply {
            createPositiveTaskTxtTaskName.editText?.addTextChangedListener {
                createPositiveTaskTxtTaskName.isErrorEnabled = false
            }
            createPositiveTaskTxtTaskPeriod.editText?.addTextChangedListener {
                createPositiveTaskTxtTaskPeriod.isErrorEnabled = false
            }
            createPositiveTaskTxtTaskCount.editText?.addTextChangedListener {
                createPositiveTaskTxtTaskCount.isErrorEnabled = false
            }
            createPositiveTaskTxtTaskDuration.editText?.addTextChangedListener {
                createPositiveTaskTxtTaskDuration.isErrorEnabled = false
            }
            createPositiveTaskTxtTaskPoints.editText?.addTextChangedListener {
                createPositiveTaskTxtTaskPoints.isErrorEnabled = false
            }
        }
    }
}
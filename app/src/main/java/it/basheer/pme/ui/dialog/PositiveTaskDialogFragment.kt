package it.basheer.pme.ui.dialog

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.FragmentCreateRewardDialogBinding
import it.basheer.pme.databinding.FragmentPositiveDialogBinding

class PositiveTaskDialogFragment(private val task: ActiveTask, private val onClickListener: () -> Unit) : DialogFragment() {

    private lateinit var mBinding: FragmentPositiveDialogBinding

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateRewardDialogFragment_PME"
        fun newInstance(task: ActiveTask, onClickListener: () -> Unit): PositiveTaskDialogFragment {
            return PositiveTaskDialogFragment(task, onClickListener)
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
        mBinding = FragmentPositiveDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            positiveTaskTxtTaskName.text = task.name
            positiveTaskTxtTaskPoints.text = "${task.points} ${getString(R.string.pt)}"
            positiveTaskTxtTaskPeriod.text = task.period
            positiveTaskTxtTaskCount.text = "${task.completed} ${getString(R.string.slash)} ${task.count}"
            positiveTaskTxtTaskDuration.text = "${task.duration} ${getString(R.string.min)}"

            when (task.status) {
                0 -> {
                    positiveTaskTxtTaskStatus.text = getString(R.string.todo)
                }
                1 -> {
                    positiveTaskTxtTaskStatus.text = getString(R.string.in_progress)
                }
                else -> {
                    positiveTaskTxtTaskStatus.text = getString(R.string.done)
                    mBinding.positiveTaskBtnSave.visibility = View.GONE
                }
            }
        }

        mBinding.positiveTaskBtnCancel.setOnClickListener {
            this.dismiss()
        }

        mBinding.positiveTaskBtnSave.setOnClickListener {
            onClickListener.invoke()
            this.dismiss()
        }
    }
}
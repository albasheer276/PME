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
import it.basheer.pme.databinding.FragmentNegativeDialogBinding
import it.basheer.pme.databinding.FragmentPositiveDialogBinding

class NegativeTaskDialogFragment(private val task: ActiveTask, private val onClickListener: () -> Unit) : DialogFragment() {

    private lateinit var mBinding: FragmentNegativeDialogBinding

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateRewardDialogFragment_PME"
        fun newInstance(task: ActiveTask, onClickListener: () -> Unit): NegativeTaskDialogFragment {
            return NegativeTaskDialogFragment(task, onClickListener)
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
        mBinding = FragmentNegativeDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            negativeTaskTxtTaskName.text = task.name
            negativeTaskTxtTaskPoints.text = "${task.points} ${getString(R.string.pt)}"

            when (task.status) {
                0 -> {
                    negativeTaskTxtTaskStatus.text = getString(R.string.free)
                }
                else -> {
                    negativeTaskTxtTaskStatus.text = getString(R.string.down)

                }
            }

            if (task.duration!! != 0) {
                negativeTaskTxtTaskDuration.text = "${task.duration} ${getString(R.string.min)}"
            } else {
                negativeTaskTxtTaskDuration.text = ""
            }
        }

        mBinding.negativeTaskBtnCancel.setOnClickListener {
            this.dismiss()
        }

        mBinding.negativeTaskBtnSave.setOnClickListener {
            onClickListener.invoke()
            this.dismiss()
        }
    }
}
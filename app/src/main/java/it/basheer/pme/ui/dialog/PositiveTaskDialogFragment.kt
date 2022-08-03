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
import androidx.recyclerview.widget.LinearLayoutManager
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.databinding.FragmentCreateRewardDialogBinding
import it.basheer.pme.databinding.FragmentPositiveDialogBinding
import it.basheer.pme.ui.adapter.PositiveTaskAdapter
import it.basheer.pme.ui.adapter.ProgressAdapter
import it.basheer.pme.util.POSITIVE_TASKS_TYPE
import it.basheer.pme.util.getStartWeekDay
import it.basheer.pme.util.hideKeyboard
import okhttp3.internal.notifyAll
import java.text.SimpleDateFormat
import java.util.*

class PositiveTaskDialogFragment(
    private val task: ActiveTask,
    private val taskLogs: List<TaskLog>,
    private val onClickListener: () -> Unit,
    private val onDeleteTaskLog: (taskLog: TaskLog) -> Unit
) :
    DialogFragment() {

    private lateinit var mBinding: FragmentPositiveDialogBinding

    private lateinit var mProgressAdapter: ProgressAdapter

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateRewardDialogFragment_PME"
        fun newInstance(
            task: ActiveTask,
            taskLogs: List<TaskLog>,
            onClickListener: () -> Unit,
            onDeleteTaskLog: (taskLog: TaskLog) -> Unit
        ): PositiveTaskDialogFragment {
            return PositiveTaskDialogFragment(task, taskLogs, onClickListener, onDeleteTaskLog)
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

        setupRecyclerView()
        loadData()

        mBinding.positiveTaskBtnCancel.setOnClickListener {
            hideKeyboard()
            this.dismiss()
        }

        mBinding.positiveTaskBtnSave.setOnClickListener {
            onClickListener.invoke()
            hideKeyboard()
            this.dismiss()
        }
    }

    private fun setupRecyclerView() {
        mBinding.positiveTaskRvProgress.apply {
            setLayoutManager(LinearLayoutManager(context))
            mProgressAdapter = ProgressAdapter(requireContext()) { taskLog ->
                onDeleteTaskLog(taskLog)
                mProgressAdapter.remove(taskLog)
                if (mProgressAdapter.count == 0) {
                    mBinding.positiveTaskRvProgress.showEmpty()
                }
            }
            adapter = mProgressAdapter
        }
    }

    private fun loadData() {
        mProgressAdapter.addAll(taskLogs)
        if (taskLogs.isEmpty()) {
            mBinding.positiveTaskRvProgress.showEmpty()
        }
    }
}
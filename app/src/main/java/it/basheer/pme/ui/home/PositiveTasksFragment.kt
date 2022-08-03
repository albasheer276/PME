package it.basheer.pme.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.databinding.FragmentPositiveTasksBinding
import it.basheer.pme.ui.adapter.PositiveTaskAdapter
import it.basheer.pme.ui.dialog.CreatePositiveTaskDialogFragment
import it.basheer.pme.ui.dialog.PositiveTaskDialogFragment
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.POSITIVE_TASKS_TYPE
import it.basheer.pme.util.getDates
import it.basheer.pme.util.getStartWeekDay
import it.basheer.pme.util.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PositiveTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentPositiveTasksBinding
    private lateinit var mPositiveTaskAdapter: PositiveTaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPositiveTasksBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()

        mBinding.positiveTasksBtnAdd.setOnClickListener {
            CreatePositiveTaskDialogFragment.newInstance() { task ->
                taskViewModel.createTask(task).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    loadData()
                }
            }.show(parentFragmentManager, CreatePositiveTaskDialogFragment.TAG)
        }
    }

    private fun setupRecyclerView() {
        mBinding.positiveTasksRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mPositiveTaskAdapter = PositiveTaskAdapter(requireContext()) { activeTask ->
                taskViewModel.getCurrentProgress(activeTask.id!!, activeTask.period, getDates()[0], getDates()[1], getDates()[2])
                    .observe(viewLifecycleOwner) { taskLogs ->
                        PositiveTaskDialogFragment.newInstance(activeTask, taskLogs, onClickListener = {
                            val taskLog = TaskLog(
                                user_id = BaseApp.getInstance().getUser().value?.id!!,
                                task_id = activeTask.id,
                                points = activeTask.points,
                                date = getDates()[3]
                            )
                            taskViewModel.createTaskLog(taskLog).observe(viewLifecycleOwner) {

                                BaseApp.getInstance().getUser().value?.let { mainUser ->
                                    mainUser.points = mainUser.points.plus(activeTask.points)
                                    BaseApp.getInstance().setUser(mainUser)
                                    userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                                        loadData()
                                    }
                                }
                            }

                        }, onDeleteTaskLog = { taskLog ->
                            taskViewModel.deleteTaskLog(taskLog).observe(viewLifecycleOwner) {
                                BaseApp.getInstance().getUser().value?.let { mainUser ->
                                    mainUser.points = mainUser.points.minus(taskLog.points)
                                    BaseApp.getInstance().setUser(mainUser)
                                    userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                                        loadData()
                                    }
                                }
                            }
                        }).show(parentFragmentManager, PositiveTaskDialogFragment.TAG)
                    }

            }
            adapter = mPositiveTaskAdapter

            setRefreshListener {
                setRefreshing(true)
                loadData()
            }
        }
    }

    private fun loadData() {
        mPositiveTaskAdapter.clear()
        mBinding.positiveTasksRvTasks.showProgress()
        taskViewModel.getActiveTasks(POSITIVE_TASKS_TYPE, BaseApp.getInstance().getUser().value?.id!!, getDates()[0], getDates()[1], getDates()[2])
            .observe(viewLifecycleOwner) { tasks ->
                mBinding.positiveTasksRvTasks.setRefreshing(false)
                mPositiveTaskAdapter.addAll(tasks)
                if (tasks.isEmpty()) {
                    mBinding.positiveTasksRvTasks.showEmpty()
                }
            }
    }
}
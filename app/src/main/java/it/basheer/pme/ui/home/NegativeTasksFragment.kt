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
import it.basheer.pme.databinding.FragmentNegativeTasksBinding
import it.basheer.pme.ui.adapter.NegativeTaskAdapter
import it.basheer.pme.ui.dialog.CreateNegativeTaskDialogFragment
import it.basheer.pme.ui.dialog.NegativeTaskDialogFragment
import it.basheer.pme.ui.dialog.PositiveTaskDialogFragment
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.NEGATIVE_TASKS_TYPE
import it.basheer.pme.util.getDates
import it.basheer.pme.util.getStartWeekDay
import it.basheer.pme.util.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NegativeTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentNegativeTasksBinding
    private lateinit var mNegativeTaskAdapter: NegativeTaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNegativeTasksBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()

        mBinding.negativeTasksBtnAdd.setOnClickListener {
            CreateNegativeTaskDialogFragment.newInstance() { task ->
                taskViewModel.createTask(task).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    loadData()
                }
            }.show(parentFragmentManager, CreateNegativeTaskDialogFragment.TAG)
        }
    }

    private fun setupRecyclerView() {
        mBinding.negativeTasksRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mNegativeTaskAdapter = NegativeTaskAdapter(requireContext()) { activeTask ->
                taskViewModel.getCurrentProgress(activeTask.id!!, activeTask.period, getDates()[0], getDates()[1], getDates()[2])
                    .observe(viewLifecycleOwner) { taskLogs ->
                        NegativeTaskDialogFragment.newInstance(activeTask, taskLogs, onClickListener = {
                            val taskLog = TaskLog(
                                user_id = BaseApp.getInstance().getUser().value?.id!!,
                                task_id = activeTask.id,
                                points = activeTask.points,
                                date = getDates()[3]
                            )
                            taskViewModel.createTaskLog(taskLog).observe(viewLifecycleOwner) {

                                BaseApp.getInstance().getUser().value?.let { mainUser ->
                                    mainUser.points = mainUser.points.minus(activeTask.points)
                                    BaseApp.getInstance().setUser(mainUser)
                                    userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                                        loadData()
                                    }
                                }
                            }

                        },
                            onDeleteTaskLog = { taskLog ->
                                taskViewModel.deleteTaskLog(taskLog).observe(viewLifecycleOwner) {
                                    BaseApp.getInstance().getUser().value?.let { mainUser ->
                                        mainUser.points = mainUser.points.plus(taskLog.points)
                                        BaseApp.getInstance().setUser(mainUser)
                                        userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                                            loadData()
                                        }
                                    }
                                }
                            }).show(parentFragmentManager, NegativeTaskDialogFragment.TAG)
                    }

            }
            adapter = mNegativeTaskAdapter

            setRefreshListener {
                setRefreshing(true)
                loadData()
            }
        }
    }

    private fun loadData() {
        mNegativeTaskAdapter.clear()
        mBinding.negativeTasksRvTasks.showProgress()
        taskViewModel.getActiveTasks(NEGATIVE_TASKS_TYPE, BaseApp.getInstance().getUser().value?.id!!, getDates()[0], getDates()[1], getDates()[2])
            .observe(viewLifecycleOwner) { tasks ->
                mBinding.negativeTasksRvTasks.setRefreshing(false)
                mNegativeTaskAdapter.addAll(tasks)
                if (tasks.isEmpty()) {
                    mBinding.negativeTasksRvTasks.showEmpty()
                }
            }
    }
}
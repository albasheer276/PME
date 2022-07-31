package it.basheer.pme.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentNegativeTasksBinding
import it.basheer.pme.databinding.FragmentPositiveTasksBinding
import it.basheer.pme.ui.adapter.NegativeTaskAdapter
import it.basheer.pme.ui.adapter.PositiveTaskAdapter
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.util.NEGATIVE_TASKS_TYPE
import it.basheer.pme.util.POSITIVE_TASKS_TYPE
import it.basheer.pme.util.getStartWeekDay
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NegativeTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentNegativeTasksBinding
    private lateinit var mNegativeTaskAdapter: NegativeTaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()

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
    }

    private fun setupRecyclerView() {
        mBinding.negativeTasksRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mNegativeTaskAdapter = NegativeTaskAdapter(requireContext())
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
        val date = Date()
        val currentDate = SimpleDateFormat("dd-MM-yyyy 00:00:00").format(date)
        val startWeekDate = SimpleDateFormat("dd-MM-yyyy 00:00:00").format(getStartWeekDay(date) ?: date)
        val startMonthDate = SimpleDateFormat("01-MM-yyyy 00:00:00").format(date)
        taskViewModel.getActiveTasks(NEGATIVE_TASKS_TYPE, BaseApp.getInstance().user?.id!!, currentDate, startWeekDate, startMonthDate)
            .observe(viewLifecycleOwner) { tasks ->
                mBinding.negativeTasksRvTasks.setRefreshing(false)
                mNegativeTaskAdapter.addAll(tasks)
                if (tasks.isEmpty()) {
                    mBinding.negativeTasksRvTasks.showEmpty()
                }
            }
    }
}
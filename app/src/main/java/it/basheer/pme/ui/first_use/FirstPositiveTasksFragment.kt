package it.basheer.pme.ui.first_use

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.ui.adapter.FirstPositiveTaskAdapter
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentFirstPositiveTasksBinding
import it.basheer.pme.ui.dialog.CreatePositiveTaskDialogFragment
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.util.POSITIVE_TASKS_TYPE
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class FirstPositiveTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentFirstPositiveTasksBinding
    private lateinit var mFirstPositiveTaskAdapter: FirstPositiveTaskAdapter


    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFirstPositiveTasksBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()

        mBinding.firstPositiveBtnAdd.setOnClickListener {
            CreatePositiveTaskDialogFragment.newInstance() { task ->
                taskViewModel.createTask(task).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    task.id = it
                    mFirstPositiveTaskAdapter.add(task)
                    mBinding.firstPositiveRvTasks.showRecycler()
                }
            }.show(parentFragmentManager, CreatePositiveTaskDialogFragment.TAG)
        }

        mBinding.firstPositiveBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_positiveTasksFragment_to_negativeTasksFragment)
        }
    }

    private fun setupRecyclerView() {
        mBinding.firstPositiveRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mFirstPositiveTaskAdapter = FirstPositiveTaskAdapter(requireContext())
            adapter = mFirstPositiveTaskAdapter
        }
    }

    private fun loadData() {
        mBinding.firstPositiveRvTasks.showProgress()
        taskViewModel.getTasks(POSITIVE_TASKS_TYPE, BaseApp.getInstance().user?.id!!).observe(viewLifecycleOwner) { tasks ->
            mFirstPositiveTaskAdapter.addAll(tasks)
            if (tasks.isEmpty()) {
                mBinding.firstPositiveRvTasks.showEmpty()
            }
        }
    }
}
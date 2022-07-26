package it.basheer.pme.ui.first_use

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
import it.basheer.pme.ui.adapter.FirstNegativeTaskAdapter
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentNegativeTasksBinding
import it.basheer.pme.ui.dialog.CreateNegativeTaskDialogFragment
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.util.NEGATIVE_TASKS_TYPE
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class NegativeTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentNegativeTasksBinding
    private lateinit var mFirstNegativeTaskAdapter: FirstNegativeTaskAdapter

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

        mBinding.firstNegativeBtnAdd.setOnClickListener {
            CreateNegativeTaskDialogFragment.newInstance() { task ->
                taskViewModel.createTask(task).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    task.id = it
                    mFirstNegativeTaskAdapter.add(task)
                    mBinding.firstNegativeRvTasks.showRecycler()
                }
            }.show(parentFragmentManager, CreateNegativeTaskDialogFragment.TAG)
        }

        mBinding.firstNegativeBtnNext.setOnClickListener {

        }
    }

    private fun setupRecyclerView() {
        mBinding.firstNegativeRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mFirstNegativeTaskAdapter = FirstNegativeTaskAdapter(requireContext())
            adapter = mFirstNegativeTaskAdapter
        }
    }

    private fun loadData() {
        mBinding.firstNegativeRvTasks.showProgress()
        taskViewModel.getTasks(NEGATIVE_TASKS_TYPE, BaseApp.getInstance().user?.id!!).observe(viewLifecycleOwner) { tasks ->
            mFirstNegativeTaskAdapter.addAll(tasks)
            if (tasks.isEmpty()) {
                mBinding.firstNegativeRvTasks.showEmpty()
            }
        }
    }
}
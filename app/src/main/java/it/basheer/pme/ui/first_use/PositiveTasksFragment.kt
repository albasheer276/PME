package it.basheer.pme.ui.first_use

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.adapter.PositiveTaskAdapter
import it.basheer.pme.databinding.FragmentPositiveTasksBinding
import it.basheer.pme.ui.dialog.CreateTaskDialogFragment

@AndroidEntryPoint
class PositiveTasksFragment : Fragment() {

    private lateinit var mBinding: FragmentPositiveTasksBinding
    private lateinit var mPositiveTaskAdapter: PositiveTaskAdapter

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

        mBinding.firstPositiveBtnAdd.setOnClickListener {
            CreateTaskDialogFragment.newInstance().show(parentFragmentManager, CreateTaskDialogFragment.TAG)
        }
    }

    private fun setupRecyclerView() {
        mBinding.firstPositiveRvTasks.apply {
            setLayoutManager(LinearLayoutManager(context))
            mPositiveTaskAdapter = PositiveTaskAdapter(requireContext())
            adapter = mPositiveTaskAdapter
            showProgress()
        }
    }

    private fun loadData() {
        mBinding.firstPositiveRvTasks.showEmpty()
    }
}
package it.basheer.pme.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import it.basheer.pme.R
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.RewardLog
import it.basheer.pme.databinding.FragmentRewardDialogBinding
import it.basheer.pme.ui.adapter.ProgressAdapter
import it.basheer.pme.ui.adapter.RewardLogAdapter
import it.basheer.pme.util.hideKeyboard

class RewardDialogFragment(private val reward: Reward, private val rewardLog: List<RewardLog>, private val onClickListener: () -> Unit) :
    DialogFragment() {

    private lateinit var mBinding: FragmentRewardDialogBinding

    private lateinit var mRewardLogAdapter: RewardLogAdapter

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateRewardDialogFragment_PME"
        fun newInstance(reward: Reward, rewardLog: List<RewardLog>, onClickListener: () -> Unit): RewardDialogFragment {
            return RewardDialogFragment(reward, rewardLog, onClickListener)
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
        mBinding = FragmentRewardDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            rewardTxtName.text = reward.name
            rewardTxtPoints.text = "${reward.points} ${getString(R.string.pt)}"
        }

        setupRecyclerView()
        loadData()

        mBinding.rewardBtnCancel.setOnClickListener {
            hideKeyboard()
            this.dismiss()
        }

        mBinding.rewardBtnSave.setOnClickListener {
            onClickListener.invoke()
            hideKeyboard()
            this.dismiss()
        }
    }

    private fun setupRecyclerView() {
        mBinding.rewardRvHistory.apply {
            setLayoutManager(LinearLayoutManager(context))
            mRewardLogAdapter = RewardLogAdapter(requireContext())
            adapter = mRewardLogAdapter
        }
    }

    private fun loadData() {
        mRewardLogAdapter.addAll(rewardLog)
        if (rewardLog.isEmpty()) {
            mBinding.rewardRvHistory.showEmpty()
        }
    }
}
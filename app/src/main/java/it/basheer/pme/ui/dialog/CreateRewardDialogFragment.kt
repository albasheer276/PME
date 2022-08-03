package it.basheer.pme.ui.dialog

import android.app.Dialog
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
import it.basheer.pme.data.model.Reward
import it.basheer.pme.databinding.FragmentCreateRewardDialogBinding
import it.basheer.pme.util.hideKeyboard

class CreateRewardDialogFragment(private val onClickListener: (reward: Reward) -> Unit) : DialogFragment() {

    private lateinit var mBinding: FragmentCreateRewardDialogBinding

    /**
     * create a new instance of the dialog
     */
    companion object {
        const val TAG = "CreateRewardDialogFragment_PME"
        fun newInstance(onClickListener: (reward: Reward) -> Unit): CreateRewardDialogFragment {
            return CreateRewardDialogFragment(onClickListener)
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
        mBinding = FragmentCreateRewardDialogBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnTextChangeListener()

        mBinding.createRewardBtnCancel.setOnClickListener {
            hideKeyboard()
            this.dismiss()
        }

        mBinding.createRewardBtnSave.setOnClickListener {
            var isValid = true

            val name = mBinding.createRewardTxtRewardName.editText?.text.toString()
            val points = mBinding.createRewardTxtRewardPoints.editText?.text.toString()

            if (name.isEmpty()) {
                isValid = false
                mBinding.createRewardTxtRewardName.isErrorEnabled = true
                mBinding.createRewardTxtRewardName.error = getString(R.string.required_field)
            }

            if (points.isEmpty()) {
                isValid = false
                mBinding.createRewardTxtRewardPoints.isErrorEnabled = true
                mBinding.createRewardTxtRewardPoints.error = getString(R.string.required_field)
            }

            if (isValid) {
                val reward = Reward(
                    name = name,
                    points = points.toInt(),
                    user_id = BaseApp.getInstance().getUser().value?.id ?: 0
                )
                onClickListener(reward)
                this.dismiss()
                hideKeyboard()
            }
        }
    }

    private fun setOnTextChangeListener() {
        mBinding.apply {
            createRewardTxtRewardName.editText?.addTextChangedListener {
                createRewardTxtRewardName.isErrorEnabled = false
            }
            createRewardTxtRewardPoints.editText?.addTextChangedListener {
                createRewardTxtRewardPoints.isErrorEnabled = false
            }
        }
    }
}
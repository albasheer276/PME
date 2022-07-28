package it.basheer.pme.ui.pin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.basheer.pme.R
import it.basheer.pme.databinding.FragmentCheckPinBinding
import it.basheer.pme.util.showKeyboard


class CheckPinFragment : Fragment() {

    private lateinit var mBinding: FragmentCheckPinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCheckPinBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.checkPinOtpView.requestFocus()
        showKeyboard()
    }
}
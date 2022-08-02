package it.basheer.pme.ui.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.basheer.pme.R
import it.basheer.pme.databinding.FragmentMembersBinding


class MembersFragment : Fragment() {

    private lateinit var mBinding: FragmentMembersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMembersBinding.inflate(inflater)
        return mBinding.root
    }
}
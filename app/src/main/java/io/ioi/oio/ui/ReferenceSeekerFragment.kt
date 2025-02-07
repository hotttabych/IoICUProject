package io.ioi.oio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentReferenceSeekerBinding

class ReferenceSeekerFragment : BaseFragment() {
    private var _binding: FragmentReferenceSeekerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReferenceSeekerBinding.inflate(inflater, container, false)
        return binding.root
    }
}
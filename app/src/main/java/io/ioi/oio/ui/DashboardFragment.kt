package io.ioi.oio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // TODO: File formatter onClickListener
            questionsButton.setOnClickListener {
                findNavController().navigate(R.id.question_predictor_fragment)
            }
            findReferencesButton.setOnClickListener {
                findNavController().navigate(R.id.reference_seeker_fragment)
            }
            formatFileButton.setOnClickListener {
                findNavController().navigate(R.id.file_check_fragment)
            }
        }
    }

    companion object {
        fun dashboardFragment() =
            DashboardFragment()
    }
}
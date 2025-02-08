package io.ioi.oio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentFileDownloadBinding

class FileDownloadFragment : BaseFragment() {
    private var _binding: FragmentFileDownloadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFileDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            readyLayout.postDelayed({
                readyLayout.visibility = View.GONE
                readyLayout.visibility = View.VISIBLE
            }, 2000)
            goHomeButton.setOnClickListener {
                findNavController().navigate(R.id.dashboard_fragment)
            }
        }
    }
}
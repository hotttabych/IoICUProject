package io.ioi.oio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentFileDownloadBinding

class FileDownloadFragment : Fragment() {
    private var _binding: FragmentFileDownloadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFileDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }
}
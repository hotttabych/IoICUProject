package io.ioi.oio.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentFileCheckerBinding

class FileCheckerFragment : BaseFragment() {
    private var _binding: FragmentFileCheckerBinding? = null
    private val binding get() = _binding!!

    private lateinit var fileUri_word: Uri

    private val getFileLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleFileUpload(it)
            if(fileUri_word.path?.isNotEmpty() == true){
                binding.addFileButtonWord.visibility = View.GONE
                binding.addFileButtonWordReady.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFileCheckerBinding.inflate(inflater, container, false)

        binding.addFileButtonWord.setOnClickListener {
            getFileLauncher.launch("application/vnd.openxmlformats-officedocument.wordprocessingml.document")}

        binding.formatFileButton.setOnClickListener {
            if (fileUri_word.path?.isNotEmpty() == true) {
                findNavController().navigate(R.id.file_download_fragment)
            }
        }

        return binding.root
    }

    private fun handleFileUpload(uri: Uri) {
        fileUri_word = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
}
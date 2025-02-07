package io.ioi.oio.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentQuestionPredictorBinding

class QuestionPredictorFragment : BaseFragment() {
    private var _binding: FragmentQuestionPredictorBinding? = null
    private val binding get() = _binding!!

    private val getFileLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleFileUpload(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionPredictorBinding.inflate(inflater, container, false)

        binding.addFileButtonPdf.setOnClickListener {
            getFileLauncher.launch("pdf/*")}

        return binding.root
    }

    private fun handleFileUpload(uri: Uri) {
        val filePath = uri.path
        Toast.makeText(requireContext(), "Файл загружен: $filePath", Toast.LENGTH_SHORT).show()
    }
}




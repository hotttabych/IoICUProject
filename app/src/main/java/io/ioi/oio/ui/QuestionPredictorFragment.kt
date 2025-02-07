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
import java.io.File

class QuestionPredictorFragment : BaseFragment() {
    private var _binding: FragmentQuestionPredictorBinding? = null
    private val binding get() = _binding!!

    private lateinit var fileUri_pdf: Uri
    private lateinit var fileUri_word: Uri
    private lateinit var fileUri_pptx: Uri

    private val getFileLauncher1 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleFileUpload1(it)
            if(fileUri_pdf != null){
                binding.addFileButtonPdf.visibility = View.GONE
                binding.addFileButtonPdfReady.visibility = View.VISIBLE
            }
        }
    }
    private val getFileLauncher2 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleFileUpload2(it)
            if(fileUri_word != null){
                binding.addFileButtonWord.visibility = View.GONE
                binding.addFileButtonWordReady.visibility = View.VISIBLE
            }
        }
    }
    private val getFileLauncher3 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleFileUpload3(it)
            if(fileUri_pptx != null){
                binding.addFileButtonPptx.visibility = View.GONE
                binding.addFileButtonPptxReady.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionPredictorBinding.inflate(inflater, container, false)

        binding.addFileButtonPdf.setOnClickListener {
            getFileLauncher1.launch("pdf/*")
        }
        binding.addFileButtonWord.setOnClickListener {
            getFileLauncher2.launch("word/*")}
        binding.addFileButtonPptx.setOnClickListener {
            getFileLauncher3.launch("pptx/*")}

        return binding.root
    }

    private fun handleFileUpload1(uri: Uri) {
        fileUri_pdf = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
    private fun handleFileUpload2(uri: Uri) {
        fileUri_word = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
    private fun handleFileUpload3(uri: Uri) {
        fileUri_pptx = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
}




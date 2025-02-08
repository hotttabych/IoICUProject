package io.ioi.oio.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.ioi.oio.R
import io.ioi.oio.api.ApiViewModel
import io.ioi.oio.databinding.FragmentQuestionPredictorBinding
import kotlinx.coroutines.launch
import java.io.File
import kotlin.getValue

class QuestionPredictorFragment : BaseFragment() {
    private var _binding: FragmentQuestionPredictorBinding? = null
    private val binding get() = _binding!!

    private val apiViewModel: ApiViewModel by viewModels()

    private var fileUri_pdf: Uri? = null
    private var fileUri_word: Uri? = null
    private var fileUri_pptx: Uri? = null

    private val getFileLauncher1 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handlePdfFileUpload1(it)
            if (fileUri_pdf?.path?.isNotEmpty() == true) {
                binding.addFileButtonPdf.visibility = View.GONE
                binding.addFileButtonPdfReady.visibility = View.VISIBLE
            }
        }
    }
    private val getFileLauncher2 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handleWordFileUpload2(it)
            if (fileUri_word?.path?.isNotEmpty() == true) {
                binding.addFileButtonWord.visibility = View.GONE
                binding.addFileButtonWordReady.visibility = View.VISIBLE
            }
        }
    }
    private val getFileLauncher3 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            handlePptxFileUpload(it)
            if (fileUri_pptx?.path?.isNotEmpty() == true) {
                binding.addFileButtonPptx.visibility = View.GONE
                binding.addFileButtonPptxReady.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionPredictorBinding.inflate(inflater, container, false)

        binding.addFileButtonPdf.setOnClickListener {
            getFileLauncher1.launch("application/pdf")
        }
        binding.addFileButtonWord.setOnClickListener {
            getFileLauncher2.launch("application/vnd.openxmlformats-officedocument.wordprocessingml.document")}
        binding.addFileButtonPptx.setOnClickListener {
            getFileLauncher3.launch("application/vnd.openxmlformats-officedocument.presentationml.presentation")}

        binding.formatFileButton.setOnClickListener{
            if (fileUri_pdf != null || fileUri_pptx != null || fileUri_word != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    binding.apply {
                        formatFileButton.visibility = View.INVISIBLE
                        predictionProgress.visibility = View.VISIBLE
                        predictionProgress.isIndeterminate = true
                    }
                    val answer = fileUri_pdf?.path?.let { pdfFilePath ->
                        apiViewModel.getQuestions(File(pdfFilePath), 10)
                    } ?: fileUri_pptx?.path?.let { pptxFilePath ->
                        apiViewModel.getQuestions(File(pptxFilePath), 10)
                    } ?: fileUri_word?.path?.let { wordFilePath ->
                        apiViewModel.getQuestions(File(wordFilePath), 10)
                    }
                    findNavController().navigate(R.id.file_download_fragment)
                }
            }
            else {
                Toast.makeText(requireContext(), "Загрузите файл для обработки", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun handlePdfFileUpload1(uri: Uri) {
        fileUri_pdf = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
    private fun handleWordFileUpload2(uri: Uri) {
        fileUri_word = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
    private fun handlePptxFileUpload(uri: Uri) {
        fileUri_pptx = uri
        Toast.makeText(requireContext(), "Файл загружен: ${uri.path}", Toast.LENGTH_SHORT).show()
    }
}




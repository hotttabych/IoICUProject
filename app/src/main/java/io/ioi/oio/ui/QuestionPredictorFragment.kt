package io.ioi.oio.ui

import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

        binding.formatFileButton.setOnClickListener {
            if (fileUri_pdf != null || fileUri_pptx != null || fileUri_word != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    binding.apply {
                        formatFileButton.visibility = View.INVISIBLE
                        predictionProgress.visibility = View.VISIBLE
                        predictionProgress.isIndeterminate = true
                    }
                    binding.editCountQua.setRangeFilter(0, 10)
                    val answer = fileUri_pdf?.let { pdfFile ->
                        apiViewModel.getQuestions(pdfFile.uriToFile(), binding.editCountQua.getIntValue())
                    } ?: fileUri_pptx?.let { pptxFile ->
                        apiViewModel.getQuestions(pptxFile.uriToFile(), binding.editCountQua.getIntValue())
                    } ?: fileUri_word?.let { wordFile ->
                        apiViewModel.getQuestions(wordFile.uriToFile(), binding.editCountQua.getIntValue())
                    } ?: ""
                    val bundle = Bundle().apply {
                        putString("result", answer)
                    }
                    findNavController().navigate(R.id.get_result_fragment, bundle)
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

    // Function to convert URI to actual file path
    private fun Uri.uriToFile(): File {
        val inputStream = requireContext().contentResolver.openInputStream(this) ?: throw IllegalStateException("Cannot open input stream")
        val file = File(requireContext().cacheDir, "temp_file.pdf") // Create a temp file
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return file
    }

    private fun EditText.setRangeFilter(minValue: Int, maxValue: Int) {
        val filter = object : InputFilter {
            override fun filter(
                source: CharSequence?, start: Int, end: Int,
                dest: Spanned?, dstart: Int, dend: Int
            ): CharSequence? {
                val newText = dest.toString() + source.toString()
                val value = newText.toIntOrNull() ?: return ""
                return if (value in minValue..maxValue) null else ""
            }
        }
        filters = arrayOf(filter)
    }

    fun EditText.getIntValue(): Int {
        return text.toString().toIntOrNull() ?: 10
    }

}


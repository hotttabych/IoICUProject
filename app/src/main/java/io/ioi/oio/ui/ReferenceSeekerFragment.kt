package io.ioi.oio.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.ioi.oio.databinding.FragmentReferenceSeekerBinding
import java.util.Calendar
import io.ioi.oio.R
import io.ioi.oio.api.ApiViewModel
import kotlinx.coroutines.launch

class ReferenceSeekerFragment : BaseFragment() {
    private var _binding: FragmentReferenceSeekerBinding? = null
    private val binding get() = _binding!!

    private val apiViewModel: ApiViewModel by viewModels()

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private var seekType: String = "книги"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReferenceSeekerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            pickStartDateButton.setOnClickListener {
                datePickerFragment { year, month, day ->
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }.also { calendar ->
                        startDate = calendar
                    }
                }.show(parentFragmentManager, "datePicker")
            }
            pickEndDateButton.setOnClickListener {
                datePickerFragment { year, month, day ->
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }.also { calendar ->
                        if ((startDate == null || (startDate!! < calendar))) {
                            endDate = calendar
                        }
                        else {
                            Toast.makeText(requireContext(), "Выберете другую дату", Toast.LENGTH_SHORT).show()
                        }

                    }
                }.show(parentFragmentManager, "datePicker")
            }
            binding.booksRadioButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    seekType = requireContext().getString(R.string.books).lowercase()
                }
            }
            binding.articlesRadioButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    seekType = requireContext().getString(R.string.articles).lowercase()
                }
            }
            binding.websitesRadioButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    seekType = requireContext().getString(R.string.websites).lowercase()
                }
            }
            binding.referenceSeekerLaunchButton.setOnClickListener {
                binding.apply {
                    referenceSeekerLaunchButton.visibility = View.INVISIBLE
                    predictionProgress.visibility = View.VISIBLE
                    predictionProgress.isIndeterminate = true
                }
                editTopic.apply {
                    if (text.toString().isNotEmpty()) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            apiViewModel.getSources(
                                text.toString(),
                                seekType,
                                startDate?.get(Calendar.YEAR).toString(),
                                endDate?.get(Calendar.YEAR).toString()
                            ).also {
                                val bundle = Bundle().apply {
                                    putString("result", it.toString())
                                }
                                findNavController().navigate(R.id.get_result_fragment, bundle)
                            }
                        }
                    }
                    else {
                        Toast.makeText(requireContext(), "Введите тему вашей работы", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

class DatePickerFragment(private var onDateSetCallback: (year: Int, month: Int, day: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        onDateSetCallback.invoke(year, month, day)
    }
}

fun datePickerFragment(onDateSet: (Int, Int, Int) -> Unit): DatePickerFragment {
    return DatePickerFragment(onDateSet)
}
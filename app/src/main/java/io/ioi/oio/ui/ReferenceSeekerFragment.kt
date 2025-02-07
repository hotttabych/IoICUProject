package io.ioi.oio.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import io.ioi.oio.databinding.FragmentReferenceSeekerBinding
import java.util.Calendar
import java.util.Date

class ReferenceSeekerFragment : BaseFragment() {
    private var _binding: FragmentReferenceSeekerBinding? = null
    private val binding get() = _binding!!

    private var startDate: Date? = null
    private var endDate: Date? = null

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
            pickStartDateButton.setOnClickListener {
                datePickerFragment { year, month, day ->
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }.also { calendar ->
                        startDate = calendar.time
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
                        endDate = calendar.time
                    }
                }.show(parentFragmentManager, "datePicker")
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
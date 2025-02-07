package io.ioi.oio.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentReferenceSeekerBinding
import java.util.Calendar

class ReferenceSeekerFragment : BaseFragment() {
    private var _binding: FragmentReferenceSeekerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReferenceSeekerBinding.inflate(inflater, container, false)
        return binding.root
    }
}

class DatePickerFragment(private var onDateSetCallback: () -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        onDateSetCallback.invoke()
    }

    companion object {
        fun datePickerFragment(onDateSet: () -> Unit) {
            DatePickerFragment(onDateSet)
        }
    }
}
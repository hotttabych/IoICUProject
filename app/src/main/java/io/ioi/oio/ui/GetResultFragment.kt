package io.ioi.oio.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentGetResultBinding


class GetResultFragment : Fragment() {
    private var _binding: FragmentGetResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            binding.copyableTextView.text = getString("result")
        }

        binding.apply {
            readyLayout.postDelayed({
                readyLayout.visibility = View.GONE
                readyLayout.visibility = View.VISIBLE
            }, 2000)
        }

        binding.goHomeButton.setOnClickListener {
            findNavController().navigate(R.id.dashboard_fragment)
        }

        binding.copyableTextView.setOnClickListener {
            copyTextToClipboard(binding.copyableTextView.text.toString())
        }

        binding.copyResultButton.setOnClickListener{
            copyTextToClipboard(binding.copyableTextView.text.toString())
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show()
    }
}



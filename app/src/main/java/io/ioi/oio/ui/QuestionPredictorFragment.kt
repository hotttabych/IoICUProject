package io.ioi.oio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ioi.oio.R
import io.ioi.oio.databinding.FragmentQuestionPredictorBinding

class QuestionPredictorFragment : BaseFragment() {
    private var _binding: FragmentQuestionPredictorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionPredictorBinding.inflate(inflater, container, false)
        return binding.root
    }
}
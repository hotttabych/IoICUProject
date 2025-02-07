package io.ioi.oio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis

open class BaseFragment : Fragment() {

    private var transitionsSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            transitionsSet = it.getBoolean("transitionsSet", false)
        }

        if (!transitionsSet) {
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("transitionsSet", transitionsSet)
        super.onSaveInstanceState(outState)
    }
}
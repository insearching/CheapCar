package com.auto.cheapcar.ui

import android.os.Bundle

class BuildDateFragment : BaseFragment() {

    companion object {
        fun newInstance() =
                MainTypeFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
    }
}
package com.auto.cheapcar.ui

import android.os.Bundle

class ManufacturerFragment : BaseFragment() {
    companion object {
        fun newInstance() =
                ManufacturerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
    }
}
package com.auto.cheapcar.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.auto.cheapcar.utils.dependencies

class BuildDateFragment : Fragment() {

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
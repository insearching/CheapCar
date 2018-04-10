package com.auto.cheapcar.ui


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.auto.cheapcar.R
import com.auto.cheapcar.utils.setTextColor
import kotlinx.android.synthetic.main.fragment_list.*

abstract class BaseFragment : Fragment() {

    private val BUNDLE_RECYCLER_LAYOUT = "recyclerlayout"
    protected lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.data_list)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            val savedRecyclerLayoutState = savedInstanceState.getParcelable<Parcelable>(BUNDLE_RECYCLER_LAYOUT)
            Handler().postDelayed({
                recyclerView.layoutManager.onRestoreInstanceState(savedRecyclerLayoutState)
            }, 100)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.layoutManager.onSaveInstanceState())
    }

    protected fun showProgress(show: Boolean){
        recyclerView.visibility = if(show) View.GONE else View.VISIBLE
        progress.visibility = if(show) View.VISIBLE else View.GONE
    }

    protected fun displayNoInternetMessage() {
        Snackbar.make(coordinatorLayout, R.string.no_internet, Snackbar.LENGTH_LONG)
                .setTextColor(Color.RED).show()
    }
}
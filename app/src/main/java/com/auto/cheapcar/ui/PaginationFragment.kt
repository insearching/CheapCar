package com.auto.cheapcar.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class PaginationFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener
            = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
            loadMoreItems(layoutManager.childCount, layoutManager.itemCount, layoutManager.findFirstVisibleItemPosition())
        }
    }

    abstract fun loadMoreItems(visibleItemCount: Int, totalItemCount: Int,
                               firstVisibleItemPosition: Int)
}
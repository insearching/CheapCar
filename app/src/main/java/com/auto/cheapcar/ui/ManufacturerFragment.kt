package com.auto.cheapcar.ui

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.auto.cheapcar.R
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.ui.ManufacturerFragment.ManufacturersAdapter.ViewHolder
import com.auto.cheapcar.utils.dependencies
import com.auto.cheapcar.utils.replaceFragment
import com.auto.cheapcar.utils.string
import javax.inject.Inject

class ManufacturerFragment : PaginationFragment(), ManufacturerPresenter.View {

    companion object {
        fun newInstance() =
                ManufacturerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    @Inject
    override lateinit var presenter: ManufacturerPresenter
    private lateinit var adapter: ManufacturersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ManufacturersAdapter(presenter)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = string(R.string.app_name)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun updateManufacturerList() {
        adapter.notifyDataSetChanged()
    }

    override fun loadMoreItems(visibleItemCount: Int, totalItemCount: Int, firstVisibleItemPosition: Int) {
        presenter.checkForMoreItems(visibleItemCount, totalItemCount, firstVisibleItemPosition)
    }

    override fun showNoInternetMessage() {
        displayNoInternetMessage()
    }

    override fun selectManufacturer(brand: Brand) {
        activity?.title = brand.title
        replaceFragment(MainTypeFragment.newInstance(brand))
    }

    override fun showLoading(show: Boolean) {
        showProgress(show)
    }

    inner class ManufacturersAdapter internal constructor(private val presenter: ManufacturerPresenter)
        : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            presenter.onBindRowViewAtPosition(position, holder)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemCount(): Int {
            return presenter.getManufacturersCount()
        }

        inner class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view), RowView {

            private val titleTv: TextView = view.findViewById(R.id.title)

            override fun setTitle(name: String) {
                titleTv.text = name
            }

            override fun setBackground(color: Int) {
                itemView.setBackgroundResource(color)
            }

            override fun setOnItemClickListener(listener: View.OnClickListener) {
                itemView.setOnClickListener(listener)
            }
        }
    }

    interface RowView {
        fun setTitle(name: String)

        fun setBackground(@DrawableRes color: Int)

        fun setOnItemClickListener(listener: View.OnClickListener)
    }
}

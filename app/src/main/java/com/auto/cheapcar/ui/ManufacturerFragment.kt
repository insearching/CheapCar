package com.auto.cheapcar.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.auto.cheapcar.R
import com.auto.cheapcar.ui.ManufacturerFragment.ManufacturersAdapter.ViewHolder
import com.auto.cheapcar.utils.dependencies
import javax.inject.Inject

class ManufacturerFragment : Fragment(), ManufacturerPresenter.View {
    companion object {
        fun newInstance() =
                ManufacturerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    @Inject
    override lateinit var presenter: ManufacturerPresenter
    lateinit var manufacturersAdapter : ManufacturersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.manufacturers_list)
        manufacturersAdapter = ManufacturersAdapter(presenter)
        recyclerView.adapter = manufacturersAdapter

    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun updateManufacturerList() {
        manufacturersAdapter.notifyDataSetChanged()
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
            return presenter.getPersonsCount()
        }

        inner class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view), RowView {

            private val titleTv: TextView = view.findViewById(R.id.title)

            override fun setTitle(name: String) {
                titleTv.text = name
            }
        }

    }

    interface RowView {
        fun setTitle(name: String)
    }
}
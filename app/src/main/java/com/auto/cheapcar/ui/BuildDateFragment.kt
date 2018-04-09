package com.auto.cheapcar.ui

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.auto.cheapcar.R
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Type
import com.auto.cheapcar.utils.dependencies
import com.auto.cheapcar.utils.toast
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class BuildDateFragment : BaseFragment(), BuildDatePresenter.View {

    private val BRAND = "brand"
    private val MAIN_TYPE = "mainType"

    companion object {
        fun newInstance(brand: Brand, mainType: Type) =
                BuildDateFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(BRAND, brand)
                        putParcelable(MAIN_TYPE, mainType)
                    }
                }
    }

    @Inject
    override lateinit var presenter: BuildDatePresenter
    private lateinit var adapter: BuildDateAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
        presenter.initializeWithData(arguments?.getParcelable(BRAND)!!,
                arguments?.getParcelable(MAIN_TYPE)!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.data_list)
        adapter = BuildDateAdapter(presenter)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun showNoInternetMessage() {
        Snackbar.make(coordinatorLayout, R.string.no_internet, Snackbar.LENGTH_LONG).show()
    }

    override fun updateDates() {
        adapter.notifyDataSetChanged()
    }

    override fun selectBuildDate(brand: String, type: String, date: String) {
        toast(getString(R.string.car_selected, brand, type, date))
    }

    inner class BuildDateAdapter internal constructor(private val presenter: BuildDatePresenter)
        : RecyclerView.Adapter<BuildDateAdapter.ViewHolder>() {

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
            return presenter.getTypesCount()
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
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
import com.auto.cheapcar.entity.bo.Type
import com.auto.cheapcar.utils.dependencies
import com.auto.cheapcar.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class MainTypeFragment : PaginationFragment(), MainTypePresenter.View {

    private val BRAND = "brand"

    companion object {
        fun newInstance(brand: Brand) =
                MainTypeFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(BRAND, brand)
                    }
                }
    }

    @Inject
    override lateinit var presenter: MainTypePresenter
    private lateinit var adapter: MainTypesAdapter
    private lateinit var selectedManufacturer : Brand

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        super.onCreate(savedInstanceState)
        selectedManufacturer = arguments?.getParcelable(BRAND)!!
        presenter.initializeWithData(selectedManufacturer)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MainTypesAdapter(presenter)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = selectedManufacturer.title
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun updateTypes() {
        adapter.notifyDataSetChanged()
        data_list.visibility = View.VISIBLE
        empty_tv.visibility = View.GONE
    }

    override fun showNoDataMessage() {
        data_list.visibility = View.GONE
        empty_tv.visibility = View.VISIBLE
    }

    override fun showNoInternetMessage() {
        displayNoInternetMessage()
    }

    override fun selectMainType(brand: Brand, mainType: Type) {
        activity?.title = "${selectedManufacturer.title} ${mainType.title}"
        replaceFragment(BuildDateFragment.newInstance(brand, mainType))
    }

    override fun showLoading(show: Boolean) {
        displayProgress(show)
    }

    override fun loadMoreItems(visibleItemCount: Int, totalItemCount: Int, firstVisibleItemPosition: Int) {
        presenter.checkForMoreItems(visibleItemCount, totalItemCount, firstVisibleItemPosition)
    }

    inner class MainTypesAdapter internal constructor(private val presenter: MainTypePresenter)
        : RecyclerView.Adapter<MainTypesAdapter.ViewHolder>() {

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
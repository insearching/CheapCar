package com.auto.cheapcar.utils

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.liftbrands.di.component.FragmentComponent
import java.util.*


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragment(fragment: Fragment) {
    hideKeyboard()
    activity!!.supportFragmentManager.transact {
        replace(R.id.content, fragment, fragment.javaClass.toString()).addToBackStack(fragment.javaClass.toString())
    }
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragmentWithPopBackStack(fragment: Fragment) {
    hideKeyboard()
    activity!!.supportFragmentManager.transact {
        replace(R.id.content, fragment)
        activity!!.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun Fragment.replaceSecondFragmentInTabletLandscape(@IdRes secondFragmentPlaceholder: Int, fragment: Fragment) {
    hideKeyboard()
    childFragmentManager.transact {
        replace(secondFragmentPlaceholder, fragment)
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Fragment.toast(message: String) {
    Toast.makeText(activity!!.baseContext, message, Toast.LENGTH_LONG).show()
}

fun Fragment.string(@StringRes id: Int): String = resources.getString(id)

@ColorInt
fun Fragment.getThemeAttrColor(@AttrRes colorAttr: Int): Int {
    val array = context!!.obtainStyledAttributes(null, intArrayOf(colorAttr))
    try {
        return array.getColor(0, 0)
    } finally {
        array.recycle()
    }
}

fun Fragment.hideKeyboard() {
    val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        if (activity!!.window.decorView.findFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity!!.window.decorView.windowToken, 0)
        } else {
            inputMethodManager.hideSoftInputFromWindow(null, 0)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

//fun Fragment.saveRecycler(recycler: RecyclerView, baseAdapter: BaseRecyclerAdapter<out Parcelable>, lastItemSelected: Parcelable?, bundle: Bundle) {
fun saveRecycler(recycler: RecyclerView, baseAdapter: BaseRecyclerAdapter<out Parcelable>, bundle: Bundle) {
    val child = recycler.getChildAt(0)
    if (child == null) {
        bundle.putInt("recycler_offset", recycler.top)
    }
    val manager = recycler.layoutManager
    if (manager is LinearLayoutManager) {
        bundle.putInt("recycler_index", manager.findFirstVisibleItemPosition())
    }
    bundle.putParcelableArrayList("recycler_content", ArrayList(baseAdapter.itemList))
    //bundle.putParcelable("recycler_last_item_selected", lastItemSelected)
}

//fun Fragment.restoreRecycler(recycler: RecyclerView, baseAdapter: BaseRecyclerAdapter<out Parcelable>, bundle: Bundle): Parcelable? {
fun restoreRecycler(recycler: RecyclerView, baseAdapter: BaseRecyclerAdapter<out Parcelable>, bundle: Bundle) {
    val manager = recycler.layoutManager
    baseAdapter.itemList = bundle.getParcelableArrayList("recycler_content")
    if (manager is LinearLayoutManager) {
        val index = bundle.getInt("recycler_index", 0)
        val offset = bundle.getInt("recycler_offset", 0)
        manager.scrollToPositionWithOffset(index, offset)
    }
    //return bundle.getParcelable("recycler_last_item_selected")
}


fun Fragment.dependencies(): FragmentComponent =
        DaggerFragmentComponent
                .builder()
                .applicationComponent(ComponentProvider.getApplicationComponent())
                .uiModule(UiModule(context!!))
                .build()

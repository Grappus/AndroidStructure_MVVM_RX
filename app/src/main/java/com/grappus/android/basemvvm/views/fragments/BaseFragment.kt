package com.grappus.android.basemvvm.views.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import com.grappus.android.basemvvm.views.activities.BaseActivity
import com.grappus.android.basemvvm.views.activities.BaseFragmentActivity
import com.grappus.android.basemvvm.listeners.FragmentSelectionListener
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by chandrapratapsingh on 5/31/18.
 */

abstract class BaseFragment : Fragment() {

    private val TAG = BaseFragment::class.java.simpleName

    private var listener: FragmentSelectionListener? = null

    var isFragmentAttached = false

    var compositeDisposable: CompositeDisposable? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        isFragmentAttached = true
        if (context is FragmentSelectionListener)
            listener = context
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()

        extractData()
        initComponents()
        if (isAdded) inflateInitialViews(isFragmentAttached)
        if (isFragmentAttached) isFragmentAttached = false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (compositeDisposable != null && !compositeDisposable!!.isDisposed())
            compositeDisposable!!.clear()
    }


    abstract fun extractData()
    abstract fun initComponents()
    abstract fun inflateInitialViews(isVeryFirstRun: Boolean)


    //Fragment Handling
    fun openFragment(reqCode: Int, data: Bundle?) {
        if (isAdded && activity != null && activity is BaseFragmentActivity
                && listener != null) {
            listener!!.onFragmentSelected(reqCode, data)
        }
    }

    fun finishActivity() {
        if (isAdded && activity != null) activity.finish()
    }

    fun navigateBack() {
        if (isAdded && activity != null) (activity as BaseActivity).navigateBack()
    }


    //Utils
    fun showToast(message: String) {
        if (isAdded) com.grappus.android.basemvvm.utils.Utils.showToast(activity, message)
    }

    fun showLoader(message: String) {
        if (isAdded) com.grappus.android.basemvvm.utils.Utils.showLoader(activity, message)
    }

    fun showKeyboard(show: Boolean) {
        if (isAdded) com.grappus.android.basemvvm.utils.Utils.showKeyboard(activity, show)
    }


    /************************************* Custom Methods *************************************
     *********************** Write them down here after this comment line ************************/
}
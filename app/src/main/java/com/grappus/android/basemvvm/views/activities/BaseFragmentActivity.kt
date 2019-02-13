package com.grappus.android.basemvvm.views.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.grappus.android.basemvvm.R
import com.grappus.android.basemvvm.listeners.FragmentSelectionListener
import com.grappus.android.basemvvm.utils.Constants
import com.grappus.android.basemvvm.views.fragments.onboarding.LoginFragment

/**
 * Created by chandrapratapsingh on 5/31/18.
 */

abstract class BaseFragmentActivity : BaseActivity(), FragmentSelectionListener,
        Constants.RequestAction, Constants.RequestArgs, Constants.RequestFragment {

    private val TAG = BaseFragmentActivity::class.java.simpleName

    var fragmentManager: androidx.fragment.app.FragmentManager? = null
    var fragmentTransaction: androidx.fragment.app.FragmentTransaction? = null

    var fragReqCode: Int = 0
    var fragReqData: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        navigateBack()
    }

    override fun navigateBack() {
        if (fragmentManager != null && fragmentManager!!.getBackStackEntryCount() > 1)
            fragmentManager!!.popBackStack()
        else {
            finish()
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
        }
    }


    //Fragments
    override fun onFragmentSelected(reqCode: Int, data: Bundle?) {
        if (fragmentManager == null) return

        fragmentTransaction = fragmentManager!!.beginTransaction()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentTransaction!!.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left,
                    R.anim.in_from_left, R.anim.out_to_right)
        }

        openFragment(reqCode, data, fragmentTransaction)
    }

    fun getFragment(reqCode: Int, data: Bundle?): androidx.fragment.app.Fragment? {
        when (reqCode) {
            Constants.RequestFragment.FRAG_LOGIN -> return LoginFragment.newInstance(data)
        }
        return null
    }

    fun openFragment(reqCode: Int, data: Bundle?, fragmentTransaction: androidx.fragment.app.FragmentTransaction?) {
        if (fragmentTransaction == null) return

        val fragment = getFragment(reqCode, data)
        if (fragment != null) {
            fragment.arguments = data
            fragmentTransaction.replace(R.id.fl_fragment_container, fragment)
            fragmentTransaction.addToBackStack(reqCode.toString())
            fragmentTransaction.commitAllowingStateLoss() //commit();
        }
    }

    fun clearFragmentBackStack(reqCode: Int, data: Bundle) {
        if (fragmentManager != null) {
            fragmentManager!!.popBackStackImmediate(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        onFragmentSelected(reqCode, data)
    }
}
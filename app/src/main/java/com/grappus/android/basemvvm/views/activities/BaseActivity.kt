package bajaj.capital.android.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.grappus.android.basemvvm.utils.Utils
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by chandrapratapsingh on 5/31/18.
 */

abstract class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    private var backPressedOnce = false

    var compositeDisposable: CompositeDisposable? = null


    companion object {
        //Define your companion objects whose value will persist across the activities
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set only if your app needs to run always in portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //To hold the instance of all the observables so that can be destroyed with activity lifecycle
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()

        //Disposes all the observables being observed
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed())
            compositeDisposable!!.clear()
    }


    abstract fun extractData()
    abstract fun initComponents()
    abstract fun navigateBack()

    fun estimateDoubleBackPress() {
        if (backPressedOnce)
            finish()
        else {
            this.backPressedOnce = true
            com.grappus.android.basemvvm.utils.Utils.showShortToast(this@BaseActivity, "Press again to Exit!")
            Handler().postDelayed({ backPressedOnce = false }, 2000)
        }
    }


    /************************************* Custom Methods *************************************
     *********************** Write them down here after this comment line ************************/
}
package com.grappus.android.basemvvm.views.fragments

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.grappus.android.basemvvm.R
import com.grappus.android.basemvvm.utils.Constants
import kotlinx.android.synthetic.main.fragment_web_view.*


/**
 * A simple [Fragment] subclass.
 */
class WebViewFragment : BaseFragment() {

    private val TAG = WebViewFragment::class.java.simpleName

    private var webUrl: String? = null


    companion object {
        fun newInstance(args: Bundle?): WebViewFragment {
            val fragment = WebViewFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }


    override fun extractData() {
        if (arguments != null)
            webUrl = arguments.getString(Constants.RequestArgs.ARG_EXTRA_1)
    }

    override fun initComponents() {

        if (com.grappus.android.basemvvm.utils.TextUtils.isEmpty(webUrl)) {
            showToast(getString(R.string.error_general))
            navigateBack()
            return
        }

        // Enable Javascript
        val webSettings = web_view!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        web_view!!.setInitialScale(1)

        // Force links and redirects to open in the WebView instead of in a browser
        web_view!!.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_bar!!.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_bar!!.visibility = View.INVISIBLE
                web_view!!.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (view != null) view.loadUrl(url)
                return true
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                //handler.proceed(); // Ignore SSL certificate errors
                showSSLErrorAlert(handler)
            }
        }
        web_view.loadUrl(webUrl)
    }

    override fun inflateInitialViews(isVeryFirstRun: Boolean) {

    }


    private fun showSSLErrorAlert(handler: SslErrorHandler?) {
        AlertDialog.Builder(activity)
                .setTitle("Security Alert!")
                .setMessage("We found some SSL certification error while opening this web page, "
                        + "proceeding further may be a security concern!")
                .setPositiveButton("Continue") { dialog, which -> if (handler != null) handler.proceed() }
                .setNegativeButton("Cancel") { dialog, which ->
                    if (handler != null) handler.cancel()
                    navigateBack()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show()
    }
}
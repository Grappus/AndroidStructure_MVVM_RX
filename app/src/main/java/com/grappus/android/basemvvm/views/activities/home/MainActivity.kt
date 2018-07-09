package com.grappus.android.basemvvm.views.activities.home

import android.os.Bundle
import com.grappus.android.basemvvm.views.activities.BaseFragmentActivity
import com.grappus.android.basemvvm.R

class MainActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        extractData()
        initComponents()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        estimateDoubleBackPress()
    }

    override fun extractData() {
        //Extract data from intent received
    }

    override fun initComponents() {
        //Initialize all the view components
    }
}
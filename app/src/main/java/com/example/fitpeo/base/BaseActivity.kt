package com.example.fitpeo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.fitpeo.util.PicassoInitializer


abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    private lateinit var viewDataBinding: ViewDataBinding
    abstract fun getLayoutID(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutID())
        PicassoInitializer().create(this)
    }

    fun getViewBinding(): V {
        return viewDataBinding as V
    }

    override fun onResume() {
        super.onResume()

    }


}
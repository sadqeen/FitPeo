package com.example.fitpeo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitpeo.base.BaseActivity
import com.example.fitpeo.databinding.ActivityMainBinding
import com.example.fitpeo.ui.HomeFragment
import com.example.fitpeo.util.NavigationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutID(): Int {
      return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUtils.replaceFragment(HomeFragment(),supportFragmentManager,R.id.rootContainer)

    }
}
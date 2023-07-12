package com.example.fitpeo.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fitpeo.R
import com.example.fitpeo.base.BaseFragment
import com.example.fitpeo.databinding.HomeFragmentBinding
import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.network.NetworkResult
import com.example.fitpeo.util.ItemCallback
import com.example.fitpeo.util.NavigationUtils
import com.example.fitpeo.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(), ItemCallback {
    private val TAG = HomeFragment::class.simpleName
    private lateinit var gridAdapter: MainGridAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private var dataList = ArrayList<ResponseData>()
    private lateinit var homeFragmentBinding: HomeFragmentBinding
    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentBinding = getViewBinding()
        if (hasNetwork(requireContext()) == true) {
            homeViewModel.getData()
        }
        else
        {
            Toast.makeText(requireContext(),getString(R.string.no_internet),Toast.LENGTH_LONG).show()
        }

        homeViewModel.dataResponse().observe(viewLifecycleOwner, Observer { response ->
            run {
                when (response.status) {
                    NetworkResult.Status.LOADING -> {
                        homeFragmentBinding.progressbar.visibility = View.VISIBLE
                        homeFragmentBinding.recylerviewMain.visibility = View.GONE

                    }
                    NetworkResult.Status.SUCCESS -> {
                        homeFragmentBinding.progressbar.visibility = View.GONE
                        homeFragmentBinding.recylerviewMain.visibility = View.VISIBLE
                        response.data?.let { dataList.addAll(it) }
                        setData()
                    }
                    NetworkResult.Status.ERROR -> {
                        homeFragmentBinding.progressbar.visibility = View.GONE
                        homeFragmentBinding.recylerviewMain.visibility = View.GONE
                    }
                }
            }

        })
    }

    private fun setData() {
        homeFragmentBinding.recylerviewMain.layoutManager =
            GridLayoutManager(requireContext(), 3)
        gridAdapter = MainGridAdapter(requireActivity(), dataList, this)
        homeFragmentBinding.recylerviewMain.adapter = gridAdapter


    }

    override fun pictureClicked(vdata: ResponseData) {
        NavigationUtils.addFragment(DetailFragment.newInstance(vdata),
            (activity)?.supportFragmentManager!!,R.id.rootContainer)
    }


}
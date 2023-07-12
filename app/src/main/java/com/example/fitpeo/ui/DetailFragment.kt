package com.example.fitpeo.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.fitpeo.R
import com.example.fitpeo.base.BaseFragment
import com.example.fitpeo.databinding.DetailfragmentBinding
import com.example.fitpeo.model.ResponseData

class DetailFragment : BaseFragment<DetailfragmentBinding>() {
    private lateinit var detailfragmentBinding: DetailfragmentBinding

    companion object {
        fun newInstance(data: ResponseData): DetailFragment {
            val args = Bundle()
            args.putParcelable("data", data)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.detailfragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailfragmentBinding = getViewBinding()
        var item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(
                "data",
                ResponseData::class.java
            )!!
        } else {
            arguments?.getParcelable<ResponseData>("data")!!
        }
        item.desc=getString(R.string.dummy_text)
        detailfragmentBinding.data = item



    }
}
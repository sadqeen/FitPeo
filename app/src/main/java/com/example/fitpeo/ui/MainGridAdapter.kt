package com.example.fitpeo.ui

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeo.MainActivity
import com.example.fitpeo.R
import com.example.fitpeo.databinding.ItemHomeLayoutBinding
import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.util.ItemCallback
import com.example.fitpeo.util.PicassoInitializer
import com.squareup.picasso.MemoryPolicy

class MainGridAdapter(
    var context: Context,
    var allData: ArrayList<ResponseData>,
    var itemCallback: ItemCallback
) : RecyclerView.Adapter<MainGridAdapter.GridHolder>() {

    class GridHolder(var itemHomeLayoutBinding: ItemHomeLayoutBinding) :
        RecyclerView.ViewHolder(itemHomeLayoutBinding.root)

    var widthPixels = 0


    init {
        val metrics = DisplayMetrics()
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(metrics)
        widthPixels = metrics.widthPixels


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        var rowPhotoLayoutBinding = DataBindingUtil.inflate<ItemHomeLayoutBinding>(
            LayoutInflater.from(parent.context), R.layout.item_home_layout, parent, false
        )
        return GridHolder(rowPhotoLayoutBinding)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        var w = ((widthPixels) / 3)
        val params = FrameLayout.LayoutParams(
            w,
            w
        )
        holder.itemHomeLayoutBinding.cardView.layoutParams = params
        holder.itemHomeLayoutBinding.data=allData[position]
        holder.itemHomeLayoutBinding.cardView.setOnClickListener {
            itemCallback.pictureClicked(allData[position])
        }


    }
}
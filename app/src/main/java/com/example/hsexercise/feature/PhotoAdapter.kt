package com.example.hsexercise.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hsexercise.R
import com.example.hsexercise.feature.database.FeatureModel
import kotlinx.android.synthetic.main.feature_item.view.*

class PhotoAdapter(var featureModel: List<FeatureModel>): RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    var visibleInt :Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_item,
        parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return featureModel.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = featureModel[position]
        val dimens = "${item.height} X ${item.width}"
        holder.author.text = item.author
        holder.dimension.text = dimens
        if (visibleInt == 1) {
            holder.progressBar.visibility = View.VISIBLE
        }else{
            holder.progressBar.visibility = View.GONE
        }
        Glide.with(holder.imageView.context).load(item.url).into(holder.imageView)
    }



    class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var author: TextView = itemView.author
        var dimension : TextView = itemView.dimensions
        var imageView : ImageView = itemView.image
        var progressBar: ProgressBar = itemView.progressBar
    }
}



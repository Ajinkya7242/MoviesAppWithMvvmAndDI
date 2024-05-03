package com.example.pirateflix.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pirateflix.databinding.Top100moviesItemBinding
import com.example.pirateflix.model.TopSeriesModelItem

class TopSeriesAdapter: RecyclerView.Adapter<TopSeriesAdapter.TopSeriesViewHolder>() {

    private var seriesList=ArrayList<TopSeriesModelItem>()
    lateinit var onItemClick: ((TopSeriesModelItem) -> Unit)
    fun setSeriesList(seriesList: ArrayList<TopSeriesModelItem>){
        this.seriesList = seriesList
        Log.d("seriesListSet",seriesList.toString())
        notifyDataSetChanged()
    }

    class TopSeriesViewHolder(val binding:Top100moviesItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSeriesViewHolder {
        return TopSeriesViewHolder(Top100moviesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    override fun onBindViewHolder(holder: TopSeriesViewHolder, position: Int) {
        val series=seriesList[position]
        Glide.with(holder.itemView).load(series.big_image).into(holder.binding.imgMovie)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(series)
        }
    }
}
package com.example.pirateflix.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pirateflix.databinding.Top100moviesItemBinding
import com.example.pirateflix.model.Top100MoviesModelItem

class Top100MoviesAdapter : RecyclerView.Adapter<Top100MoviesAdapter.Top100MoviesviewHolder>() {

    private var moviesList=ArrayList<Top100MoviesModelItem>()
    lateinit var onItemClick:((Top100MoviesModelItem)->Unit)
    fun setMovies(moviesList:ArrayList<Top100MoviesModelItem>){
        this.moviesList=moviesList
        Log.d("moviesListSet",moviesList.toString())
        notifyDataSetChanged()
    }
    class Top100MoviesviewHolder(val  binding:Top100moviesItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top100MoviesviewHolder {
        return Top100MoviesviewHolder(Top100moviesItemBinding.inflate(LayoutInflater.from(parent.context),parent ,false))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: Top100MoviesviewHolder, position: Int) {
        val movie=moviesList[position]
        Log.d("MoviesNames",movie.image.toString())

        Glide.with(holder.itemView).load(movie.big_image).into(holder.binding.imgMovie)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(movie)
        }
    }


}
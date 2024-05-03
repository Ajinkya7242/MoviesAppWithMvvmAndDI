package com.example.pirateflix.model

data class SeriesDetailsModel(
    val big_image: String,
    val description: String,
    val genre: List<String>,
    val id: String,
    val image: String,
    val imdb_link: String,
    val imdbid: String,
    val rank: Int,
    val rating: Double,
    val thumbnail: String,
    val title: String,
    val trailer: String,
    val trailer_embed_link: String,
    val trailer_youtube_id: String,
    val year: String
)
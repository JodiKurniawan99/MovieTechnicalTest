package com.example.domain

import com.example.core.common.Resource
import com.example.core.model.Genre
import com.example.core.model.Movie
import com.example.core.model.Review
import com.example.core.model.Video

interface MovieRepository {
    suspend fun getGenres(): Resource<List<Genre>>
    suspend fun getMoviesByGenre(genreId: String, page: Int): Resource<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Resource<Movie>
    suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>>
    suspend fun getMovieVideos(movieId: Int): Resource<List<Video>>
}

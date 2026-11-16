package com.example.data.repository

import com.example.core.common.DispatcherProvider
import com.example.core.common.Resource
import com.example.core.model.Genre
import com.example.core.model.Movie
import com.example.core.model.Review
import com.example.core.model.Video
import com.example.core.network.TmdbApiService
import com.example.data.mapper.toDomain
import com.example.domain.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService,
    private val dispatchers: DispatcherProvider
) : MovieRepository {

    override suspend fun getGenres(): Resource<List<Genre>> {
        return withContext(dispatchers.io) {
            try {
                val response = apiService.getGenres()
                val genres = response.genres.map { it.toDomain() }
                Resource.Success(genres)
            } catch (e: Exception) {
                Resource.Error(e, e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    override suspend fun getMoviesByGenre(genreId: String, page: Int): Resource<List<Movie>> {
        return withContext(dispatchers.io) {
            try {
                val response = apiService.getMoviesByGenre(genreId = genreId, page = page)
                val movies = response.results.map { it.toDomain() }
                Resource.Success(movies)
            } catch (e: Exception) {
                Resource.Error(e, e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<Movie> {
        return withContext(dispatchers.io) {
            try {
                val dto = apiService.getMovieDetails(movieId)
                Resource.Success(dto.toDomain())
            } catch (e: Exception) {
                Resource.Error(e, e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>> {
        return withContext(dispatchers.io) {
            try {
                val response = apiService.getMovieReviews(movieId = movieId, page = page)
                val reviews = response.results.map { it.toDomain() }
                Resource.Success(reviews)
            } catch (e: Exception) {
                Resource.Error(e, e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Resource<List<Video>> {
        return withContext(dispatchers.io) {
            try {
                val response = apiService.getMovieVideos(movieId)
                val videos = response.results.map { it.toDomain() }
                Resource.Success(videos)
            } catch (e: Exception) {
                Resource.Error(e, e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}

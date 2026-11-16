package com.example.domain.usecase

import com.example.core.common.Resource
import com.example.core.model.Movie
import com.example.domain.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<Movie> {
        return repository.getMovieDetails(movieId)
    }
}

package com.example.domain.usecase

import com.example.core.common.Resource
import com.example.core.model.Movie
import com.example.domain.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(genreId: String, page: Int): Resource<List<Movie>> {
        return repository.getMoviesByGenre(genreId, page)
    }
}

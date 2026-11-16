package com.example.domain.usecase

import com.example.core.common.Resource
import com.example.core.model.Review
import com.example.domain.MovieRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): Resource<List<Review>> {
        return repository.getMovieReviews(movieId, page)
    }
}

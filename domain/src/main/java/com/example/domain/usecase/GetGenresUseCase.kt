package com.example.domain.usecase

import com.example.core.common.Resource
import com.example.core.model.Genre
import com.example.domain.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Resource<List<Genre>> {
        return repository.getGenres()
    }
}

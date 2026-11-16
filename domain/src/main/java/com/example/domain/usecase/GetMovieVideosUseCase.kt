package com.example.domain.usecase

import com.example.core.common.Resource
import com.example.core.model.Video
import com.example.domain.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<List<Video>> {
        return repository.getMovieVideos(movieId)
    }
}

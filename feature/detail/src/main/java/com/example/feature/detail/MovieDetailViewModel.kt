package com.example.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Resource
import com.example.core.model.Movie
import com.example.core.model.Review
import com.example.core.model.Video
import com.example.domain.usecase.GetMovieDetailsUseCase
import com.example.domain.usecase.GetMovieReviewsUseCase
import com.example.domain.usecase.GetMovieVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val reviews: List<Review> = emptyList(),
    val videos: List<Video> = emptyList(),
    val errorMessage: String? = null,
    val reviewsPage: Int = 1,
    val isLoadingMoreReviews: Boolean = false,
    val reviewsEndReached: Boolean = false
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        if (movieId != 0) {
            loadMovieDetails()
            loadReviews(initial = true)
            loadVideos()
        }
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getMovieDetailsUseCase(movieId)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, movie = result.data) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message ?: "Failed to load movie details") }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun loadReviews(initial: Boolean = false) {
        val currentState = _uiState.value
        val pageToLoad = if (initial) 1 else currentState.reviewsPage
        if (!initial && (currentState.isLoadingMoreReviews || currentState.reviewsEndReached)) return

        if (!initial) {
            _uiState.update { it.copy(isLoadingMoreReviews = true) }
        }

        viewModelScope.launch {
            when (val result = getMovieReviewsUseCase(movieId, pageToLoad)) {
                is Resource.Success -> {
                    val newReviews = result.data
                    _uiState.update {
                        it.copy(
                            reviews = if (initial) newReviews else it.reviews + newReviews,
                            reviewsPage = pageToLoad + 1,
                            reviewsEndReached = newReviews.isEmpty(),
                            isLoadingMoreReviews = false
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoadingMoreReviews = false) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun loadVideos() {
        viewModelScope.launch {
            when (val result = getMovieVideosUseCase(movieId)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(videos = result.data) }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }
}

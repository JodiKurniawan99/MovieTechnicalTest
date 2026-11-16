package com.example.feature.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Resource
import com.example.core.model.Movie
import com.example.domain.usecase.GetMoviesByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null,
    val page: Int = 1,
    val endReached: Boolean = false
)

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val genreId: String = savedStateHandle.get<String>("genreId") ?: ""

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        if (genreId.isNotEmpty()) {
            loadMovies(initial = true)
        }
    }

    fun loadMovies(initial: Boolean = false) {
        val currentState = _uiState.value
        if (initial) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, page = 1, endReached = false) }
        } else {
            if (currentState.isLoadingMore || currentState.endReached || currentState.isLoading) return
            _uiState.update { it.copy(isLoadingMore = true) }
        }

        val pageToLoad = if (initial) 1 else currentState.page

        viewModelScope.launch {
            when (val result = getMoviesByGenreUseCase(genreId, pageToLoad)) {
                is Resource.Success -> {
                    val newMovies = result.data
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            movies = if (initial) newMovies else it.movies + newMovies,
                            page = pageToLoad + 1,
                            endReached = newMovies.isEmpty(),
                            errorMessage = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = result.message ?: "Failed to load movies"
                        )
                    }
                }
                is Resource.Loading -> {
                }
            }
        }
    }
}

package com.example.feature.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.Resource
import com.example.core.model.Genre
import com.example.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GenresUiState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenresUiState())
    val uiState: StateFlow<GenresUiState> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getGenresUseCase()) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, genres = result.data, errorMessage = null) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message ?: "Failed to load genres") }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}

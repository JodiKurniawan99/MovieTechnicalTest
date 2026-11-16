package com.example.data.mapper

import com.example.core.model.Genre
import com.example.core.network.dto.GenreDto

fun GenreDto.toDomain(): Genre = Genre(
    id = id,
    name = name
)

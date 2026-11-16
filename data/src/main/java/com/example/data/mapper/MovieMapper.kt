package com.example.data.mapper

import com.example.core.model.Movie
import com.example.core.network.dto.MovieDto
import com.example.core.network.dto.MovieDetailDto

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate.orEmpty()
)

fun MovieDetailDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate.orEmpty()
)

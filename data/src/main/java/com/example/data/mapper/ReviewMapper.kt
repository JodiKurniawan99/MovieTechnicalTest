package com.example.data.mapper

import com.example.core.model.Review
import com.example.core.network.dto.ReviewDto

fun ReviewDto.toDomain(): Review = Review(
    id = id,
    author = author,
    content = content,
    createdAt = createdAt,
    rating = authorDetails?.rating
)

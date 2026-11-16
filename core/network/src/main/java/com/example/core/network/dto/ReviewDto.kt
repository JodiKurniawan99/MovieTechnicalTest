package com.example.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDetailsDto(
    @Json(name = "rating") val rating: Double?
)

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @Json(name = "id") val id: String,
    @Json(name = "author") val author: String,
    @Json(name = "content") val content: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "author_details") val authorDetails: AuthorDetailsDto?
)

@JsonClass(generateAdapter = true)
data class ReviewResponseDto(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<ReviewDto>,
    @Json(name = "total_pages") val totalPages: Int
)

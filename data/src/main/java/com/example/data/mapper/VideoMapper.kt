package com.example.data.mapper

import com.example.core.model.Video
import com.example.core.network.dto.VideoDto

fun VideoDto.toDomain(): Video = Video(
    id = id,
    key = key,
    name = name,
    site = site,
    type = type
)

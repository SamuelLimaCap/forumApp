package com.example.forumapp.models

import com.example.forumapp.models.enum.EnumResponse

data class Response<T>(
    val data: T,
    val enumResponse : EnumResponse
)
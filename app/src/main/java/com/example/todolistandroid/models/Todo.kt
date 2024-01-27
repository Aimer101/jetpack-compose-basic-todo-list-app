package com.example.todolistandroid.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Todo(
    val id: Int,
    @SerialName(value = "item")
    val name: String
)

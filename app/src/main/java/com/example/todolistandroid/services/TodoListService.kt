package com.example.todolistandroid.services

import com.example.todolistandroid.models.Todo
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Serializable
data class CreateItemDTO(
    val item: String,
)

interface TodoListService {
    @GET("todo_list/all")
    suspend fun getTodoList(): List<Todo>

    @POST("create")
    suspend fun createItem(
        @Body request: CreateItemDTO
    ): Todo

    @DELETE("delete_item/{item_id}")
    suspend fun deleteItem(@Path("item_id") itemId: Int)
}

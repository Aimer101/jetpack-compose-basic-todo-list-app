package com.example.todolistandroid.config

import com.example.todolistandroid.repositories.TodoListRepository
import com.example.todolistandroid.repositories.TodoListRepositoryImplementation
import com.example.todolistandroid.services.TodoListService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory


interface AppContainer {
    val todoListRepository : TodoListRepository;
}

class AppContainerImplementation : AppContainer {

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:8080/api/v1/"
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private  val todoListServiceImplementation : TodoListService by lazy {
        retrofit.create(TodoListService::class.java)
    }


    override val todoListRepository: TodoListRepository by lazy {
        TodoListRepositoryImplementation(todoListServiceImplementation)
    }
}

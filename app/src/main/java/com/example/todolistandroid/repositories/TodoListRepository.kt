package com.example.todolistandroid.repositories

import com.example.todolistandroid.models.Todo
import com.example.todolistandroid.services.CreateItemDTO
import com.example.todolistandroid.services.TodoListService

interface TodoListRepository {
    suspend fun getTodosList(): List<Todo>
    suspend fun createTodo(item : String): Todo
    suspend fun deleteItem(itemId: Int)
}

class TodoListRepositoryImplementation(private val todoListService : TodoListService) : TodoListRepository {
    override suspend fun getTodosList(): List<Todo> {
        return todoListService.getTodoList();
    }

    override suspend fun createTodo(item: String): Todo {
        return todoListService.createItem(CreateItemDTO(item = item));
    }

    override suspend fun deleteItem(itemId: Int) {
        return todoListService.deleteItem(itemId);
    }
}



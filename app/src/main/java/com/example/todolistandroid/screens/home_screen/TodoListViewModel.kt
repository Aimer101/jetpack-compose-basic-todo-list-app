package com.example.todolistandroid.screens.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todolistandroid.common.FetchingState
import com.example.todolistandroid.common.FormSubmissionState
import com.example.todolistandroid.config.ApplicationConfig
import com.example.todolistandroid.models.Todo
import com.example.todolistandroid.repositories.TodoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class TodoListViewModel(private val todoListRepository: TodoListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListViewState())
    val uiState: StateFlow<TodoListViewState> = _uiState.asStateFlow()

    companion object {
        val TAG: String = TodoListViewModel::class.java.simpleName
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ApplicationConfig)
                val todoListRepository = application.container.todoListRepository
                TodoListViewModel(todoListRepository)
            }
        }
    }

    fun addItemToMainList(item: Todo) {
        var items = _uiState.value.payload
        items.plus(item)
        items = items.sortedByDescending { it.id }
        _uiState.update { currentState -> currentState.copy(payload = items) }
    }

    fun getTodosList() {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(fetchingState = FetchingState.LOADING) }
            try {
                val result = todoListRepository.getTodosList()
                Log.i(TAG, "SUCCESS")
                Log.i(TAG, "$result")

                _uiState.update { currentState ->
                    currentState.copy(
                        fetchingState = FetchingState.SUCCESS,
                        payload = result.sortedByDescending { it.id }
                    )
                }
            } catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(fetchingState = FetchingState.ERROR) }

            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(fetchingState = FetchingState.ERROR) }
            }
        }
    }

    fun onDelete(itemId: Int) {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(formSubmissionStatus = FormSubmissionState.LOADING) }
            try {
                todoListRepository.deleteItem(itemId)
                Log.i(TAG, "SUCCESS")

                _uiState.update { currentState ->
                    currentState.copy(
                        formSubmissionStatus = FormSubmissionState.SUCCESS,
                        payload = _uiState.value.payload.filter { it.id != itemId })
                }
            } catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(formSubmissionStatus = FormSubmissionState.ERROR) }

            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(formSubmissionStatus = FormSubmissionState.ERROR) }
            }
        }
    }
}

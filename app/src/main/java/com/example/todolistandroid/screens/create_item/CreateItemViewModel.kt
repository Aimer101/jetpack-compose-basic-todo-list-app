package com.example.todolistandroid.screens.create_item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.todolistandroid.common.FormSubmissionState
import com.example.todolistandroid.config.ApplicationConfig
import com.example.todolistandroid.models.Todo
import com.example.todolistandroid.repositories.TodoListRepository
import com.example.todolistandroid.screens.home_screen.TodoListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CreateItemViewModel(private val todoListRepository: TodoListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateItemViewState())
    val uiState: StateFlow<CreateItemViewState> = _uiState.asStateFlow()

    companion object {
        val TAG: String = CreateItemViewModel::class.java.simpleName

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ApplicationConfig)
                val todoListRepository = application.container.todoListRepository
                CreateItemViewModel(todoListRepository)
            }
        }
    }

    fun onChange (item : String) {
        _uiState.update { currentState -> currentState.copy(item = item) }

    }

    fun onSubmit(navController: NavHostController, addItemToOriginalList: (Todo) -> Unit) {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(formSubmissionState = FormSubmissionState.LOADING) }
            try {
                val result = todoListRepository.createTodo(_uiState.value.item)

                Log.i(TAG, "$result")


                addItemToOriginalList(result)
                navController.navigateUp()
                _uiState.update { currentState -> currentState.copy(formSubmissionState = FormSubmissionState.SUCCESS, item = "") }
                navController.navigateUp()
            } catch (e: IOException) {
                _uiState.update { currentState -> currentState.copy(formSubmissionState = FormSubmissionState.ERROR) }

            } catch (e: HttpException) {
                _uiState.update { currentState -> currentState.copy(formSubmissionState = FormSubmissionState.ERROR) }
            }
        }
    }


}
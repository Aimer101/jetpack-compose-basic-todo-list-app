package com.example.todolistandroid.screens.home_screen

import com.example.todolistandroid.common.FetchingState
import com.example.todolistandroid.common.FormSubmissionState
import com.example.todolistandroid.models.Todo

data class TodoListViewState(
    val fetchingState: FetchingState = FetchingState.INITIAL,
    val formSubmissionStatus : FormSubmissionState = FormSubmissionState.INITIAL,
    val payload: List<Todo> = emptyList(),
)

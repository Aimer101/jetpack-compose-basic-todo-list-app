package com.example.todolistandroid.screens.create_item

import com.example.todolistandroid.common.FormSubmissionState

data class CreateItemViewState(
    val item: String = "",
    val formSubmissionState: FormSubmissionState = FormSubmissionState.INITIAL,
)
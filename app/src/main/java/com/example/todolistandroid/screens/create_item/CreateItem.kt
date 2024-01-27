package com.example.todolistandroid.screens.create_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todolistandroid.common.FormSubmissionState
import com.example.todolistandroid.models.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateItem(navController: NavHostController, addItemToOriginalList: (Todo) -> Unit) {
    val createItemViewModel: CreateItemViewModel = viewModel(factory = CreateItemViewModel.Factory)
    val uiState = createItemViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text("Create Item")
                }
            )
        },
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // create a text-field here
            TextField(
                value = uiState.value.item,
                onValueChange = { createItemViewModel.onChange(it) },
                label = { Text("Item") }
            )

            Spacer(Modifier.height(20.dp))

            Button(onClick = {
                createItemViewModel.onSubmit(navController, addItemToOriginalList)
            }) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(10.dp)
                ) {
                    if (uiState.value.formSubmissionState == FormSubmissionState.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Create Item")
                    }
                }
            }
        }
    }
}
